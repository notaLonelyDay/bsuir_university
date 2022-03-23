import datetime
import socket
import sys

import pytz


def getOrDefault(l: list, idx: int, default):
    return l[idx] if -len(l) <= idx < len(l) else default


def rfc868_to_datetime(rfc868):
    return datetime.datetime.fromtimestamp(
        rfc868 - 2208988800,  # convert to epoch time
        pytz.timezone("Europe/Minsk"))


if __name__ == '__main__':
    server = sys.argv[1]
    port = int(getOrDefault(sys.argv, 2, '37'))

    mySocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    mySocket.connect((server, port))
    data = mySocket.recv(4)
    mySocket.close()

    time_rfc868 = int.from_bytes(data, byteorder='big')
    print(rfc868_to_datetime(time_rfc868))
