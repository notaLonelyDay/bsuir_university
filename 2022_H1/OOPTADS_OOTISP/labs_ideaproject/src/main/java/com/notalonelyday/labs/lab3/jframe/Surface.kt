package com.notalonelyday.labs.lab3.jframe

import com.notalonelyday.labs.lab3.shape.base.AbstractShape
import com.notalonelyday.labs.lab3.shape.line.StraightLine
import java.awt.*
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.border.Border

object Surface : JPanel(), MouseListener {

    init {
        addMouseListener(this)
        isVisible = true
        border = BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED)
    }
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
    val shapes = mutableListOf<AbstractShape>()
    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        doDrawing(g)
        this.g = g
        shapes.forEach { g.drawShape(it) }
    }

    fun drawShape(shape: AbstractShape) {
        shapes.add(shape)
        repaint()
    }

    val points = mutableListOf<Point>()

    private fun addPoint(p: Point){
        println(p)
        if (points.lastOrNull() != p) {
            points.add(0, p)
            refreshPoints()
            Controls.addPoint(p)
        }
    }

    private fun refreshPoints(){

    }

    override fun mouseClicked(mouseEvent: MouseEvent) {
        addPoint(mouseEvent.point)
    }
    override fun mousePressed(mouseEvent: MouseEvent) {
    }
    override fun mouseReleased(mouseEvent: MouseEvent) {
    }
    override fun mouseEntered(mouseEvent: MouseEvent) {
    }
    override fun mouseExited(mouseEvent: MouseEvent) {
    }
}