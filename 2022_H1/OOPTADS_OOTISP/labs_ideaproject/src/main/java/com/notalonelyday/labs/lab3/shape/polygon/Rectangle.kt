package com.notalonelyday.labs.lab3.shape.polygon

import com.notalonelyday.labs.lab3.shape.base.DrawingFunction
import com.notalonelyday.labs.lab3.shape.base.DrawingPrimitive

class RectangleDrawingFunction(
    x1: Double,
    y1: Double,
    x2: Double,
    y2: Double,
) : DrawingFunction(
    x1, x2
) {
    override fun invoke(x: Double): List<DrawingPrimitive> {
        TODO("Not yet implemented")
    }

}

class Rectangle() {
}