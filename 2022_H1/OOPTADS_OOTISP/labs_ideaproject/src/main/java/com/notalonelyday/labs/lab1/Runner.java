package com.notalonelyday.labs.lab1;

public class Runner {
    public static void main(String[] args) {
        Computer pc = new Computer();

        pc.name = "MyPc";
        System.out.println("Pc name: "+ pc.name);

        System.out.println("Pc state: "+ pc.getState());
        pc.startWork();
        System.out.println("Pc state: "+ pc.getState());
        pc.startWork(Computer.STATE.WORKING);
        System.out.println("Pc state: "+ pc.getState());

        System.out.println("Needed ram size for 10 cores: " + Computer.calculateNeededRamSize(10));

        System.out.println("Pc ram: "+ pc.getRamSize());
        pc.setRamSize(4084);
        System.out.println("Pc ram: "+ pc.getRamSize());
        Runtime.getRuntime().gc();
        System.gc();
    }
}
