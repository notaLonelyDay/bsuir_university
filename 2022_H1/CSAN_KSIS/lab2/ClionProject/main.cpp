#pragma comment(lib, "Ws2_32.lib")
#include <iostream>
#include <winsock2.h>
#include <ws2tcpip.h>
#include <fstream>

int64_t GetFileSize(const std::string& fileName) {
	// no idea how to get filesizes > 2.1 GB in a C++ kind-of way.
	// I will cheat and use Microsoft's C-style file API
	FILE* f;
	if (fopen_s(&f, fileName.c_str(), "rb") != 0) {
		return -1;
	}
	_fseeki64(f, 0, SEEK_END);
	const int64_t len = _ftelli64(f);
	fclose(f);
	return len;
}

///
/// Recieves data in to buffer until bufferSize value is met
///
int RecvBuffer(SOCKET s, char* buffer, int bufferSize, int chunkSize = 4 * 1024) {
	int i = 0;
	while (i < bufferSize) {
		const int l = recv(s, &buffer[i], __min(chunkSize, bufferSize - i), 0);
		if (l < 0) { return l; } // this is an error
		i += l;
	}
	return i;
}

///
/// Sends data in buffer until bufferSize value is met
///
int SendBuffer(SOCKET s, const char* buffer, int bufferSize, int chunkSize = 4 * 1024) {

	int i = 0;
	while (i < bufferSize) {
		const int l = send(s, &buffer[i], __min(chunkSize, bufferSize - i), 0);
		if (l < 0) { return l; } // this is an error
		i += l;
	}
	return i;
}

//
// Sends a file
// returns size of file if success
// returns -1 if file couldn't be opened for input
// returns -2 if couldn't send file length properly
// returns -3 if file couldn't be sent properly
//
int64_t SendFile(SOCKET s, const std::string& fileName, int chunkSize = 64 * 1024) {

	const int64_t fileSize = GetFileSize(fileName);
	if (fileSize < 0) { return -1; }

	std::ifstream file(fileName, std::ifstream::binary);
	if (file.fail()) { return -1; }

	if (SendBuffer(s, reinterpret_cast<const char*>(&fileSize),
	               sizeof(fileSize)) != sizeof(fileSize)) {
		return -2;
	}

	char* buffer = new char[chunkSize];
	bool errored = false;
	int64_t i = fileSize;
	while (i != 0) {
		const int64_t ssize = __min(i, (int64_t)chunkSize);
		if (!file.read(buffer, ssize)) { errored = true; break; }
		const int l = SendBuffer(s, buffer, (int)ssize);
		if (l < 0) { errored = true; break; }
		i -= l;
	}
	delete[] buffer;

	file.close();

	return errored ? -3 : fileSize;
}

//
// Receives a file
// returns size of file if success
// returns -1 if file couldn't be opened for output
// returns -2 if couldn't receive file length properly
// returns -3 if couldn't receive file properly
//
int64_t RecvFile(SOCKET s, const std::string& fileName, int chunkSize = 64 * 1024) {
	std::ofstream file(fileName, std::ofstream::binary);
	if (file.fail()) { return -1; }

	int64_t fileSize;
	if (RecvBuffer(s, reinterpret_cast<char*>(&fileSize),sizeof(fileSize)) != sizeof(fileSize)) {
		return -2;
	}

	char* buffer = new char[chunkSize];
	bool errored = false;
	int64_t i = fileSize;
	while (i != 0) {
		const int r = RecvBuffer(s, buffer, (int)__min(i, (int64_t)chunkSize));
		if ((r < 0) || !file.write(buffer, r)) { errored = true; break; }
		i -= r;
	}
	delete[] buffer;

	file.close();

	return errored ? -3 : fileSize;
}

int main()
{
	WSADATA wsaData;
	WSAStartup(MAKEWORD(2, 2), &wsaData);

	{
		struct addrinfo hints = { 0 };
		hints.ai_family = AF_INET;
		hints.ai_socktype = SOCK_STREAM;
		hints.ai_protocol = IPPROTO_TCP;
		hints.ai_flags = AI_PASSIVE;

		struct addrinfo* result = NULL;
		if (0 != getaddrinfo(NULL, "9001", &hints, &result)) {
			// TODO: failed (don't just return, clean up)
		}

		SOCKET server = socket(result->ai_family, result->ai_socktype, result->ai_protocol);
		if (server == INVALID_SOCKET) {
			// TODO: failed (don't just return, clean up)
		}

		if (bind(server, result->ai_addr, (int)result->ai_addrlen) == INVALID_SOCKET) {
			// TODO: failed (don't just return, clean up)
		}
		freeaddrinfo(result);

		if (listen(server, SOMAXCONN) == SOCKET_ERROR) {
			// TODO: failed (don't just return, clean up)
		}

		// start a client on another thread
		HANDLE hClientThread = CreateThread(NULL, 0, ClientProc, NULL, 0, 0);

		SOCKET client = accept(server, NULL, NULL);

		const int64_t rc = RecvFile(client, "D:\\thetransmittedfile.bin");
		if (rc < 0) {
			std::cout << "Failed to recv file: " << rc << std::endl;
		}

		closesocket(client);
		closesocket(server);

		WaitForSingleObject(hClientThread, INFINITE);
		CloseHandle(hClientThread);
	}
	WSACleanup();
	return 0;
}