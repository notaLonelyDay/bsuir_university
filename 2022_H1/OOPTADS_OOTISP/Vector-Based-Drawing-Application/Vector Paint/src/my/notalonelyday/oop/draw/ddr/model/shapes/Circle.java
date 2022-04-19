package my.notalonelyday.oop.draw.ddr.model.shapes;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import my.notalonelyday.oop.draw.Shape;
import my.notalonelyday.oop.draw.ddr.model.MainShape;
import my.notalonelyday.oop.draw.ddr.view.drawers.CircleDrawer;
import my.notalonelyday.oop.draw.ddr.view.drawers.Drawer;

public class Circle extends MainShape {

    public Circle() {
        super();
    }

    public Circle(Point position, double radius) {
        super();
        setPosition(position);
        getProperties().put("x", getPosition().getX());
        getProperties().put("y", getPosition().getY());
        getProperties().put("radius", new Double(radius));
        getProperties().put("bond_1_x", getBonds()[0].getX());
        getProperties().put("bond_1_y", getBonds()[0].getY());
        getProperties().put("bond_2_x", getBonds()[1].getX());
        getProperties().put("bond_2_y", getBonds()[1].getY());
        getProperties().put("bond_3_x", getBonds()[2].getX());
        getProperties().put("bond_3_y", getBonds()[2].getY());
        getProperties().put("bond_4_x", getBonds()[3].getX());
        getProperties().put("bond_4_y", getBonds()[3].getY());
    }

    @Override
    public Drawer getDrawer() {
        return new CircleDrawer(this);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Shape clone = new Circle();
        Map<String, Double> clone_prop = new HashMap<>();
        clone_prop.putAll(this.getProperties());
        clone.setProperties(clone_prop);
        return clone;
    }

    public Point[] getBonds() {
        Point p1 = new Point(getProperties().get("x").intValue() - getProperties().get("radius").intValue(),
                getProperties().get("y").intValue() - getProperties().get("radius").intValue());
        Point p2 = new Point(getProperties().get("x").intValue() + getProperties().get("radius").intValue(),
                getProperties().get("y").intValue());
        Point p3 = new Point(getProperties().get("x").intValue(),
                getProperties().get("y").intValue() + getProperties().get("radius").intValue());
        Point p4 = new Point(getProperties().get("x").intValue() + getProperties().get("radius").intValue(),
                getProperties().get("y").intValue() + getProperties().get("radius").intValue());
        return new Point[]{p1, p2, p3, p4};
    }
}
