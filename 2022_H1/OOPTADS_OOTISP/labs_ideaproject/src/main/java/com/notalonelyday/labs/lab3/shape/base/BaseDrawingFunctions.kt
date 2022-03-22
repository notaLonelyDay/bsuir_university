package com.notalonelyday.labs.lab3.shape.base

import kotlin.math.abs

class LineDrawingFunction(
    private val x1: Double,
    private val y1: Double,
    private val x2: Double,
    private val y2: Double,
) : DrawingFunction(
    x1,
    x2
) {
    private val k = abs(y1 - y2) / abs(x1 - x2);
    private val b = y1 - k * x1;

    override fun invoke(x: Double): List<DrawingPrimitive> {
        return if (k == Double.POSITIVE_INFINITY) {
            listOf(DrawingPrimitive.Range(y1, y2))
        } else {
            listOf(DrawingPrimitive.Point(x * k + b));
        }
    }
}