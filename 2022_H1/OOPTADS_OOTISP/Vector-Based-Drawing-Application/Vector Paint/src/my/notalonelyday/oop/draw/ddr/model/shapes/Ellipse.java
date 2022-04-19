package my.notalonelyday.oop.draw.ddr.model.shapes;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import my.notalonelyday.oop.draw.Shape;
import my.notalonelyday.oop.draw.ddr.model.MainShape;
import my.notalonelyday.oop.draw.ddr.view.drawers.Drawer;
import my.notalonelyday.oop.draw.ddr.view.drawers.EllipseDrawer;

public class Ellipse extends MainShape {

	public Ellipse() {
		super();
	}

	public Ellipse(Point position, double width, double height) {
		super();
		this.setPosition(position);
		getProperties().put("x", position.getX());
		getProperties().put("y", position.getY());
		getProperties().put("width", new Double(width));
		getProperties().put("height", new Double(height));
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
		return new EllipseDrawer(this);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Shape clone = new Ellipse();
		Map<String, Double> clone_prop = new HashMap<>();
		clone_prop.putAll(this.getProperties());
		clone.setProperties(clone_prop);
		return clone;
	}

	@Override
	public Point[] getBonds() {
		Point p1 = new Point(getProperties().get("x").intValue(), getProperties().get("y").intValue());
		Point p2 = new Point(getProperties().get("x").intValue() + getProperties().get("width").intValue(),
				getProperties().get("y").intValue());
		Point p3 = new Point(p1.x, p1.y + getProperties().get("height").intValue());
		Point p4 = new Point(p1.x + getProperties().get("width").intValue(),
				p1.y + getProperties().get("height").intValue());
		return new Point[] { p1, p2, p3, p4 };
	}

}
