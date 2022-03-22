package com.notalonelyday.labs.lab3.shape.base

import java.awt.Point
import javax.swing.event.DocumentEvent
import kotlin.jvm.functions.FunctionN
import kotlin.math.max
import kotlin.math.min

sealed class DrawingPrimitive {
    data class Point(val y: Int) : DrawingPrimitive()
    data class Range(
        val fromY: Int,
        val toY: Int,
    ) : DrawingPrimitive()
}

abstract class DrawingFunction(
    private val _fromX: Int,
    private val _toX: Int,
) : AbstractFunction<List<DrawingPrimitive>>() {
    val fromX: Int = _fromX
        get() = min(field, _toX)
    val toX: Int = _toX
        get() = max(field, _fromX)
}


abstract class AbstractShape {
    abstract val F_x: List<DrawingFunction>
}