package my.notalonelyday.oop.draw.ddr.view.drawers;

import my.notalonelyday.oop.draw.ddr.model.MainShape;

import java.awt.*;
import java.util.Map;

public class SquareDrawer extends Drawer {
    public SquareDrawer(MainShape shape) {
        super(shape);
    }

    @Override
    public void draw(Graphics canvas) {
        Map<String, Double> properties = shape.getProperties();
        Graphics2D g = (Graphics2D) canvas;
        g.setColor(new Color(properties.get("fill_color").intValue()));
        g.fillRect(properties.get("x").intValue(), properties.get("y").intValue(),
                properties.get("width").intValue(), properties.get("width").intValue());
        g.setColor(new Color(properties.get("color").intValue()));
        g.drawRect(properties.get("x").intValue(), properties.get("y").intValue(),
                properties.get("width").intValue(), properties.get("width").intValue());
    }
}
