package DuplicatedCode;

import FileProcessing.FileHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class DuplicatedCode extends JButton implements ActionListener {

    public ArrayList<boolean[]> allWarningLines;
    public ArrayList<boolean[]> allBadLines;
    public ArrayList<boolean[]> allHazardLines;

    public DuplicatedCode(){
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                report();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
    }

    public void report(){
        System.out.println("\n\n ----- Duplicated Code ----- ");
        System.out.println("3-4 lines: warning");
        System.out.println("5-6 lines: bad");
        System.out.println("7-etc lines: hazard");

        allWarningLines = new ArrayList<>();
        allBadLines = new ArrayList<>();
        allHazardLines = new ArrayList<>();

        for(int fileNum=0; fileNum<FileHandler.uploadedFiles.size(); fileNum++){
            File f = FileHandler.uploadedFiles.get(fileNum);
            try(BufferedReader br0 = new BufferedReader(new FileReader(f))) {

                ArrayList<String> file = new ArrayList<>();
                ArrayList<Integer> blankLines = new ArrayList<>();
                ArrayList<Integer> curlyLines = new ArrayList<>();

                //get file length and add relevant lines to arraylist
                long totalNumLines=0;

                String line = br0.readLine();

                while(line != null){
                    line = line.trim();
                    if(!line.equals("")){
                        if(!line.equals("{") && !line.equals("}")) {
                            while(line.endsWith("{") || line.endsWith("}")){
                                line = line.substring(0, line.length()-1).trim();
                            }
                            file.add(line);
                        }
                        else{
                            curlyLines.add((int) totalNumLines);
                        }
                    }
                    else{
                        blankLines.add((int) totalNumLines);
                    }

                    totalNumLines++;
                    line = br0.readLine();
                }
                br0.close();

                long actualNumLines = file.size();

                boolean[] currentWarningLines = new boolean[(int) totalNumLines];
                boolean[] currentBadLines = new boolean[(int) totalNumLines];
                boolean[] currentHazardLines = new boolean[(int) totalNumLines];

                System.out.println("\n\nClass: " + f.getName()
                        + ", totalNumLines: " + totalNumLines + ", actualNumLines: " + actualNumLines);


//                System.out.println("10: " + convertActualToTotalLineNum(10, blankLines, curlyLines));


                bigLoop:
                for(int occurenceNum=0; occurenceNum<actualNumLines-2; occurenceNum++) {

                    ArrayList<String> currentLines = new ArrayList<>();
                    ArrayList<String> duplicateConsecutiveLines = new ArrayList<>();
                    ArrayList<String> duplicateSeparatedLines = new ArrayList<>();

                    ArrayList<Integer> currentLineNums = new ArrayList<>();
                    ArrayList<Integer> duplicateConsecutiveLineNums = new ArrayList<>();
                    ArrayList<Integer> duplicateSeparatedLineNums = new ArrayList<>();

                    int fileLinesIndex = occurenceNum;
                    currentLines.add(file.get(fileLinesIndex));
                    currentLineNums.add(fileLinesIndex);
//                    System.out.println("Tra la la: " + occurenceNum + " " + currentLines.get(0));

                    // check for consecutive lines
                    if (currentLines.get(0).equals(file.get(++fileLinesIndex))) {
                        duplicateConsecutiveLines.add(currentLines.get(0));
                        duplicateConsecutiveLineNums.add(fileLinesIndex-1);
                        duplicateConsecutiveLines.add(currentLines.get(0));
                        duplicateConsecutiveLineNums.add(fileLinesIndex);
                        occurenceNum++;
                        if(exitLoop(fileLinesIndex+1, file, duplicateConsecutiveLines, currentLines, new ArrayList<String>()))
                            break bigLoop;
                        line = file.get(++fileLinesIndex);
                        while (line.equals(duplicateConsecutiveLines.get(0))) {
                            currentLineNums.set(0, fileLinesIndex);

                            duplicateConsecutiveLines.add(line);
                            duplicateConsecutiveLineNums.add(fileLinesIndex);
                            occurenceNum++;
                            if(exitLoop(fileLinesIndex+1, file, duplicateConsecutiveLines, currentLines, new ArrayList<String>()))
                                break bigLoop;
                            line = file.get(++fileLinesIndex);
                        }
                    }
                    else{
                        if(exitLoop(fileLinesIndex, file, duplicateConsecutiveLines, currentLines, new ArrayList<String>()))
                            break bigLoop;
                        line = file.get(fileLinesIndex);
                    }
                    currentLines.add(line);
                    currentLineNums.add(fileLinesIndex);
                    int currentLinesIndex=fileLinesIndex;
//                    System.out.println("Test 222: " + line);
                    int currLineFileIndex = fileLinesIndex;
//                    ArrayList<String> tempCurrentLines = currentLines;
//                    ArrayList<Integer> tempCurrentLineNums = currentLineNums;
//                    ArrayList<String> tempDuplicateLines = duplicateSeparatedLines;
//                    ArrayList<Integer> tempDuplicateLineNums = duplicateSeparatedLineNums;

                    smallLoop:
                    for (int numLinesApart = 0; numLinesApart < actualNumLines - fileLinesIndex - 1; numLinesApart++) {
                        int resetIndex = fileLinesIndex;
                        if(exitLoop(fileLinesIndex+1, file, duplicateConsecutiveLines, currentLines, duplicateSeparatedLines))
                            break smallLoop;
                        line = file.get(++fileLinesIndex);
//                        System.out.println("0: " + line);

                        int currLineIndex = 0;
//                        System.out.println("Test 444: " + currentLines.get(currLineIndex+1));
                        if (line.equals(currentLines.get(currLineIndex++))) {
                            if(exitLoop(fileLinesIndex+1, file, duplicateConsecutiveLines, currentLines, duplicateSeparatedLines))
                                break smallLoop;
                            line = file.get(++fileLinesIndex);
                            // check for consecutive lines
                            if (line.equals(file.get(fileLinesIndex - 1))) {
                                if(exitLoop(fileLinesIndex+1, file, duplicateConsecutiveLines, currentLines, duplicateSeparatedLines))
                                    break smallLoop;
                                line = file.get(++fileLinesIndex);
                            }
//                            System.out.println("1: " + line);

                            if (line.equals(currentLines.get(currLineIndex++))) {
//                                System.out.println(duplicateSeparatedLines.size());
                                duplicateSeparatedLines.add(currentLines.get(currLineIndex - 2));
                                duplicateSeparatedLineNums.add(fileLinesIndex-1);
                                duplicateSeparatedLines.add(currentLines.get(currLineIndex - 1));
                                duplicateSeparatedLineNums.add(fileLinesIndex);

                                if(exitLoop(fileLinesIndex+1, file, duplicateConsecutiveLines, currentLines, duplicateSeparatedLines))
                                    break smallLoop;
                                line = file.get(++fileLinesIndex);
                                // check for consecutive lines
                                if (line.equals(file.get(fileLinesIndex - 1))) {
                                    if(exitLoop(fileLinesIndex+1, file, duplicateConsecutiveLines, currentLines, duplicateSeparatedLines))
                                        break smallLoop;
                                    line = file.get(++fileLinesIndex);
                                }

                                currentLines.add(file.get(++currLineFileIndex));
                                currentLineNums.add(currLineFileIndex);
//                                System.out.println("2: " + currentLines.get(currLineIndex));

                                while (currLineIndex < numLinesApart + 2 && line.equals(currentLines.get(currLineIndex))) {
                                    duplicateSeparatedLines.add(currentLines.get(currLineIndex));
                                    duplicateSeparatedLineNums.add(fileLinesIndex);
                                    if(exitLoop(fileLinesIndex+1, file, duplicateConsecutiveLines, currentLines, duplicateSeparatedLines))
                                        break smallLoop;
                                    line = file.get(++fileLinesIndex);
                                    // check for consecutive lines
                                    if (line.equals(file.get(fileLinesIndex - 1))) {
                                        if(exitLoop(fileLinesIndex+1, file, duplicateConsecutiveLines, currentLines, duplicateSeparatedLines))
                                            break smallLoop;
                                        line = file.get(++fileLinesIndex);
                                    }
                                    currentLines.add(file.get(++currLineFileIndex));
                                    currentLineNums.add(currLineFileIndex);
//                                    System.out.println("3: " + currentLines.get(currLineIndex+1));
                                }
                                currentLines.remove(currentLines.size()-1);
                                currentLineNums.remove(currentLineNums.size()-1);
                            }
                        }
//                        if(duplicateSeparatedLines.size()>0)
//                            System.out.println(duplicateSeparatedLines.size() + " " + duplicateSeparatedLines);
//                        printAllLines(new ArrayList<>(), new ArrayList<>(), duplicateSeparatedLines);
                        fileLinesIndex = ++resetIndex;


                        if(duplicateSeparatedLines.size()>0) {
                            boolean[] currWarning = addDuplicateLinesToWarningArray(currentLines.size(), currentLineNums, duplicateSeparatedLineNums, currentWarningLines,
                                    blankLines, curlyLines);
                            for (int i = 0; i < currWarning.length; i++) {
                                if (currWarning[i]) {
//                                    System.out.println("test 1");
                                    currentWarningLines[i] = true;
                                }
                            }
                            boolean[] currBad = addDuplicateLinesToBadArray(currentLines.size(), currentLineNums, duplicateSeparatedLineNums, currentBadLines,
                                    blankLines, curlyLines);
                            for (int i = 0; i < currWarning.length; i++) {
                                if (currBad[i]) {
//                                    System.out.println("test 2");
                                    currentBadLines[i] = true;
                                }
                            }
                            boolean[] currHazard = addDuplicateLinesToHazardArray(currentLines.size(), currentLineNums, duplicateSeparatedLineNums, currentHazardLines,
                                    blankLines, curlyLines);
                            for (int i = 0; i < currHazard.length; i++) {
                                if (currHazard[i]) {
//                                    System.out.println("test 3");
                                    currentHazardLines[i] = true;
                                }
                            }
                        }

                        while(currentLines.size()>2) {
                            currentLines.remove(currentLines.size()-1);
                            currentLineNums.remove(currentLineNums.size()-1);
                        }
                        duplicateSeparatedLines = new ArrayList<>();
                        duplicateSeparatedLineNums = new ArrayList<>();
//                        duplicateSeparatedLines = new
                    }

//                    printAllLines(duplicateConsecutiveLines, new ArrayList<>(), duplicateSeparatedLines);
//                    exitLoop(file.size(), file, duplicateConsecutiveLines, currentLines, duplicateSeparatedLines);
                }

//                if(currentWarningLines.length > 0)
                    allWarningLines.add(currentWarningLines);
//                else
//                    allWarningLines.add(new boolean[(int) f.length()]);
//                if(currentBadLines.length > 0)
                    allBadLines.add(currentBadLines);
//                else
//                    allBadLines.add(new boolean[(int) f.length()]);
//                if(currentHazardLines.length > 0)
                    allHazardLines.add(currentHazardLines);
//                    for(int i=0; i<currentHazardLines.length; i++){
//                        System.out.println(currentHazardLines[i]);
//                    }
//                System.out.println();
//                else
//                    allHazardLines.add(new boolean[(int) f.length()]);

                BufferedReader br = new BufferedReader(new FileReader(f));
                System.out.println("\nHazard Lines:");
                for(int j=0; j<totalNumLines; j++){
                    line = br.readLine();
                    if(allHazardLines.get(allHazardLines.size()-1)[j]){
                        System.out.println((j+1) + " " + line.trim());
                    }
                }
                br.close();

                br = new BufferedReader(new FileReader(f));
                System.out.println("\nBad Lines:");
                for(int j=0; j<totalNumLines; j++){
                    line = br.readLine();
                    if(allBadLines.get(allBadLines.size()-1)[j]){
                        System.out.println((j+1) + " " + line.trim());
                    }
                }
                br.close();

                br = new BufferedReader(new FileReader(f));
                System.out.println("\nWarning Lines:");
                for(int j=0; j<totalNumLines; j++){
                    line = br.readLine();
                    if(allWarningLines.get(allWarningLines.size()-1)[j]){
                        System.out.println((j+1) + " " + line.trim());
                    }
                }
                br.close();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private boolean[] addDuplicateLinesToWarningArray(int currentLinesSize, ArrayList<Integer> currentLineNums,
                                                      ArrayList<Integer> duplicateSeparatedLineNums, boolean[] currentWarningLines,
                                                        ArrayList<Integer> blankLines, ArrayList<Integer> curlyLines){
//        ArrayList<Integer> allLines = currentLineNums;
//        allLines.addAll(duplicateSeparatedLineNums);
//        boolean[] tempWarningLines = currentWarningLines;
//        for(int i=0; i<currentLineNums.size(); i++){
//            System.out.print(convertActualToTotalLineNum(currentLineNums.get(i), blankLines, curlyLines) + " ");
//        }
//        System.out.println();
//        for(int i=0; i<duplicateSeparatedLineNums.size(); i++){
//            System.out.print(convertActualToTotalLineNum(duplicateSeparatedLineNums.get(i), blankLines, curlyLines) + " ");
//        }
//        System.out.println(duplicateSeparatedLineNums);
        System.out.println();

        if(currentLinesSize>=3 && currentLinesSize<=4){
            for(int i=0; i<currentLineNums.size(); i++){
                currentWarningLines[convertActualToTotalLineNum(currentLineNums.get(i), blankLines, curlyLines)] = true;
            }
            for(int i=0; i<duplicateSeparatedLineNums.size(); i++){
                currentWarningLines[convertActualToTotalLineNum(duplicateSeparatedLineNums.get(i), blankLines, curlyLines)] = true;
            }
        }
        return currentWarningLines;
    }
    private boolean[] addDuplicateLinesToBadArray(int currentLinesSize, ArrayList<Integer> currentLineNums,
                                                      ArrayList<Integer> duplicateSeparatedLineNums, boolean[] currentBadLines,
                                                  ArrayList<Integer> blankLines, ArrayList<Integer> curlyLines){
        //        ArrayList<Integer> allLines = currentLineNums;
//        allLines.addAll(duplicateSeparatedLineNums);
//        boolean[] tempWarningLines = currentBadLines;

        if(currentLinesSize>=5 && currentLinesSize<=6){
            for(int i=0; i<currentLineNums.size(); i++){
                currentBadLines[convertActualToTotalLineNum(currentLineNums.get(i), blankLines, curlyLines)] = true;
            }
            for(int i=0; i<duplicateSeparatedLineNums.size(); i++){
                currentBadLines[convertActualToTotalLineNum(duplicateSeparatedLineNums.get(i), blankLines, curlyLines)] = true;
            }
        }
        return currentBadLines;
    }
    private boolean[] addDuplicateLinesToHazardArray(int currentLinesSize, ArrayList<Integer> currentLineNums,
                                                  ArrayList<Integer> duplicateSeparatedLineNums, boolean[] currentHazardLines,
                                                     ArrayList<Integer> blankLines, ArrayList<Integer> curlyLines){
        //        ArrayList<Integer> allLines = currentLineNums;
//        allLines.addAll(duplicateSeparatedLineNums);
//        boolean[] tempWarningLines = currentHazardLines;

        if(currentLinesSize>=7){
            for(int i=0; i<currentLineNums.size(); i++){
                currentHazardLines[convertActualToTotalLineNum(currentLineNums.get(i), blankLines, curlyLines)] = true;
            }
            for(int i=0; i<duplicateSeparatedLineNums.size(); i++){
                currentHazardLines[convertActualToTotalLineNum(duplicateSeparatedLineNums.get(i), blankLines, curlyLines)] = true;
            }
        }
        return currentHazardLines;
    }


    private boolean exitLoop(int lineNum, /*int totalNumLines, */ArrayList<String> file,
            ArrayList<String> duplicateConsecutiveLines, ArrayList<String> currentLines, ArrayList<String> duplicateSeparatedLines/*,
            ArrayList<Integer> duplicateConsecutiveLineNums, ArrayList<Integer> currentLineNums, ArrayList<Integer> duplicateSeparatedLineNums*/){

//        boolean[] currentWarningLines = new boolean[totalNumLines];

        if(lineNum<file.size()){
            return false;
        }
        else {
//            printAllLines(duplicateConsecutiveLines, currentLines, duplicateSeparatedLines);
//
//            int index=0;
//            for(int i=0; i<totalNumLines; i++){
//                if(duplicateConsecutiveLineNums.get(index) == i){
//                    currindex++;
//                }
//            }

            return true;
        }
    }

    private int convertActualToTotalLineNum(int lineNum, ArrayList<Integer> blankLines, ArrayList<Integer> curlyLines){
        int blankLinesIndex = 0, curlyLinesIndex = 0;
        int k=0;
        while(k<=lineNum) {
            if (blankLines.size()>blankLinesIndex && blankLines.get(blankLinesIndex) == k) {
                blankLinesIndex++;
                lineNum++;
            } else if (curlyLines.size()>blankLinesIndex && curlyLines.get(curlyLinesIndex) == k) {
                curlyLinesIndex++;
                lineNum++;
            } else {
                k++;
            }
        }

        return lineNum;
    }

    private void printAllLines(ArrayList<String> duplicateConsecutiveLines,
                               ArrayList<String> currentLines, ArrayList<String> duplicateSeparatedLines){
        printDuplicateConsecutiveLines(duplicateConsecutiveLines);
        printCurrentLines(currentLines);
        printDuplicateSeparatedLines(duplicateSeparatedLines);

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
    private void printCurrentLines(ArrayList<String> currentLines){
        if (currentLines.size() > 0) {
            System.out.println("Current lines:");
            for(int i=0; i<currentLines.size(); i++){
                System.out.println(currentLines.get(i));
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
