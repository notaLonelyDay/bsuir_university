package com.notalonelyday.labs.lab2;

public class Main {
    public static void main(String[] args) {
        Plane plane = new Plane();
        plane.startUsage();

        plane.fly();
        plane.land();
        plane.fly();
        plane.crashPlane();

        plane.endUsage();
    }
}
