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
            try(BufferedReader br = new BufferedReader(new FileReader(f))) {
                br.mark(1000);
                long numLines=0;
                while(br.readLine() != null){
                    numLines++;
                }
//                Stream brLength = br.lines();
                br.reset();
                System.out.println("result: " + f.getName() + ": " + numLines);

                for(int i=0; i<numLines/2; i++){
                    while(br.readLine() == null){
                    }
                    br.mark(1000);
                    List<String> lines = new ArrayList<>();
                    for(int j=0; j<2; j++){
                        String tempLine = br.readLine();
                        if(tempLine == null){
                            j--;
                        }
                        else{
                            lines.add(tempLine);
                        }
                        System.out.println("wow" + j + ": " + lines.get(j));
                    }
                    for(int k=0; k<numLines/lines.size(); k++){
                        int numDuplicateLines=0;
                        for(int j=0; j<2; j++){
                            System.out.println("dude " + lines.get(j));
                            String tempLine = br.readLine();
                            if(tempLine != null){
                                if(lines.get(j).equals(tempLine)){
                                    System.out.println("Dupped");
                                    numDuplicateLines++;
                                }
                            }
                            else{
                                j--;
                            }
                        }
                        if(numDuplicateLines == lines.size()){
                            for(int t=0; t<numDuplicateLines; t++){
                                System.out.println("dup " + t + ": " + lines.get(t));
                            }
                            System.out.println();
                        }
                    }
                    br.reset();
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
