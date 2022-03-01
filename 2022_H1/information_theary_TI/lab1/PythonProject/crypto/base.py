class BaseCrypt:

    @classmethod
    def encrypt(cls, text: str, key: str):
        pass

    @classmethod
    def _encrypt_raw(cls, text: str, key: str):
        pass

    @classmethod
    def decrypt(cls, text: str, key: str):
        pass

    @classmethod
    def _decrypt_raw(cls, text: str, key: str):
        pass
