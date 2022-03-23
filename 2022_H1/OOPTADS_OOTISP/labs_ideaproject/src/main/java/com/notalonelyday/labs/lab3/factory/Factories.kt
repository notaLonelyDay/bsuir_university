package com.notalonelyday.labs.lab3.factory

import com.notalonelyday.labs.lab3.shape.line.StraightLine
import com.notalonelyday.labs.lab3.shape.polygon.Rectangle
import com.notalonelyday.labs.lab3.shape.polygon.Rhombus
import com.notalonelyday.labs.lab3.shape.polygon.Square
import com.notalonelyday.labs.lab3.shape.round.Circle
import com.notalonelyday.labs.lab3.shape.round.Ellipse
import java.awt.Point
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

object StraightLineFactory : AbstractShapeFactory() {
    override val name: String = "Straight Line"
    override val neededPoints: Int = 2
    override fun create(vararg ps: Point): StraightLine {
        val fp = ps[1]
        val sp = ps[0]
        return StraightLine(fp.x, fp.y, sp.x, sp.y)
    }
}

object RectangleFactory : AbstractShapeFactory() {
    override val name: String = "Rectangle"
    override val neededPoints: Int = 2
    override fun create(vararg ps: Point): Rectangle {
        val fp = ps[1]
        val sp = ps[0]
        return Rectangle(fp.x, fp.y, sp.x, sp.y)
    }
}

object RhombusFactory : AbstractShapeFactory() {
    override val name: String = "Rhombus"
    override val neededPoints: Int = 2
    override fun create(vararg ps: Point): Rhombus {
        val fp = ps[1]
        val sp = ps[0]
        return Rhombus(fp.x, fp.y, sp.x, sp.y)
    }
}

fun Point.lengthTo(p: Point): Int {
    return sqrt((x - p.x).toDouble().pow(2) + (y - p.y).toDouble().pow(2)).roundToInt()
}

object SquareFactory : AbstractShapeFactory() {
    override val name: String = "Square"
    override val neededPoints: Int = 2
    override fun create(vararg ps: Point): Square {
        val fp = ps[1]
        val sp = ps[0]
        return Square(fp.x, fp.y, fp.lengthTo(sp))
    }
}

object CircleFactory : AbstractShapeFactory() {
    override val name: String = "Circle"
    override val neededPoints: Int = 2
    override fun create(vararg ps: Point): Circle {
        val fp = ps[1]
        val sp = ps[0]
        return Circle(fp.x, fp.y, fp.lengthTo(sp))
    }
}

object EllipseFactory : AbstractShapeFactory() {
    override val name: String = "Ellipse"
    override val neededPoints: Int = 2
    override fun create(vararg ps: Point): Ellipse {
        val fp = ps[1]
        val sp = ps[0]
        return Ellipse(fp.x, fp.y, abs(fp.x - sp.x), abs(fp.y - sp.y))
    }
}
