import math
import numpy as np
import random

def is_num(string):
    alphabet = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "0"]
    for one_char in string:
        if one_char not in alphabet:
            string = string.replace(one_char, "")
    return string


def isdel(num1, num2):  # p, q
    if (num1 - 1) % num2 == 0:
        return True
    return False


def isprime(num):
    max = int(num ** 0.5) + 1
    for n in range(2, max ):
        if num % n == 0:
            return False
    return True


def is_Prime(n):
    """
    Miller-Rabin primality test.

    A return value of False means n is certainly not prime. A return value of
    True means n is very likely a prime.
    """
    if n != int(n):
        return False
    n = int(n)
    # Miller-Rabin test for prime
    if n == 0 or n == 1 or n == 4 or n == 6 or n == 8 or n == 9:
        return False

    if n == 2 or n == 3 or n == 5 or n == 7:
        return True
    s = 0
    d = n - 1
    while d % 2 == 0:
        d >>= 1
        s += 1
    assert (2 ** s * d == n - 1)

    def trial_composite(a):
        if pow(a, d, n) == 1:
            return False
        for i in range(s):
            if pow(a, 2 ** i * d, n) == n - 1:
                return False
        return True

    for i in range(8):  # number of trials
        a = random.randrange(2, n)
        if trial_composite(a):
            return False

    return True


def hashf(mes, q):
    hf = 100
    if len(mes) == 0:
        return hf
    else:
        n = len(mes)
        for i in range(n):
            hf = pow(hf + mes[i], 2, q)
        return hf


def generateh(p, q):
    h = []
    i = 1
    while i < p - 1:
        if pow(i, (p - 1) // q, p) > 1:
            h.append(i)
            i += 1
        else:
            i += 1
    return h


def generateg(h, p, q):
    g = pow(h, (p - 1) // q, p)
    if g > 1:
        return g
    else:
        return "g<1"


def pow_h(base, degree, module):
    degree = bin(degree)[2:]
    r = 1

    for i in range(len(degree) - 1, -1, -1):
        r = (r * base ** int(degree[i])) % module
        base = (base ** 2) % module

    return r


def encrypt(mes, p, q, x, k, h):
    if is_Prime(q) & is_Prime(p):
        if isdel(p, q):
            hf = hashf(mes, q)
            g = generateg(h, p, q)
            if g == "g<1":
                return "g<1", "", "", "", ""
            y = pow(g, x, p)
            r = pow(g, k, p) % q
            s = pow(k, q-2) * (hf + x*r) % q
            if (s == 0) | (r == 0):
                return ""
            return r, s, hf, g, y
        else:
            return "notdel", "notdel", "", "", ""
    else:
        return "notprime", "notprime", "", "", ""


def check(mes, p, q, r, s, h, x):
    if is_Prime(q) & is_Prime(p):
        if isdel(p, q):
            hf = hashf(mes, q)
            g = generateg(h, p, q)
            y = pow(g, x, p)
            w = pow(s, q-2, q)
            u1 = hf * w % q
            u2 = r*w % q
            v = (g**u1*y**u2 % p) % q
            return hf, w, u1, u2, v
        else:
            return "notdel", "notdel", "", "", ""
    else:
        return "notprime", "notprime", "", "", ""


if __name__ == '__main__':
    q = 107
    p = 8
    M = [2, 4, 21, 10, 18]
    hf = hashf(M, q)
    r, s = encrypt(M, p, q, 45, 31, 2)
    print(r)
