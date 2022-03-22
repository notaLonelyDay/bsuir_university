package com.notalonelyday.labs.lab3.shape.round

import com.notalonelyday.labs.lab3.shape.base.AbstractShape
import com.notalonelyday.labs.lab3.shape.base.DrawingFunction
import com.notalonelyday.labs.lab3.shape.base.DrawingPrimitive
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

class EllipseDrawingFunction(
    private val x0: Int,
    private val y0: Int,
    private val a: Int,
    private val b: Int,
) : DrawingFunction(
    x0 - a,
    x0 + a
) {

    override fun invoke(x: Int): List<DrawingPrimitive> {
        val y1 = sqrt((1 - (x - x0).toDouble().pow(2) / (a * a))) * b + y0
        val y2 = -sqrt((1 - (x - x0).toDouble().pow(2) / (a * a))) * b + y0
        return listOf(
            DrawingPrimitive.Point(y1.roundToInt()),
            DrawingPrimitive.Point(y2.roundToInt()),
        )
    }
}


open class Ellipse(
    x0: Int,
    y0: Int,
    a: Int,
    b: Int,
) : AbstractShape() {
    override val F_x: List<DrawingFunction> = listOf(
        EllipseDrawingFunction(x0, y0, a, b)
    )
}
