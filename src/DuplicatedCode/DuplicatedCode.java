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
    public ArrayList<boolean[]> allConsecutiveLines;

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
        allConsecutiveLines = new ArrayList<>();

        for(int fileNum=0; fileNum<FileHandler.uploadedFiles.size(); fileNum++){
            File f = FileHandler.uploadedFiles.get(fileNum);
            try(BufferedReader br0 = new BufferedReader(new FileReader(f))) {

                ArrayList<String> file = new ArrayList<>();
                ArrayList<Integer> blankLines = new ArrayList<>();
                ArrayList<Integer> curlyLines = new ArrayList<>();
                ArrayList<Integer> commentedLines = new ArrayList<>();

                //get file length and add relevant lines to arraylist
                long totalNumLines=0;

                String line = br0.readLine();

                while(line != null){
                    line = line.trim();
                    if(!line.equals("")){
                        if(!line.equals("{") && !line.equals("}")) {
                            if(!line.startsWith("//")){
                                if(!line.startsWith("/*")){
                                    while(line.endsWith("{") || line.endsWith("}")){
                                        line = line.substring(0, line.length()-1).trim();
                                    }
                                    file.add(line);
                                }
                                else{
                                    if(!line.contains("*/")){
                                        while(!line.contains("*/")){
                                            commentedLines.add((int) totalNumLines);
                                            totalNumLines++;
                                            line = br0.readLine();
                                            if(line==null)
                                                break;
                                        }
                                        line = line.substring(line.indexOf("*/")+2);
                                        file.add(line);
                                    }
                                    else{
                                        line = line.substring(line.indexOf("*/")+2, line.length()-1);
                                        file.add(line);
                                    }
                                }
                            }
                            else{
                                commentedLines.add((int) totalNumLines);
                            }
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
                boolean[] currentConsecutiveLines = new boolean[(int) totalNumLines];

                System.out.println("\n\nClass: " + f.getName()
                        + ", totalNumLines: " + totalNumLines + ", actualNumLines: " + actualNumLines);

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
                    int currLineFileIndex = fileLinesIndex;

                    smallLoop:
                    for (int numLinesApart = 0; numLinesApart < actualNumLines - fileLinesIndex - 1; numLinesApart++) {
                        int resetIndex = fileLinesIndex;
                        if(exitLoop(fileLinesIndex+1, file, duplicateConsecutiveLines, currentLines, duplicateSeparatedLines))
                            break smallLoop;
                        line = file.get(++fileLinesIndex);

                        int currLineIndex = 0;
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

                            if (line.equals(currentLines.get(currLineIndex++))) {
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
                                }
                                currentLines.remove(currentLines.size()-1);
                                currentLineNums.remove(currentLineNums.size()-1);
                            }
                        }
                        fileLinesIndex = ++resetIndex;

                        if(duplicateSeparatedLines.size()>0) {
                            boolean[] currWarning = addDuplicateLinesToWarningArray(currentLines.size(), currentLineNums, duplicateSeparatedLineNums, currentWarningLines,
                                    blankLines, curlyLines, commentedLines);
                            for (int i = 0; i < currWarning.length; i++) {
                                if (currWarning[i]) {
                                    currentWarningLines[i] = true;
                                }
                            }
                            boolean[] currBad = addDuplicateLinesToBadArray(currentLines.size(), currentLineNums, duplicateSeparatedLineNums, currentBadLines,
                                    blankLines, curlyLines, commentedLines);
                            for (int i = 0; i < currWarning.length; i++) {
                                if (currBad[i]) {
                                    currentBadLines[i] = true;
                                }
                            }
                            boolean[] currHazard = addDuplicateLinesToHazardArray(currentLines.size(), currentLineNums, duplicateSeparatedLineNums, currentHazardLines,
                                    blankLines, curlyLines, commentedLines);
                            for (int i = 0; i < currHazard.length; i++) {
                                if (currHazard[i]) {
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
                    }

                    if(duplicateConsecutiveLines.size()>0){
                        boolean[] currConsecutive = addConsecutiveLinesToConsecutiveArray(duplicateConsecutiveLineNums, currentConsecutiveLines,
                                blankLines, curlyLines, commentedLines);
                        for (int i = 0; i < currConsecutive.length; i++) {
                            if (currConsecutive[i]) {
                                currentConsecutiveLines[i] = true;
                            }
                        }
                    }
                }

                allWarningLines.add(currentWarningLines);
                allBadLines.add(currentBadLines);
                allHazardLines.add(currentHazardLines);
                allConsecutiveLines.add(currentConsecutiveLines);
//                allConsecutiveLines.add();

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

                br = new BufferedReader(new FileReader(f));
                System.out.println("\nConsecutive Duplicate Lines:");
                for(int j=0; j<totalNumLines; j++){
                    line = br.readLine();
                    if(allConsecutiveLines.get(allConsecutiveLines.size()-1)[j]){
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
                                                        ArrayList<Integer> blankLines, ArrayList<Integer> curlyLines, ArrayList<Integer> commentedLines){
        if(currentLinesSize>=3 && currentLinesSize<=4){
            for(int i=0; i<currentLineNums.size(); i++){
                currentWarningLines[convertActualToTotalLineNum(currentLineNums.get(i), blankLines, curlyLines, commentedLines)] = true;
            }
            for(int i=0; i<duplicateSeparatedLineNums.size(); i++){
                currentWarningLines[convertActualToTotalLineNum(duplicateSeparatedLineNums.get(i), blankLines, curlyLines, commentedLines)] = true;
            }
        }
        return currentWarningLines;
    }
    private boolean[] addDuplicateLinesToBadArray(int currentLinesSize, ArrayList<Integer> currentLineNums,
                                                      ArrayList<Integer> duplicateSeparatedLineNums, boolean[] currentBadLines,
                                                  ArrayList<Integer> blankLines, ArrayList<Integer> curlyLines, ArrayList<Integer> commentedLines){
        if(currentLinesSize>=5 && currentLinesSize<=6){
            for(int i=0; i<currentLineNums.size(); i++){
                currentBadLines[convertActualToTotalLineNum(currentLineNums.get(i), blankLines, curlyLines, commentedLines)] = true;
            }
            for(int i=0; i<duplicateSeparatedLineNums.size(); i++){
                currentBadLines[convertActualToTotalLineNum(duplicateSeparatedLineNums.get(i), blankLines, curlyLines, commentedLines)] = true;
            }
        }
        return currentBadLines;
    }
    private boolean[] addDuplicateLinesToHazardArray(int currentLinesSize, ArrayList<Integer> currentLineNums,
                                                     ArrayList<Integer> duplicateSeparatedLineNums, boolean[] currentHazardLines,
                                                     ArrayList<Integer> blankLines, ArrayList<Integer> curlyLines, ArrayList<Integer> commentedLines){
        if(currentLinesSize>=7){
            for(int i=0; i<currentLineNums.size(); i++){
                currentHazardLines[convertActualToTotalLineNum(currentLineNums.get(i), blankLines, curlyLines, commentedLines)] = true;
            }
            for(int i=0; i<duplicateSeparatedLineNums.size(); i++){
                currentHazardLines[convertActualToTotalLineNum(duplicateSeparatedLineNums.get(i), blankLines, curlyLines, commentedLines)] = true;
            }
        }
        return currentHazardLines;
    }
    private boolean[] addConsecutiveLinesToConsecutiveArray(ArrayList<Integer> duplicateConsecutiveLineNums, boolean[] currentConsecutiveLines,
                                                     ArrayList<Integer> blankLines, ArrayList<Integer> curlyLines, ArrayList<Integer> commentedLines){
        for(int i=0; i<duplicateConsecutiveLineNums.size(); i++){
            currentConsecutiveLines[convertActualToTotalLineNum(duplicateConsecutiveLineNums.get(i), blankLines, curlyLines, commentedLines)] = true;
        }
        return currentConsecutiveLines;
    }


    private boolean exitLoop(int lineNum, ArrayList<String> file,
            ArrayList<String> duplicateConsecutiveLines, ArrayList<String> currentLines, ArrayList<String> duplicateSeparatedLines){

        if(lineNum<file.size()){
            return false;
        }
        return true;
    }

    private int convertActualToTotalLineNum(int lineNum, ArrayList<Integer> blankLines, ArrayList<Integer> curlyLines, ArrayList<Integer> commentedLines){
        int blankLinesIndex = 0, curlyLinesIndex = 0, commentedLinesIndex=0;
        int k=0;
        while(k<=lineNum) {
            if (blankLines.size()>blankLinesIndex && blankLines.get(blankLinesIndex) == k) {
                blankLinesIndex++;
                lineNum++;
            } else if (curlyLines.size()>blankLinesIndex && curlyLines.get(curlyLinesIndex) == k) {
                curlyLinesIndex++;
                lineNum++;
            } else if (commentedLines.size()>commentedLinesIndex && commentedLines.get(commentedLinesIndex) == k) {
                commentedLinesIndex++;
                lineNum++;
            } else {
                k++;
            }
        }

        return lineNum;
    }



}
