package com.notalonelyday.labs.lab3.shape.polygon


class Square(
    x: Int,
    y: Int,
    h: Int,
) : Rectangle(
    x - h,
    y - h,
    x + h,
    y + h,
)
