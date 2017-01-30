package com.iridium;

import javax.swing.*;

/**
 * Created by earthshakira on 30/1/17.
 */
public class Frame extends JFrame{
    Screen s;

    public Frame(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400,80);
        setResizable(false);
        setTitle("Emotion Graph");
        init();
    }

    public void init(){
        setLocationRelativeTo(null);
        s=new Screen();
        add(s);
        setVisible(true);
    }

}

/*

 */