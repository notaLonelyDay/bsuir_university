package my.notalonelyday.oop.draw.ddr.view.drawers;

import my.notalonelyday.oop.draw.ddr.model.MainShape;

import java.awt.*;

abstract public class Drawer {
    protected MainShape shape;

    public Drawer(MainShape shape) {
        this.shape = shape;
    }

    abstract public void draw(Graphics canvas);
}
