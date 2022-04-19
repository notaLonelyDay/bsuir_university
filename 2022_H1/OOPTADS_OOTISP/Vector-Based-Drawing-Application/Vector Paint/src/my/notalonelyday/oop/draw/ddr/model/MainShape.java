package my.notalonelyday.oop.draw.ddr.model;

import my.notalonelyday.oop.draw.Shape;
import my.notalonelyday.oop.draw.ddr.view.drawers.Drawer;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class MainShape implements Shape {
    private Map<String, Double> properties;
    private Point position;
    private Color default_color = Color.BLUE;
    private Color default_fill_color = Color.RED;

    public MainShape() {
        properties = new HashMap<String, Double>();
        properties.put("selected", 0.0);
        properties.put("color", default_color.getRGB() * 1.0);
        properties.put("fill_color", default_fill_color.getRGB() * 1.0);
        properties.put("bond_1_x", -1.0);
        properties.put("bond_1_y", -1.0);
        properties.put("bond_2_x", -1.0);
        properties.put("bond_2_y", -1.0);
        properties.put("bond_3_x", -1.0);
        properties.put("bond_3_y", -1.0);
        properties.put("bond_4_x", -1.0);
        properties.put("bond_4_y", -1.0);
    }

    @Override
    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void setProperties(Map<String, Double> properties) {
        this.properties = properties;
    }

    @Override
    public Map<String, Double> getProperties() {
        return properties;
    }

    @Override
    public void setColor(Color color) {
        properties.put("color", color.getRGB() * 1.0);
    }

    @Override
    public Color getColor() {
        return new Color(properties.get("color").intValue());
    }

    @Override
    public void setFillColor(Color color) {
        properties.put("fill_color", color.getRGB() * 1.0);
    }

    @Override
    public Color getFillColor() {
        return new Color(getProperties().get("fill_color").intValue());
    }

    @Override
    public Drawer getDrawer() {
        return null;
    }

    public abstract Object clone() throws CloneNotSupportedException;

    public abstract Point[] getBonds();
}
