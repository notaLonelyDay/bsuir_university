package com.notalonelyday.labs.lab3.jframe;


import com.notalonelyday.labs.lab3.shape.line.StraightLine;

import javax.swing.*;
import java.awt.*;

public class Surface extends JPanel {

    private void doDrawing(Graphics graphics){

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setPaint(new Color(120,120,120));

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
        drawFigures(g);
    }

    public void drawFigures(Graphics graphics){
        DrawerKt.drawShape(graphics,new StraightLine(
            10,10,10,100
        ), 1);
    }
}
