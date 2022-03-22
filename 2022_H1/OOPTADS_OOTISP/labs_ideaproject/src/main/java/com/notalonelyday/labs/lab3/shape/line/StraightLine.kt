package com.notalonelyday.labs.lab3.shape.line

import com.notalonelyday.labs.lab3.shape.base.AbstractShape
import com.notalonelyday.labs.lab3.shape.base.DrawingFunction
import com.notalonelyday.labs.lab3.shape.base.LineDrawingFunction
import java.awt.Point

class StraightLine(
    x1: Int,
    y1: Int,
    x2: Int,
    y2: Int,
) : AbstractShape() {

    companion object{
        const val pointsNeeded = 2

        fun create(vararg ps: Point): StraightLine {
            val fp = ps[0]
            val sp = ps[1]
            return StraightLine(
                fp.x,
                fp.y,
                sp.x,
                sp.y
            )
        }
    }

    override val F_x: List<DrawingFunction> = listOf(
        LineDrawingFunction(
            x1, y1, x2, y2
        )
    )
}