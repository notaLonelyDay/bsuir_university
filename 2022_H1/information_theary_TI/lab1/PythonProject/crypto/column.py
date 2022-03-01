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
        pprint.pprint(sorted_enum)
        return "".join((''.join(i[1]) for i in sorted_enum))

    @classmethod
    def _decrypt_raw(cls, text: str, key: str):
        key = cls.key_to_list(key)
        matrix = [[] for _ in range(len(key))]
        matrix_titled = list(zip(key, range(len(key)), matrix))  # 0-key; 1-idx; 3-str
        matrix_titled.sort()

        take_letters = len(text) // len(key)
        if len(text) % len(key) != 0:
            take_letters += 1

        not_enough = len(key) - (len(text) % len(key))
        not_enough_range = range(len(key) - not_enough, len(key))

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
    key = "crypton"
    print(ColumnCrypt.key_to_list(key))
    print(
        ColumnCrypt.decrypt(
            ColumnCrypt.encrypt("case", key),
            key
        )
    )
