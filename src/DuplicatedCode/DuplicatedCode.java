package DuplicatedCode;

import FileProcessing.FileHandler;
import General.Literals;
import PrimtiveObsession.PrimtiveDataTypes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DuplicatedCode extends JButton implements ActionListener {

    public DuplicatedCode(){
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                report();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        //TODO
    }


    // TODO
    // every 2 lines, 3, 4, etc
    // every 1 consecutive line

    public void nothing(){
        int k=0;
        k++;
        k++;
        k++;
        k++;
    }

    public void report(){
        System.out.println("\n\n ----- Duplicated Code ----- ");

        for(File f : FileHandler.uploadedFiles) {
//            String s = new String();
            try(BufferedReader br0 = new BufferedReader(new FileReader(f))) {
                br0.mark(1000);
                long numLines=1;
                while(br0.readLine() != null){
                    numLines++;
                }
                br0.close();
//                Stream brLength = br.lines();
                System.out.println("\n\nClass: " + f.getName() + ": " + numLines);

                int bigMarkIndex=0;
                bigLoop:
                for(int i=0; i<numLines-6; i++){
                    int markIndex=0;
                    BufferedReader br = new BufferedReader(new FileReader(f));
                    for(int j=0; j<bigMarkIndex; j++){
                        br.readLine();
                    }
                    br.mark(1000);

                    String tmp = br.readLine().trim();
                    while(tmp.equals("") || tmp.equals("{") || tmp.equals("}")){
                        br.mark(1000);
                        tmp = br.readLine().trim();
                        bigMarkIndex++;
                        i++;
                    }
                    br.reset();

                    List<String> lines = new ArrayList<>();
                    lines.add(br.readLine().trim());
                    String line = br.readLine().trim();
                    markIndex+=2;
                    while(line.equals("") || line.equals("{") || line.equals("}")){
                        if(i+markIndex+bigMarkIndex < numLines-6) {
                            line = br.readLine().trim();
                            markIndex++;
                        }
                        else{
                            break bigLoop;
                        }
                    }
                    lines.add(line);

                    br.mark(1000);

//                    System.out.println("\nset lines: " + lines.get(0));
//                    System.out.println("set lines: " + lines.get(1));
//
//                    System.out.println("num lines to check: " + (numLines-bigMarkIndex-markIndex-1));
                    for(int k=0; k<numLines-bigMarkIndex-markIndex-1; k++){
                        int numDuplicateLines=0;
//                        System.out.println(k);

//                        for(int j=0; j<k; j++){
//                            br.readLine();
//                        }


                        String tempLine = br.readLine().trim();
                        while(tempLine.equals("") || tempLine.equals("{") || tempLine.equals("}")){
                            k++;
//                            System.out.println(k);
                            if(k >= (numLines-bigMarkIndex-markIndex-1)){
                                break;
                            }
                            else {
                                tempLine = br.readLine().trim();
                            }
                        }

                        if(k < (numLines-bigMarkIndex-markIndex-1)) {

//                            System.out.println("k: " + k);
//                            System.out.println("curr line 0: " + tempLine);

                            br.mark(1000);

                            if(lines.get(0).equals(tempLine)) {
//                                System.out.println("Dupped");
                                numDuplicateLines++;
                                endLoop:
                                for(int j = 1; j < lines.size(); j++) {
                                    tempLine = br.readLine().trim();
                                    int num=1;
                                    while (tempLine.equals("") || tempLine.equals("{") || tempLine.equals("}")) {
                                        num++;
                                        //eeek
                                        System.out.println(num + ":num k:" + k + " else: " + (numLines-bigMarkIndex-markIndex-1));
                                        if(num+k < (numLines-bigMarkIndex-markIndex-1)){
                                            tempLine = br.readLine().trim();
                                        }
                                        else{
                                            break endLoop;
                                        }
                                    }
//                                    System.out.println("curr line " + j + "; " + tempLine);

                                    if(lines.get(j).equals(tempLine)) {
//                                        System.out.println("Dupped");
                                        numDuplicateLines++;
                                    }
                                }

                                if(numDuplicateLines == lines.size()) {
                                    for (int t = 0; t < numDuplicateLines; t++) {
                                        System.out.println("dup line " + t + ": " + lines.get(t));
                                    }
                                    System.out.println();
                                }
                            }
                        }
                        br.reset();
                    }
                    bigMarkIndex++;
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
