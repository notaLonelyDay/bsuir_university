package com.notalonelyday.labs.lab1;

class Base{
    public Base() {
        System.out.println("CONSTRUCTOR CALLED");
    }
}

public class Computer extends Base {
    private STATE state;
    private int ramSize;
    private int cpuCount;
    public String name = "";

    private static int RAM_PER_CORE = 128;
    public static int calculateNeededRamSize(int cpuCount){
        return cpuCount * RAM_PER_CORE;
    }

    public Computer() {
        System.out.println("Constructor called");
        ramSize = 2048;
        cpuCount = 8;
        state = STATE.POWERED_OFF;
    }
    public Computer(STATE startState) {
        super();
        state = startState;
    }

    public void startWork(){
        if(state == STATE.POWERED_OFF || state == STATE.IDLE) {
            state = STATE.WORKING;
            System.out.println("Work started");
        } else
            throw new IllegalStateException();
    }
    public void startWork(STATE fromState){
        if(state == fromState) {
            state = STATE.WORKING;
            System.out.println("Work started");
        } else
            throw new IllegalStateException();
    }


    public int getRamSize() {
        return ramSize;
    }

    public void setRamSize(int ramSize) {
        this.ramSize = ramSize;
    }

    public int getCpuCount() {
        return cpuCount;
    }

    public void setCpuCount(int cpuCount) {
        this.cpuCount = cpuCount;
    }

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("Destructor called");
        state = STATE.POWERED_OFF;
    }

    public enum STATE {
        POWERED_OFF,
        IDLE,
        WORKING
    }
}
