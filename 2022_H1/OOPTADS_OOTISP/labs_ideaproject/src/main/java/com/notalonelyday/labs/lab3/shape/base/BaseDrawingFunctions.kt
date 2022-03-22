package com.notalonelyday.labs.lab3.shape.base

import kotlin.math.abs
import kotlin.math.round
import kotlin.math.roundToInt

class LineDrawingFunction(
    private val x1: Int,
    private val y1: Int,
    private val x2: Int,
    private val y2: Int,
) : DrawingFunction(
    x1,
    x2
) {
    private val k = abs(y1 - y2).toDouble() / abs(x1 - x2);
    private val b = y1 - k * x1;

    override fun invoke(x: Int): List<DrawingPrimitive> {
        return if (k == Double.POSITIVE_INFINITY) {
            listOf(DrawingPrimitive.Range(y1, y2))
        } else {
            listOf(DrawingPrimitive.Point((x * k + b).roundToInt()))
        }
    }
}