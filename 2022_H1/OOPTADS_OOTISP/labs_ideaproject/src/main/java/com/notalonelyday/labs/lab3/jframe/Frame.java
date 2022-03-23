package com.notalonelyday.labs.lab3.jframe;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    Surface surface;
    Controls controls;
    public Frame(){
        surface = Surface.INSTANCE;
        controls = Controls.INSTANCE;
        initUi();
    }

    private void initUi(){
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 10;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(surface, constraints);
        GridBagConstraints constraints1 = new GridBagConstraints();
        constraints1.weightx = 1;
        constraints.weighty = 1;
        constraints1.fill = GridBagConstraints.HORIZONTAL;
        add(controls, constraints1);

        setTitle("Figures Painter");
        setSize(500,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        surface.setVisible(true);
        controls.setVisible(true);

    }

}
