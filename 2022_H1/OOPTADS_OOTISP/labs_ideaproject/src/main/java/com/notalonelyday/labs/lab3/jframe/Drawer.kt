package com.notalonelyday.labs.lab3.jframe

import com.notalonelyday.labs.lab3.shape.base.AbstractShape
import com.notalonelyday.labs.lab3.shape.base.DrawingPrimitive
import java.awt.Graphics

fun Graphics.drawPoint(x: Double, y: Double) {
    drawLine(x.toInt(), y.toInt(), x.toInt(), y.toInt());
}

fun Graphics.drawPrimitive(x: Double, primitive: DrawingPrimitive) {
    when(primitive){
        is DrawingPrimitive.Point -> {
            drawPoint(x, primitive.y)
        }
        is DrawingPrimitive.Range -> {
            for ()
        }
    }
}

fun Graphics.drawShape(shape: AbstractShape, step: Double) {
    for (f in shape.F_x) {
        var x = f.fromX
        while (x <= f.toX) {
            f.invoke(x).forEach { drawPrimitive(x, it) }
            x += step
        }
    }
}
