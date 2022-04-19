package my.notalonelyday.oop.draw.ddr.view.drawers;

import my.notalonelyday.oop.draw.ddr.model.MainShape;

import java.awt.*;
import java.util.Map;

public class LineDrawer extends Drawer {
    public LineDrawer(MainShape shape) {
        super(shape);
    }

    @Override
    public void draw(Graphics canvas) {
        Map<String, Double> properties = shape.getProperties();
        Graphics2D g = (Graphics2D) canvas;
        g.setColor(new Color(properties.get("fill_color").intValue()));
        g.drawLine(properties.get("x1").intValue(), properties.get("y1").intValue(),
                properties.get("x2").intValue(), properties.get("y2").intValue());
        g.setColor(new Color(properties.get("color").intValue()));
    }
}
