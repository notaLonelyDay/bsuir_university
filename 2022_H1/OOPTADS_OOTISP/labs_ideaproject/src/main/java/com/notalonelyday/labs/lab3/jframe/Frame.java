package com.notalonelyday.labs.lab3.jframe;

import javax.swing.*;

public class Frame extends JFrame {

    Surface surface;
    Controls controls;
    public Frame(){
        surface = new Surface();
        controls = new Controls();
        initUi();
    }

    private void initUi(){
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        add(surface);
        add(controls);

        setTitle("Figures Painter");
        setSize(500,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
