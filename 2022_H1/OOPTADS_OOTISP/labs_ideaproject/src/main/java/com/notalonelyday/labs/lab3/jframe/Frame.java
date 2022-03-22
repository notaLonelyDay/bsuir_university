package com.notalonelyday.labs.lab3.jframe;

import javax.swing.*;

public class Frame extends JFrame {

    Surface surface;
    public Frame(){
        surface = new Surface();
        initUi();
    }

    private void initUi(){
        add(surface);

        setTitle("Figures Painter");
        setSize(500,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
