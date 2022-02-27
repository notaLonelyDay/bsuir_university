package com.notalonelyday.labs.lab2;

abstract class UsageLogger {
    protected String prefix = "[INFO] ";
    abstract void log(String s);
    void logWithPrefix(String s){
       log(prefix + s);
    }
    void startUsage(){
        logWithPrefix("Usage started");
    }
    void endUsage(){
        logWithPrefix("Usage ended");
    }
}
