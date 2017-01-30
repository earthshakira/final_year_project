package com.iridium;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by earthshakira on 30/1/17.
 */
public class Screen extends JPanel{

    public Screen(){
        repaint();
    }

    public void  paint(java.awt.Graphics g){
        File op=new File("predict.out");
        try {
            Scanner scr = new Scanner(op);
            int i=0;
            while(scr.hasNext()){
                int emotion=Integer.parseInt(scr.nextLine());
                    switch (emotion){
                        case 0://neutral
                            g.setColor(Color.white);
                            g.drawLine(i,0,i,50);
                            break;
                        case 1://anger
                            g.setColor(Color.red);
                            g.drawLine(i,0,i,50);
                            break;
                        case 2://boredom
                            g.setColor(Color.gray);
                            g.drawLine(i,0,i,50);
                            break;
                        case 3://disgust
                            g.setColor(Color.green);
                            g.drawLine(i,0,i,50);
                            break;
                        case 4://fear
                            g.setColor(Color.magenta);
                            g.drawLine(i,0,i,50);
                            break;
                        case 5://happiness
                            g.setColor(Color.yellow);
                            g.drawLine(i,0,i,50);
                            break;
                        case 6://sadness
                            g.setColor(Color.black);
                            g.drawLine(i,0,i,50);
                            break;

                    }
                    i++;
                }
            }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
