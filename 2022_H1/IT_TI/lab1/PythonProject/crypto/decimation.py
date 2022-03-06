from crypto.base import BaseCrypt, Lang, CryptoException
from crypto.util import is_co_prime


class DecimationCrypt(BaseCrypt):
    _key_lang = Lang.dig
    _text_lang = Lang.en

    @classmethod
    def _encrypt_raw(cls, text: str, key: str):
        try:
            key = int(key)
        except ValueError:
            raise CryptoException("Key must be int")
        if not is_co_prime(key, len(cls._en_uppercase)):
            raise CryptoException("Key must be co-prime to text length")
        return "".join(
            cls._map_letter(i, key)
            for i in text
        )

    @classmethod
    def _map_letter(cls, ch: str, key: int):
        return cls._get_text_letter(
            (cls._letter_index(ch) * key) % len(cls._en_uppercase),
            ch.isupper()
        )

    @classmethod
    def _decrypt_raw(cls, text: str, key: str):
        try:
            key = int(key)
        except ValueError:
            raise CryptoException("Key must be int")
        if not is_co_prime(key, len(cls._en_uppercase)):
            raise CryptoException("Keys must be co-prime")

        alpha_upper = "".join((cls._map_letter(i, key) for i in cls._en_uppercase))
        alpha_lower = "".join((cls._map_letter(i, key) for i in cls._en_lowercase))

        def map_encrypted(ch: str):
            alpha = alpha_upper if ch.isupper() else alpha_lower
            if alpha.find(ch) == -1:
                raise CryptoException("Invalid cypher text")
            return cls._get_text_letter(alpha.index(ch), ch.isupper())

        return "".join(
            map_encrypted(i)
            for i in text
        )


if __name__ == '__main__':
    key = ""
    print(DecimationCrypt.encrypt("mexico", key))
    print(DecimationCrypt.decrypt(
        DecimationCrypt.encrypt("PizDoLiz1 zxcsda nxc vgsdn fasdnfgxcg dast sdfg 2", key),
        key
    ))
