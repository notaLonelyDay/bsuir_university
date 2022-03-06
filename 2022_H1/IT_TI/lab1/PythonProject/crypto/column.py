import pprint

from crypto.base import BaseCrypt, Lang


class ColumnCrypt(BaseCrypt):
    _text_lang = Lang.en
    _key_lang = Lang.en

    @classmethod
    def _encrypt_raw(cls, text: str, key: str):
        matrix = [list() for _ in range(len(key))]
        cur_col = 0
        for i in text:
            matrix[cur_col].append(i)
            cur_col += 1
            cur_col %= len(matrix)

        sorted_enum = list(enumerate(matrix))
        sorted_enum.sort(key=lambda i: cls._letter_index(key[i[0]]))
        return "".join((''.join(i[1]) for i in sorted_enum))

    @classmethod
    def _decrypt_raw(cls, text: str, key: str):
        key = cls.key_to_list(key)
        lkey = len(key)
        ltext = len(text)
        matrix = [[] for _ in range(lkey)]
        matrix_titled = list(zip(key, range(lkey), matrix))  # 0-key; 1-idx; 3-str
        matrix_titled.sort()

        not_enough = lkey - (ltext % lkey)
        take_letters = ltext // lkey
        if ltext % lkey != 0:
            take_letters += 1
        else:
            not_enough = 0
        not_enough_range = range(lkey - not_enough, lkey)

        cur_pos = 0

        def take_letters_from_key(n: int) -> str:
            nonlocal cur_pos
            ans = text[cur_pos:cur_pos + n]
            cur_pos += n
            assert len(ans) == n
            return ans

        for key, idx, lst in matrix_titled:
            to_take = take_letters
            if idx in not_enough_range:
                to_take -= 1
            lst += list(take_letters_from_key(to_take))

        matrix_titled.sort(key=lambda i: i[1])
        ans = []
        try:
            for row in range(len(matrix_titled[0][2])):
                for key, idx, lst in matrix_titled:
                    ans.append(lst[row])
        except IndexError:
            pass

        return "".join(ans)

    @classmethod
    def key_to_list(cls, key: str) -> list:
        raw_key = [(cls._letter_index(i[1]), i[0]) for i in enumerate(key)]
        raw_key_e = list(enumerate(raw_key))
        raw_key_e.sort(key=lambda i: i[1])
        raw_key_ee = list(enumerate(raw_key_e))
        raw_key_ee.sort(key=lambda i: i[1])
        ans = [i[0] for i in raw_key_ee]
        return ans


if __name__ == '__main__':
    key = "FKSIS"
    print(
        ColumnCrypt.decrypt(
            ColumnCrypt.encrypt("PTOI", key),
            key
        )
    )
    print(ColumnCrypt.decrypt("PTOI", "FKSIS"))
