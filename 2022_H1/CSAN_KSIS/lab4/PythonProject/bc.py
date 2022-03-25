import argparse
import socket

from urllib.parse import urlparse


def print_formatted(s):
    print('-' * 15 + s + '-' * 15)


def print_err(s):
    print('[ERROR] ' + s)


if __name__ == '__main__':
    parser = argparse.ArgumentParser(prog='bc', usage='%(prog)s [options]')
    parser.add_argument("host", nargs=1, help="example: 127.0.0.1/users")
    parser.add_argument("port", nargs='?', help="example: 80", default=80, type=int)
    parser.add_argument("-m", help='request method (GET, POST, HEAD)', default='GET')
    parser.add_argument('-d', help='request data, if set, request will be POST')
    parser.add_argument('-H', action='append', help='headers, example: \"Signature: asildbf1234dsfkvjh\"')
    args = parser.parse_args()
    url = urlparse("http://" + args.host[0])

    host = url.hostname
    path = url.path
    port = args.port
    method = args.m
    data = args.d
    headers = args.H
    if data is not None:
        method = 'POST'

    # OPENING SOCKET
    try:
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.connect((host, port))
    except ConnectionRefusedError:
        print_err(f'Cannot connect to host {host}:{port}')
        exit(1)

    # REQUEST CONSTRUCTION
    path_str = path if len(path) != 0 else '/'
    headers_str = ('\n' + '\n'.join(headers)) if headers is not None else ''
    data_str = f"\n\n{data}" if data is not None else ''
    request = f"""
{method} {path_str} HTTP/1.0{headers_str}{data_str}

"""
    print_formatted('RAW REQUEST BEGIN')
    print(request)
    print_formatted('RAW REQUEST END')
    print('\n\n')

    # RECEIVING DATA
    s.sendall(request.encode())
    BUFF_SIZE = 4096  # 4 KiB
    data = b''
    while True:
        part = s.recv(BUFF_SIZE)
        data += part
        if len(part) < BUFF_SIZE:
            break

    # PRINTING RAW DATA
    print_formatted('RAW RESPONSE BEGIN')
    print(data.decode())
    print_formatted('RAW RESPONSE END')
