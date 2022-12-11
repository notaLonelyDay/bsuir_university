import numpy as np
from typing import Callable, List
from scipy.optimize import minimize


def f(x):
    X1, X2, X3, X4, X5 = x
    return 900 * 10 / X1 \
           + 0.5 * 5 * X1 \
           + 700 * 5 / X2 \
           + 0.5 * 15 * X2 \
           + 300 * 20 / X3 \
           + 0.5 * 10 * X3 \
           + 1000 * 30 / X4 \
           + 0.5 * 2 * X4 \
           + 200 * 6 / X5 \
           + 0.5 * 3 * X5


def coordinate_descent(func: Callable[..., float],
                       x0: List[float],
                       odm: Callable[[Callable[[float], float], float, float], float],
                       eps: float = 0.0001,
                       step_crushing_ratio: float = 0.99):
    k = 0
    N = len(x0)
    h = np.array([1.0] * N)
    x_points = [x0]

    while h[0] > eps:
        x_points.append([0] * N)
        for i in range(N):
            args = x_points[k].copy()

            def odm_func(x):
                nonlocal i, func, args
                args[i] = x
                return func(*args)

            ak = odm(odm_func, args[i], h[i])

            x_points[k + 1][i] = ak

        if np.linalg.norm(np.array(x_points[k + 1]) - np.array(x_points[k])) <= eps:
            break

        k += 1
        h *= step_crushing_ratio

    return x_points[len(x_points) - 1]


def odm(fnc, x0, h):
    res = minimize(fnc, x0, method='nelder-mead', options={'xatol': h, 'disp': False})
    return res.x[0]


res = coordinate_descent(lambda *args: f(args), [1., 1., 1., 1., 1.], odm)
for id, i in enumerate(res):
    print(f"q{id}={i}")
print("f=" + str(f(coordinate_descent(lambda *args: f(args), [1., 1., 1., 1., 1.], odm))))
