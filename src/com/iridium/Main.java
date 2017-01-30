package com.iridium;

//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..
import org.omg.CORBA.INTERNAL;

import javax.swing.*;
import java.awt.Frame;
import java.io.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    //Note: Typically the main method will be in a
    //separate class. As this is a simple one class
    //example it's all in the one class.
    public static void main(String[] args) {

        new Main();
    }

    public Main()
    {
        JFrame guiFrame = new JFrame();
        //make sure the program exits when the frame closes
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Speech Emotion Recognition System");
        guiFrame.setSize(720,640);

        //This will center the JFrame in the middle of the screen
        guiFrame.setLocationRelativeTo(null);

        JButton selectFile = new JButton("Select File");
        JTextArea displayer = new JTextArea();

        selectFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OpenFile of = new OpenFile();
                int ret=-1;
                try{
                    ret=of.pickMe();
                }catch (Exception er){
                    er.printStackTrace();
                }finally {
                    switch (ret){
                        case 0:
                            JOptionPane.showMessageDialog(null, "Error! File could not be opened");
                            break;
                        case 1:
                            JOptionPane.showMessageDialog(null, "Error! File is not of type wav");
                            break;
                        case 2:
                            displayer.setText("\n\n\n");
                            displayer.append("Log: File "+of.name+" successfully opened");
                            makefile(displayer);
                            break;
                        case 3:break;
                        default:
                            JOptionPane.showMessageDialog(null, "Error! Program has Malfunctioned");
                            break;

                    }
                }
            }
        });
        guiFrame.add(selectFile,BorderLayout.NORTH);
        guiFrame.add(displayer);
        //make sure the JFrame is visible
        guiFrame.setVisible(true);
    }

    boolean makefile(JTextArea disp){
        disp.append("\nExtracting Features");
        String output = executeCommand("python3 extract_features.py test.wav");
        disp.append("\n"+output);
        disp.append("\nFile Details:");
        output = executeCommand("ls -l | grep mfcc_file.te");
        disp.append("\n"+output);
        disp.append("\nScaling the features to set in [-1,1]...");
        output = executeCommand("./svm-scale mfcc_file.te");
        disp.append("\nWriting Scaled File..");
        try{
            PrintWriter writer = new PrintWriter("mfcc_file_scale.te", "UTF-8");
            writer.print(output);
            writer.close();
        } catch (IOException e) {
            // do something
        }
        disp.append("\nFile Write Complete");
        disp.append("\nPredicting Output...");
        output = executeCommand("./svm-predict mfcc_file_scale.te emo_final_scale.tr.model predict.out");
        disp.append("\nPrediction Complete!");
        generateOutput(disp);
        return false;
    }

    private String executeCommand(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }

    private void generateOutput(JTextArea disp){
        File op=new File("predict.out");
        try {
            Scanner scr = new Scanner(op);
            int i = 0;
            int[] emotionCount=new int[7];
            String[] emotion={"neutral","anger","boredom","disgust","fear","happiness","sadness"};
            double probability[] = new double[7];
            while (scr.hasNext()) {
                int num = Integer.parseInt(scr.nextLine());
                emotionCount[num]++;
                i++;
            }
            disp.append("\nInstances detected : "+i);
            disp.append("\nEmotion count is:");
            double highestProbability=0.0,lowestProbability=1.1;
            int highest=-1;

            for(int j=0;j<emotion.length;j++) {
                double prob=((double)emotionCount[j])/i;
                if(prob>highestProbability) {
                    highestProbability = prob;
                    highest=j;
                }
                if(prob<lowestProbability)
                    lowestProbability=prob;
                disp.append("\n" + emotion[j] + " : " + emotionCount[j]+" -> "+prob);
            }
            disp.append("\nPredicted Emotion : " + emotion[highest]);
            if(highestProbability-lowestProbability < 0.1 ){
                disp.append("\nHigh Diversity Emotion can be neutral");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        new com.iridium.Frame();
    }
}