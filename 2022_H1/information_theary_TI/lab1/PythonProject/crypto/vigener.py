# -*- coding: utf-8 -*-
from crypto.base import BaseCrypt


def getBadAplhabet():
    return "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm"


def clean_key(key):
    alphabet = get_alphabet()
    for i in key:
        if i not in alphabet:
            a = 2 / 0


def clean_text(text: str):
    badAlphabet = getBadAplhabet()
    print(badAlphabet)
    for i in text:
        if i in badAlphabet:
            a = 2 / 0


def get_alphabet():
    return "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюяабвгдеёжзийклмнопрстуфхцчшщъыьэюя"


def getUpperAlphabet():
    return "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"


def getLowerAplhabet():
    return "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"


def find_rotation_times(char):
    lower_alph = getLowerAplhabet()
    upper_alph = getUpperAlphabet()

    if char in lower_alph:
        return lower_alph.find(char)
    elif char in upper_alph:
        return upper_alph.find(char)
    else:
        return 0


def rotate_letter(letter, times):
    alphabet = get_alphabet()
    if letter not in alphabet:
        return letter
    current_pos = alphabet.find(letter)
    new_pos_letter = alphabet[current_pos + times]
    return new_pos_letter


def rotate_letter_back(letter, times):
    alphabet = get_alphabet()
    if letter not in alphabet:
        return letter
    current_pos = alphabet.rfind(letter)
    new_pos_letter = alphabet[current_pos - times]
    return new_pos_letter


def getAllowedSymb():
    return "-_ =+1234567890"


def encode_vis(plain_text, key):
    clean_key(key)
    clean_text(plain_text)

    res = ""
    counter = 0
    for i in range(len(plain_text)):
        char = plain_text[i]
        if char in getAllowedSymb():
            res += char
            continue
        key_rotate_times = int(counter / len(key))
        new_key_letter = rotate_letter(key[counter % len(key)], key_rotate_times)
        rot_times = find_rotation_times(new_key_letter)
        res += rotate_letter(char, rot_times)
        counter += 1
    return res


def decode_vis(cypher_text, key):
    res = ""
    counter = 0
    for i in cypher_text:
        char = i
        if char in getAllowedSymb():
            res += char
            continue
        key_rotate_times = int(counter / len(key))
        new_key_letter = rotate_letter(key[counter % len(key)], key_rotate_times)
        rot_times = find_rotation_times(new_key_letter)
        res += rotate_letter_back(char, rot_times)
        counter += 1
    return res


class VigenerCrypt(BaseCrypt):

    @classmethod
    def _encrypt_raw(cls, text: str, key: str):
        return encode_vis(plain_text=text, key=key)

    @classmethod
    def _decrypt_raw(cls, text: str, key: str):
        return decode_vis(cypher_text=text, key=key)


def main():
    a = encode_vis("САНКТ-ПЕТЕРБУРГ  -  ГОРОД СВЯТОГО ПЕТРА", "ленин")
    print(a)
    b = decode_vis(a, "ленин")
    print(b)


if __name__ == "__main__":
    main()
