package com.notalonelyday.labs.lab3.shape.polygon

import com.notalonelyday.labs.lab3.shape.base.AbstractShape
import com.notalonelyday.labs.lab3.shape.base.DrawingFunction
import com.notalonelyday.labs.lab3.shape.base.LineDrawingFunction
import kotlin.math.abs

open class Rhombus(
    x1: Int,
    y1: Int,
    x2: Int,
    y2: Int,
) : AbstractShape() {
    val w = abs(x1-x2)
    val h = abs(y1-y2)
    override val F_x: List<DrawingFunction> = listOf(
        LineDrawingFunction(x1, y1, x2, y2),
        LineDrawingFunction(x2, y2, x2+w, y1),
        LineDrawingFunction(x2+w, y1, x2, y1-h),
        LineDrawingFunction(x2, y1-h, x1, y1),
    )
}