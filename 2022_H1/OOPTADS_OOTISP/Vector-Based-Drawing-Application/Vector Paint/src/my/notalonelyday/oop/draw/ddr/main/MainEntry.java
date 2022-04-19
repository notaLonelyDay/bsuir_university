package my.notalonelyday.oop.draw.ddr.main;

import my.notalonelyday.oop.draw.ddr.controller.Controller;
import my.notalonelyday.oop.draw.ddr.model.DrawEngineImp;

public class MainEntry {
	public static void main(String[] args) {
		new Controller(DrawEngineImp.getUniqueInstance());
	}
}
