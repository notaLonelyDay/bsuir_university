package com.notalonelyday.labs.lab3.shape.base

import javax.swing.event.DocumentEvent
import kotlin.jvm.functions.FunctionN

sealed class DrawingPrimitive {
    data class Point(val y: Double) : DrawingPrimitive()
    data class Range(
        val fromY: Double,
        val toY: Double,
    ) : DrawingPrimitive()
}

abstract class DrawingFunction(
    val fromX: Double,
    val toX: Double
) : AbstractFunction<List<DrawingPrimitive>>() {
}



abstract class AbstractShape {
    abstract val F_x: List<DrawingFunction>
}