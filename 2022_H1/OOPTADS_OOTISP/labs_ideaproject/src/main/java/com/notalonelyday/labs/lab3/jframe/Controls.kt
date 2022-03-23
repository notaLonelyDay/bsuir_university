package com.notalonelyday.labs.lab3.jframe

import java.awt.*
import javax.swing.*
import javax.swing.table.DefaultTableModel


object Controls : JPanel() {

    val tableNames = arrayOf("Point")
    val table: JTable
    val model = DefaultTableModel()

    init {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        val jComboBox = JComboBox<String>(
            shapes.map { it.name }.toTypedArray()
        )
        val jButton = JButton("Draw")
        jButton.addActionListener {
            val factory = shapes[jComboBox.selectedIndex]
            val shape = factory.create(*Surface.points.toTypedArray())
            Surface.drawShape(shape)
        }


        table = JTable(arrayOf(
        ), tableNames)
        model.addColumn("POINT")
        table.model = model

        add(table)
        add(jComboBox)
        add(jButton)

    }

    fun addPoint(p: Point) {
        model.insertRow(0, arrayOf(p.toString()))
        model.fireTableRowsInserted(0, 1)
    }
}