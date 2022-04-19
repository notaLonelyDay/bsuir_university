package my.notalonelyday.oop.draw.ddr.model.shapes;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import my.notalonelyday.oop.draw.Shape;
import my.notalonelyday.oop.draw.ddr.model.MainShape;
import my.notalonelyday.oop.draw.ddr.view.drawers.Drawer;
import my.notalonelyday.oop.draw.ddr.view.drawers.TriangleDrawer;

public class Triangle extends MainShape {

	public Triangle() {
		super();
	}

	public Triangle(Point p1, Point p2, Point p3) {
		super();
		setPosition(p1);
		this.getProperties().put("x1", p1.getX());
		this.getProperties().put("y1", p1.getY());
		this.getProperties().put("x2", p2.getX());
		this.getProperties().put("y2", p2.getY());
		this.getProperties().put("x3", p3.getX());
		this.getProperties().put("y3", p3.getY());
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
		return new TriangleDrawer(this);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Shape clone = new Triangle();
		Map<String, Double> clone_prop = new HashMap<>();
		clone_prop.putAll(this.getProperties());
		clone.setProperties(clone_prop);
		return clone;
	}

	@Override
	public Point[] getBonds() {
		int x1 = getProperties().get("x1").intValue();
		int x2 = getProperties().get("x2").intValue();
		int x3 = getProperties().get("x3").intValue();
		int y1 = getProperties().get("y1").intValue();
		int y2 = getProperties().get("y2").intValue();
		int y3 = getProperties().get("y3").intValue();
		Point p1 = new Point(Math.min(Math.min(x1, x2), x3), Math.min(Math.min(y1, y2), y3));
		Point p4 = new Point(Math.max(Math.max(x1, x2), x3), Math.max(Math.max(y1, y2), y3));
		Point p2 = new Point(p1.x + (p4.x - p1.x), p1.y);
		Point p3 = new Point(p1.x, p1.y + (p4.y - p1.y));
		return new Point[] { p1, p2, p3, p4 };
	}

}