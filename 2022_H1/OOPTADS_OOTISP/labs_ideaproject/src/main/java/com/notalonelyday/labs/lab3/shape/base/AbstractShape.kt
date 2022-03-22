package com.notalonelyday.labs.lab3.shape.base

import javax.swing.event.DocumentEvent
import kotlin.jvm.functions.FunctionN

sealed class DrawingPrimitive {
    data class Point(val y: Int) : DrawingPrimitive()
    data class Range(
        val fromY: Int,
        val toY: Int,
    ) : DrawingPrimitive()
}

abstract class DrawingFunction(
    val fromX: Int,
    val toX: Int
) : AbstractFunction<List<DrawingPrimitive>>() {
}



abstract class AbstractShape {
    abstract val F_x: List<DrawingFunction>
}