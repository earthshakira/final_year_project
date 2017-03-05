package com.iridium;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static java.nio.file.StandardCopyOption.*;

/**
 * Created by earthshakira on 30/1/17.
 */
public class OpenFile {
    JFileChooser fileChooser = new JFileChooser();
    String name;

    public int pickMe() throws Exception{

        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            File file= fileChooser.getSelectedFile();
            name=file.getName();
            System.out.println(name);
            String[] fileName=name.split("\\.");
            System.out.print(fileName.length);
            if(!fileName[fileName.length-1].equals("wav")){
//                if(fileName[fileName.length-1].equals("mp3") || fileName[fileName.length-1].equals("mp4")){
//                    System.out.println("MP file found");
//                    String command = "ffmpeg -i \""+file.getAbsolutePath()+"\" test.wav -y";
//                    System.out.println(command);
//                    executeCommand(command);
//                    System.out.println();
//                    return 2;
//                }
                return 1;
            }
            FileOutputStream out = new FileOutputStream("test.wav");
            Files.copy(file.toPath(),out);
            return  2;
        }else return 3;
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
}
