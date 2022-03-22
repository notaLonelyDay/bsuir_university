package com.notalonelyday.labs.lab3.jframe

import com.notalonelyday.labs.lab3.shape.base.AbstractShape
import com.notalonelyday.labs.lab3.shape.base.DrawingPrimitive
import java.awt.Graphics

fun Graphics.drawPoint(x: Int, y: Int) {
    drawLine(x, y, x, y);
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
