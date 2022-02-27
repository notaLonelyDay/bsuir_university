package com.notalonelyday.labs.lab2;

final public class Plane extends UsageLogger implements Flyable {
    @Override
    public void fly() {
        System.out.println("Plane started flying");
    }

    @Override
    public void land() {
        System.out.println("Plane landed");
    }

    @Override
    void log(String s) {
        System.out.println(s);
    }

    final void crashPlane(){
        System.out.println("Plane crushed");
    }
}
