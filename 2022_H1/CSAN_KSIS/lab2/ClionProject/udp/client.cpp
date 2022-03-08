#pragma comment(lib, "Ws2_32.lib")
#include <iostream>
#include <winsock2.h>
#include <ws2tcpip.h>
#include <fstream>

int64_t GetFileSize(const std::string &fileName) {
	// no idea how to get filesizes > 2.1 GB in a C++ kind-of way.
	// I will cheat and use Microsoft's C-style file API
	FILE *f;
	if (fopen_s(&f, fileName.c_str(), "rb")!=0) {
		return -1;
	}
	_fseeki64(f, 0, SEEK_END);
	const int64_t len = _ftelli64(f);
	fclose(f);
	return len;
}

///
/// Sends data in buffer until bufferSize value is met
///
int SendBuffer(SOCKET s, const char *buffer, int bufferSize, int chunkSize = 4*1024) {

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
int64_t SendFile(SOCKET s, const std::string &fileName, int chunkSize = 64*1024) {

	const int64_t fileSize = GetFileSize(fileName);
	if (fileSize < 0) { return -1; }

	std::ifstream file(fileName, std::ifstream::binary);
	if (file.fail()) { return -1; }

	if (SendBuffer(s, reinterpret_cast<const char *>(&fileSize),
	               sizeof(fileSize))!=sizeof(fileSize)) {
		return -2;
	}

	char *buffer = new char[chunkSize];
	bool errored = false;
	int64_t i = fileSize;
	while (i!=0) {
		const int64_t ssize = __min(i, (int64_t)chunkSize);
		if (!file.read(buffer, ssize)) {
			errored = true;
			break;
		}
		const int l = SendBuffer(s, buffer, (int)ssize);
		if (l < 0) {
			errored = true;
			break;
		}
		i -= l;
	}
	delete[] buffer;

	file.close();

	return errored ? -3 : fileSize;
}

int main() {
	WSADATA wsaData;
	WSAStartup(MAKEWORD(2, 2), &wsaData);

	{
		struct addrinfo hints = {0}, *result, *ptr;
		hints.ai_family = AF_UNSPEC;
		hints.ai_socktype = SOCK_DGRAM;
		hints.ai_protocol = IPPROTO_UDP;

		if (getaddrinfo("127.0.0.1", "9001", &hints, &result)!=0) {
			return 5;
		}

		SOCKET client = INVALID_SOCKET;
		for (ptr = result; ptr!=NULL; ptr = ptr->ai_next) {
			client = socket(ptr->ai_family, ptr->ai_socktype, 0);
			if (client==SOCKET_ERROR) {
				return 5;
			}
			if (connect(client, ptr->ai_addr, (int)ptr->ai_addrlen) == SOCKET_ERROR) {
				closesocket(client);
				client = INVALID_SOCKET;
				continue;
			}
			break;
		}
		freeaddrinfo(result);

		if (client==SOCKET_ERROR) {
			std::cout << "Couldn't create client socket" << std::endl;
			return ~1;
		}

		int64_t rc = SendFile(client, R"(C:\Users\user\Desktop\lang.exe)");
		if (rc < 0) {
			std::cout << "Failed to send file: " << rc << std::endl;
		}

		closesocket(client);

	}
	WSACleanup();
	return 0;
}