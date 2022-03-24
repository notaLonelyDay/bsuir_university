import socket
import sys
import time


def getOrDefault(l: list, idx: int, default):
    return l[idx] if -len(l) <= idx < len(l) else default


def posix_to_rfc868(time):
    return time + 2208988800


if __name__ == '__main__':
    ip = sys.argv[1]
    port = int(getOrDefault(sys.argv, 2, '3774'))

    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.bind((ip, port))
    while True:
        print("\nReady to receive requests on port " + str(port) + " ...")
        sock.listen()
        conn, addr = sock.accept()
        rfc868 = posix_to_rfc868(int(time.time()))
        conn.send(rfc868.to_bytes(length=4, byteorder="big"))
        print(f"\nSent time {rfc868} to {addr}\n")
        conn.close()

    sock.close()

    time_rfc868 = int.from_bytes(data, byteorder='big')
    print(rfc868_to_datetime(time_rfc868))
