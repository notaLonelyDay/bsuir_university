package com.notalonelyday.labs.lab3.shape.polygon

import com.notalonelyday.labs.lab3.shape.base.AbstractShape
import com.notalonelyday.labs.lab3.shape.base.DrawingFunction
import com.notalonelyday.labs.lab3.shape.base.DrawingPrimitive
import com.notalonelyday.labs.lab3.shape.base.LineDrawingFunction

class Rectangle(
    x1: Int,
    y1: Int,
    x2: Int,
    y2: Int,
) : AbstractShape() {
    override val F_x: List<DrawingFunction> = listOf(
        LineDrawingFunction(x1, y1, x2, y1),
        LineDrawingFunction(x1, y2, x2, y2),
        LineDrawingFunction(x1, y1, x1, y2),
        LineDrawingFunction(x2, y1, x2, y2),
    )
}