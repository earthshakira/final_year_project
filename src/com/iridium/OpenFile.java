package com.iridium;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
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
                return 1;
            }
            FileOutputStream out = new FileOutputStream("test.wav");
            Files.copy(file.toPath(),out);
            return  2;
        }else return 3;
    }
}
