package com.notalonelyday.labs.lab3.jframe

import com.notalonelyday.labs.lab3.shape.base.AbstractShape
import com.notalonelyday.labs.lab3.shape.base.DrawingPrimitive
import com.notalonelyday.labs.lab3.shape.line.StraightLine
import com.notalonelyday.labs.lab3.shape.polygon.Rectangle
import com.notalonelyday.labs.lab3.shape.polygon.Rhombus
import com.notalonelyday.labs.lab3.shape.polygon.Square
import com.notalonelyday.labs.lab3.shape.round.Circle
import com.notalonelyday.labs.lab3.shape.round.Ellipse
import java.awt.Graphics

val shapes = listOf(
    StraightLine::class,
    Rectangle::class,
    Rhombus::class,
    Square::class,
    Circle::class,
    Ellipse::class
)

fun Graphics.drawPoint(x: Int, y: Int) {
//    drawLine(x, y, x, y);
    drawOval(x,y,2,2)
}

fun Graphics.drawPrimitive(x: Int, p: DrawingPrimitive, step: Int) {
    when (p) {
        is DrawingPrimitive.Point -> {
            drawPoint(x, p.y)
        }
        is DrawingPrimitive.Range -> {
            for (y in p.fromY..p.toY step step) {
                drawPoint(x, y)
            }
        }
    }
}

fun Graphics.drawShape(shape: AbstractShape, step: Int = 1) {
    for (f in shape.F_x) {
        for (x in f.fromX..f.toX step step) {
            f.invoke(x).forEach { drawPrimitive(x, it, step) }
        }
    }
}
