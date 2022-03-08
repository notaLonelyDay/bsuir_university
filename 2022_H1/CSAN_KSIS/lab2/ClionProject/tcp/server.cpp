#pragma comment(lib, "Ws2_32.lib")
#include <iostream>
#include <winsock2.h>
#include <ws2tcpip.h>
#include <fstream>

///
/// Recieves data in to buffer until bufferSize value is met
///
int RecvBuffer(SOCKET s, char *buffer, int bufferSize, int chunkSize = 4*1024) {
	int i = 0;
	while (i < bufferSize) {
		const int l = recv(s, &buffer[i], __min(chunkSize, bufferSize - i), 0);
		if (l < 0) { return l; } // this is an error
		i += l;
	}
	return i;
}

//
// Receives a file
// returns size of file if success
// returns -1 if file couldn't be opened for output
// returns -2 if couldn't receive file length properly
// returns -3 if couldn't receive file properly
//
int64_t RecvFile(SOCKET s, const std::string &fileName, int chunkSize = 64*1024) {
	std::ofstream file(fileName, std::ofstream::binary | std::ofstream::out);
	if (file.fail()) { return -1; }

	int64_t fileSize;
	if (RecvBuffer(s, reinterpret_cast<char *>(&fileSize), sizeof(fileSize))!=sizeof(fileSize)) {
		return -2;
	}

	char *buffer = new char[chunkSize];
	bool errored = false;
	int64_t i = fileSize;
	while (i!=0) {
		const int r = RecvBuffer(s, buffer, (int)__min(i, (int64_t)chunkSize));
		if ((r < 0) || !file.write(buffer, r)) {
			errored = true;
			break;
		}
		i -= r;
	}
	delete[] buffer;

	file.close();

	return errored ? -3 : fileSize;
}

int main() {
	WSADATA wsaData;
	WSAStartup(MAKEWORD(2, 2), &wsaData);

	{
		struct addrinfo hints = {0};
		hints.ai_family = AF_INET;
		hints.ai_socktype = SOCK_STREAM;
		hints.ai_protocol = IPPROTO_TCP;
		hints.ai_flags = AI_PASSIVE;

		struct addrinfo *result = NULL;
		if (0!=getaddrinfo(NULL, "9001", &hints, &result)) {
			return 5;
		}

		SOCKET server = socket(result->ai_family, result->ai_socktype, result->ai_protocol);
		if (server==INVALID_SOCKET) {
			return 5;
		}

		if (bind(server, result->ai_addr, (int)result->ai_addrlen)==INVALID_SOCKET) {
			return 5;
		}
		freeaddrinfo(result);

		if (listen(server, SOMAXCONN)==SOCKET_ERROR) {
			return 5;
		}

		SOCKET client = accept(server, NULL, NULL);

		const int64_t rc = RecvFile(client, R"(C:\Users\user\Desktop\received123.exe)");
		if (rc < 0) {
			std::cout << "Failed to recv file: " << rc << std::endl;
		}

		closesocket(client);
		closesocket(server);

	}
	WSACleanup();
	return 0;
}