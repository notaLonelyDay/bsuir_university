package com.notalonelyday.labs.lab3.jframe

import com.notalonelyday.labs.lab3.shape.base.AbstractShape
import java.awt.*
import java.awt.event.MouseEvent
import javax.swing.BoxLayout
import javax.swing.JComboBox
import javax.swing.JPanel

class Controls : JPanel() {

    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        val jComboBox = JComboBox<String>(arrayOf("test", "test2"))
        add(jComboBox)
    }
}