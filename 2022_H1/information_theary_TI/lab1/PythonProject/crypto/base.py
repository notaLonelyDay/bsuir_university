import string
from enum import Enum


class Lang(Enum):
    ru = "ru"
    en = "en"
    dig = "dig"
    empty = ""


class BaseCrypt:
    _ru_lowercase = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяабвгдеёжзийклмнопрстуфхцчшщъыьэюя"
    _ru_uppercase = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
    _ru_letters = _ru_uppercase + _ru_lowercase
    _en_uppercase = string.ascii_uppercase
    _en_lowercase = string.ascii_lowercase
    _en_letters = _en_uppercase + _en_lowercase
    _text_lang = Lang.empty
    _key_lang = Lang.empty

    @classmethod
    def encrypt(cls, text: str, key: str):
        return cls._encrypt_raw(
            cls._filter_text(text, cls._text_lang),
            cls._filter_text(key, cls._key_lang)
        )

    @classmethod
    def _encrypt_raw(cls, text: str, key: str):
        pass

    @classmethod
    def decrypt(cls, text: str, key: str):
        return cls._decrypt_raw(
            cls._filter_text(text, cls._text_lang),
            cls._filter_text(key, cls._key_lang)
        )

    @classmethod
    def _decrypt_raw(cls, text: str, key: str):
        pass

    @classmethod
    def _filter_text(cls, text: str, lang: Lang):
        alpha = None
        if lang == Lang.en:
            alpha = cls._en_letters
        elif lang == Lang.en:
            alpha = cls._ru_letters
        elif lang == Lang.dig:
            alpha = string.digits

        assert alpha is not None, "Wrong alphabet"
        return "".join((i for i in text if i in alpha))

    @classmethod
    def _letter_index(cls, ch: str):
        assert len(ch) == 1, "ch must be char"
        ans = max(
            cls._ru_lowercase.find(ch),
            cls._ru_uppercase.find(ch),
            cls._en_lowercase.find(ch),
            cls._en_uppercase.find(ch),
        )
        assert ans != -1, "symbol is not from ru/en alphabet"
        return ans
