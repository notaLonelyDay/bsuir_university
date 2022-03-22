package com.notalonelyday.labs.lab3.jframe

import com.notalonelyday.labs.lab3.shape.base.AbstractShape
import java.awt.*
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.JPanel

class Surface : JPanel(), MouseListener {
    private fun doDrawing(graphics: Graphics) {
        val g2d = graphics as Graphics2D
        g2d.paint = Color(120, 120, 120)
        val rh = RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON)
        rh[RenderingHints.KEY_RENDERING] = RenderingHints.VALUE_RENDER_QUALITY
        g2d.setRenderingHints(rh)
    }

    var g: Graphics? = null
    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        doDrawing(g)
        this.g = g
        addMouseListener(this)
    }

    fun drawShape(shape: AbstractShape?) {
        g!!.drawShape(shape!!, 1)
    }

    val points = mutableListOf<Point>()

    private fun addPoint(p: Point){
        if (points.lastOrNull() != p) {
            points.add(p)
            refreshPoints()
        }
    }

    private fun refreshPoints(){

    }

    override fun mouseClicked(mouseEvent: MouseEvent) {
        addPoint(mouseEvent.point)
    }
    override fun mousePressed(mouseEvent: MouseEvent) {}
    override fun mouseReleased(mouseEvent: MouseEvent) {}
    override fun mouseEntered(mouseEvent: MouseEvent) {}
    override fun mouseExited(mouseEvent: MouseEvent) {}
}