package com.notalonelyday.labs.lab3.factory

import com.notalonelyday.labs.lab3.shape.base.AbstractShape
import java.awt.Point

abstract class AbstractShapeFactory {
    abstract val name: String
    abstract val neededPoints: Int
    abstract fun create(vararg ps: Point): AbstractShape
}