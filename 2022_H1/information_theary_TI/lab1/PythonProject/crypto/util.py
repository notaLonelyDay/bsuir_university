def is_co_prime(a: int, b: int):
    return compute_gcd(a, b) == 1


def compute_gcd(x, y):
    while y:
        x, y = y, x % y
    return x
