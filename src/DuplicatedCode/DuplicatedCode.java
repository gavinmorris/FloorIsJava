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


    //TODO
    // mark lines on original text with highest number
    // print number to console with lines
    // report how many of each hazard there are per file
    // Dead Code


    public void report(){
        System.out.println("\n\n ----- Duplicated Code ----- ");
        System.out.println("3-4 lines: warning");
        System.out.println("5-7 lines: bad");
        System.out.println("7-etc lines: hazard");

        for(File f : FileHandler.uploadedFiles) {
            try(BufferedReader br0 = new BufferedReader(new FileReader(f))) {

                ArrayList<String> file = new ArrayList<>();

                //get file length and add relevant lines to arraylist
                long totalNumLines=0;

                String line = br0.readLine();

                while(line != null){
                    line = line.trim();
                    if(!line.equals("") && !line.equals("{") && !line.equals("}")){
                        file.add(line.trim());
                    }

                    totalNumLines++;
                    line = br0.readLine();
                }
                br0.close();

                long actualNumLines = file.size();

                System.out.println("\n\nClass: " + f.getName()
                        + ", totalNumLines: " + totalNumLines + ", actualNumLines: " + actualNumLines);

                bigLoop:
                for(int occurenceNum=0; occurenceNum<actualNumLines-2; occurenceNum++) {

                    ArrayList<String> currentLines = new ArrayList<>();
                    ArrayList<String> duplicateConsecutiveLines = new ArrayList<>();
                    ArrayList<String> duplicateSeparatedLines = new ArrayList<>();

                    int fileLinesIndex = occurenceNum;
                    currentLines.add(file.get(fileLinesIndex));
//                    System.out.println("Tra la la: " + occurenceNum + " " + currentLines.get(0));

                    // check for consecutive lines
                    if (currentLines.get(0).equals(file.get(++fileLinesIndex))) {
                        duplicateConsecutiveLines.add(currentLines.get(0));
                        duplicateConsecutiveLines.add(currentLines.get(0));
                        occurenceNum++;
                        if(exitLoop(fileLinesIndex+1, file)) {
                            printDuplicateConsecutiveLines(duplicateConsecutiveLines);
                            break bigLoop;
                        }
                        line = file.get(++fileLinesIndex);
                        while (line.equals(duplicateConsecutiveLines.get(0))) {
                            duplicateConsecutiveLines.add(line);
                            occurenceNum++;
                            if(exitLoop(fileLinesIndex+1, file)) {
                                printDuplicateConsecutiveLines(duplicateConsecutiveLines);
                                break bigLoop;
                            }
                            line = file.get(++fileLinesIndex);
                        }
                    }
                    else{
                        if(exitLoop(fileLinesIndex, file)) {
                            printDuplicateConsecutiveLines(duplicateConsecutiveLines);
                            break bigLoop;
                        }
                        line = file.get(fileLinesIndex);
                    }
                    currentLines.add(line);
//                    System.out.println("Test 222: " + line);
                    int currLineFileIndex = fileLinesIndex;

                    smallLoop:
                    for (int numLinesApart = 0; numLinesApart < actualNumLines - fileLinesIndex - 1; numLinesApart++) {
                        int resetIndex = fileLinesIndex;
                        if(exitLoop(fileLinesIndex+1, file)) {
                            printDuplicateConsecutiveLines(duplicateConsecutiveLines);
                            printDuplicateSeparatedLines(duplicateSeparatedLines);
                            break smallLoop;
                        }
                        line = file.get(++fileLinesIndex);
//                        System.out.println("0: " + line);

                        int currLineIndex = 0;
//                        System.out.println("Test 444: " + currentLines.get(currLineIndex+1));
                        if (line.equals(currentLines.get(currLineIndex++))) {
                            if(exitLoop(fileLinesIndex+1, file)) {
                                printDuplicateConsecutiveLines(duplicateConsecutiveLines);
                                printDuplicateSeparatedLines(duplicateSeparatedLines);
                                break smallLoop;
                            }
                            line = file.get(++fileLinesIndex);
                            // check for consecutive lines
                            if (line.equals(file.get(fileLinesIndex - 1))) {
                                if(exitLoop(fileLinesIndex+1, file)) {
                                    printDuplicateConsecutiveLines(duplicateConsecutiveLines);
                                    printDuplicateSeparatedLines(duplicateSeparatedLines);
                                    break smallLoop;
                                }
                                line = file.get(++fileLinesIndex);
                            }
//                            System.out.println("1: " + line);

                            if (line.equals(currentLines.get(currLineIndex++))) {

                                duplicateSeparatedLines.add(currentLines.get(currLineIndex - 2));
                                duplicateSeparatedLines.add(currentLines.get(currLineIndex - 1));

                                if(exitLoop(fileLinesIndex+1, file)) {
                                    printDuplicateConsecutiveLines(duplicateConsecutiveLines);
                                    printDuplicateSeparatedLines(duplicateSeparatedLines);
                                    break smallLoop;
                                }
                                line = file.get(++fileLinesIndex);
                                // check for consecutive lines
                                if (line.equals(file.get(fileLinesIndex - 1))) {
                                    if(exitLoop(fileLinesIndex+1, file)) {
                                        printDuplicateConsecutiveLines(duplicateConsecutiveLines);
                                        printDuplicateSeparatedLines(duplicateSeparatedLines);
                                        break smallLoop;
                                    }
                                    line = file.get(++fileLinesIndex);
                                }

                                currentLines.add(file.get(++currLineFileIndex));
//                                System.out.println("2: " + currentLines.get(currLineIndex));

                                while (currLineIndex < numLinesApart + 2 && line.equals(currentLines.get(currLineIndex))) {
                                    duplicateSeparatedLines.add(currentLines.get(currLineIndex));
                                    if(exitLoop(fileLinesIndex+1, file)) {
                                        printDuplicateConsecutiveLines(duplicateConsecutiveLines);
                                        printDuplicateSeparatedLines(duplicateSeparatedLines);
                                        break smallLoop;
                                    }
                                    line = file.get(++fileLinesIndex);
                                    // check for consecutive lines
                                    if (line.equals(file.get(fileLinesIndex - 1))) {
                                        if(exitLoop(fileLinesIndex+1, file)) {
                                            printDuplicateConsecutiveLines(duplicateConsecutiveLines);
                                            printDuplicateSeparatedLines(duplicateSeparatedLines);
                                            break smallLoop;
                                        }
                                        line = file.get(++fileLinesIndex);
                                    }
                                    currentLines.add(line);
//                                    System.out.println("3: " + currentLines.get(currLineIndex+1));
                                }
                            }
                        }
                        fileLinesIndex = ++resetIndex;
                    }

                    printDuplicateConsecutiveLines(duplicateConsecutiveLines);
                    printDuplicateSeparatedLines(duplicateSeparatedLines);
                }




            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean exitLoop(int index, ArrayList<String> file){
        if(index<file.size()){
            return false;
        }
        return true;
    }

    private void printDuplicateConsecutiveLines(ArrayList<String> duplicateConsecutiveLines){
        if (duplicateConsecutiveLines.size() > 0) {
            System.out.println("Duplicate consecutive lines:");
            for(int i=0; i<duplicateConsecutiveLines.size(); i++){
                System.out.println(duplicateConsecutiveLines.get(i));
            }
            System.out.println();
        }
    }
    private void printDuplicateSeparatedLines(ArrayList<String> duplicateSeparatedLines){
        if (duplicateSeparatedLines.size() > 0) {
            System.out.println("Duplicate separated lines:");
            for(int i=0; i<duplicateSeparatedLines.size(); i++){
                System.out.println(duplicateSeparatedLines.get(i));
            }
            System.out.println();
        }
    }
}
