package com.notalonelyday.labs.lab3.shape.line

import com.notalonelyday.labs.lab3.shape.base.AbstractShape
import com.notalonelyday.labs.lab3.shape.base.DrawingFunction
import com.notalonelyday.labs.lab3.shape.base.LineDrawingFunction

class StraightLine(
    x1: Double,
    y1: Double,
    x2: Double,
    y2: Double,
) : AbstractShape() {
    override val F_x: List<DrawingFunction> = listOf(
        LineDrawingFunction(
            x1, y1, x2, y2
        )
    )
}