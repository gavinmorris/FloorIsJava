package DeadCode;

import FileProcessing.FileHandler;
import InappropriateIntimacy.InappropriateIntimacy;
import Utilities.ClassMethod;
import Utilities.ClassObjectTuple;
import Utilities.ClassVariable;
import Utilities.Literals;
import Utilities.Smells;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DeadCode extends JButton implements ActionListener {

    public DeadCode(){
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("\n\n\nDead Code Checker\n\n");
                report();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
    }

    public void report() {
        System.out.println("-------Public Methods never called---------");
        checkPublicMethods(new ArrayList<ClassMethod<String, String>>());
        System.out.println("-------Private Methods never called---------");
        checkPrivateMethods();
        System.out.println("-------Protected Methods never called---------");
        checkProtectedMethods(new ArrayList<ClassMethod<String, String>>());
        System.out.println("-------Public Variables never used---------");
        checkPublicVariables(new ArrayList<ClassVariable<String, String>>());
        System.out.println("-------Private Variables never used---------");
        checkPrivateVariables();
        System.out.println("-------Protected Variables never used---------");
        checkProtectedVariables(new ArrayList<ClassVariable<String, String>>());
    }


    private void checkPublicMethods(List<ClassMethod<String, String>> unused){
        for(File f : FileHandler.uploadedFiles) {
            boolean[] definiteDeadCodeArray = new boolean[(int) f.length()];
            boolean[] possibleOccurenceArray = new boolean[(int) f.length()];
            for (ClassMethod<String, String> cm : unused) {
                if(cm.getClassName().equals(FileHandler.removeExtension(f.getName()))){
                    boolean definiteOccurence = false;
                    ArrayList<Integer> possibleOccurenceLineNums = new ArrayList<>();

                    try(BufferedReader br = new BufferedReader(new FileReader(f))) {
                        int lineIndex=0;
                        for(String line; (line = br.readLine()) != null; ) {
                            // if line != method call ever then aadd to dead code
                            if(line.contains(" ".concat(cm.getMethodName().concat("("))) || line.contains(".".concat(cm.getMethodName().concat("(")))) {
                                // if method call is a declaration
                                if (line.contains(Literals.PUBLIC) && line.contains(Literals.O_BRACKET) && line.contains(Literals.C_BRACKET) && !line.contains("new")) {
                                    possibleOccurenceLineNums.add(lineIndex);
                                }
                                else {
                                    definiteOccurence = true;
                                    break;
                                }
                            }
                            lineIndex++;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(!definiteOccurence) {
                        if (possibleOccurenceLineNums.size() > 1) {
                            for (int i = 0; i < possibleOccurenceLineNums.size(); i++) {
                                possibleOccurenceArray[possibleOccurenceLineNums.get(i)] = true;
                            }
                        }
                        else{
                            definiteDeadCodeArray[possibleOccurenceLineNums.get(0)] = true;
                        }
                    }
                }
            }
            printDeadCodeLines(f, definiteDeadCodeArray, "Public Definite Dead Code:");
            printDeadCodeLines(f, possibleOccurenceArray, "Public Possible Dead Code:");
        }
    }
    private void checkPublicVariables(List<ClassVariable<String, String>> unused){
        for(File f : FileHandler.uploadedFiles) {
            boolean[] definiteDeadCodeArray = new boolean[(int) f.length()];
            boolean[] possibleOccurenceArray = new boolean[(int) f.length()];
            for (ClassVariable<String, String> cv : unused) {
                if(cv.getClassName().equals(FileHandler.removeExtension(f.getName()))){
                    boolean definiteOccurence = false;
                    ArrayList<Integer> possibleOccurenceLineNums = new ArrayList<>();

                    try(BufferedReader br = new BufferedReader(new FileReader(f))) {
                        int lineIndex=0;
                        for(String line; (line = br.readLine()) != null; ) {
                            // if line != method call ever then aadd to dead code
                            if(line.contains(cv.getVariableName())) {
                                // if method call is a declaration
                                if (line.contains(Literals.PUBLIC) && !line.contains(Literals.O_BRACKET) && !line.contains(Literals.C_BRACKET) && line.contains("new")) {
                                    possibleOccurenceLineNums.add(lineIndex);
                                }
                                else {
                                    definiteOccurence = true;
                                    break;
                                }
                            }
                            lineIndex++;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(!definiteOccurence) {
                        if (possibleOccurenceLineNums.size() > 1) {
                            for (int i = 0; i < possibleOccurenceLineNums.size(); i++) {
                                possibleOccurenceArray[possibleOccurenceLineNums.get(i)] = true;
                            }
                        }
                        else{
                            definiteDeadCodeArray[possibleOccurenceLineNums.get(0)] = true;
                        }
                    }
                }
            }
            printDeadCodeLines(f, definiteDeadCodeArray, "Public Definite Dead Code Variables:");
            printDeadCodeLines(f, possibleOccurenceArray, "Public Possible Dead Code Variables:");
        }
    }

    private void checkPrivateMethods(){
        for(File f : FileHandler.uploadedFiles) {
            if(!isInterface(f)) {
                List<ClassMethod<String, String>> methods = new ArrayList<>();
                try(BufferedReader br = new BufferedReader(new FileReader(f))) {
                    for(String line; (line = br.readLine()) != null; ) {
                        if(line.contains(Literals.PRIVATE) && line.contains(Literals.O_BRACKET) && line.contains(Literals.C_BRACKET) && !line.contains("new")) {
                            methods.add(new ClassMethod<>(FileHandler.removeExtension(f.getName()), getMethodDef(line)));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                boolean[] definiteDeadCodeArray = new boolean[(int) f.length()];
                boolean[] possibleOccurenceArray = new boolean[(int) f.length()];
                for (ClassMethod<String, String> cm : methods) {
                    if(cm.getClassName().equals(FileHandler.removeExtension(f.getName()))){
                        boolean definiteOccurence = false;
                        ArrayList<Integer> possibleOccurenceLineNums = new ArrayList<>();

                        try(BufferedReader br = new BufferedReader(new FileReader(f))) {
                            int lineIndex=0;
                            for(String line; (line = br.readLine()) != null; ) {
                                // if line != method call ever then aadd to dead code
                                if(line.contains(" ".concat(cm.getMethodName().concat("("))) || line.contains(".".concat(cm.getMethodName().concat("(")))) {
                                    // if method call is a declaration
                                    if (line.contains(Literals.PRIVATE) && line.contains(Literals.O_BRACKET) && line.contains(Literals.C_BRACKET) && !line.contains("new")) {
                                        possibleOccurenceLineNums.add(lineIndex);
                                    }
                                    else {
                                        definiteOccurence = true;
                                        break;
                                    }
                                }
                                lineIndex++;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(!definiteOccurence) {
                            if (possibleOccurenceLineNums.size() > 1) {
                                for (int i = 0; i < possibleOccurenceLineNums.size(); i++) {
                                    possibleOccurenceArray[possibleOccurenceLineNums.get(i)] = true;
                                }
                            }
                            else{
                                definiteDeadCodeArray[possibleOccurenceLineNums.get(0)] = true;
                            }
                        }
                    }
                }
                printDeadCodeLines(f, definiteDeadCodeArray, "Private Definite Dead Code Methods:");
                printDeadCodeLines(f, possibleOccurenceArray, "Private Possible Dead Code Methods:");
            }
        }
    }
    private void checkPrivateVariables(){
        for(File f : FileHandler.uploadedFiles) {
            if(!isInterface(f)) {
                List<ClassVariable<String, String>> variables = new ArrayList<>();
                try(BufferedReader br = new BufferedReader(new FileReader(f))) {
                    for(String line; (line = br.readLine()) != null; ) {
                        // if line contains a variable (not a method) add to list
                        if(line.contains(Literals.PRIVATE) && !line.contains(Literals.O_BRACKET) && !line.contains(Literals.C_BRACKET) && line.contains("new")) {
                            variables.add(new ClassVariable<>(FileHandler.removeExtension(f.getName()), getMethodDef(line)));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                boolean[] definiteDeadCodeArray = new boolean[(int) f.length()];
                boolean[] possibleOccurenceArray = new boolean[(int) f.length()];
                for (ClassVariable<String, String> cv : variables) {
                    if(cv.getClassName().equals(FileHandler.removeExtension(f.getName()))){
                        boolean definiteOccurence = false;
                        ArrayList<Integer> possibleOccurenceLineNums = new ArrayList<>();

                        try(BufferedReader br = new BufferedReader(new FileReader(f))) {
                            int lineIndex=0;
                            for(String line; (line = br.readLine()) != null; ) {
                                // if line != method call ever then aadd to dead code
                                if(line.contains(cv.getVariableName())) {
                                    // if method call is a declaration
                                    // if line contains a variable (not a method) add to list
                                    if (line.contains(Literals.PRIVATE) && !line.contains(Literals.O_BRACKET) && !line.contains(Literals.C_BRACKET) && line.contains("new")) {
                                        possibleOccurenceLineNums.add(lineIndex);
                                    }
                                    else {
                                        definiteOccurence = true;
                                        break;
                                    }
                                }
                                lineIndex++;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(!definiteOccurence) {
                            if (possibleOccurenceLineNums.size() > 1) {
                                for (int i = 0; i < possibleOccurenceLineNums.size(); i++) {
                                    possibleOccurenceArray[possibleOccurenceLineNums.get(i)] = true;
                                }
                            }
                            else{
                                definiteDeadCodeArray[possibleOccurenceLineNums.get(0)] = true;
                            }
                        }
                    }
                }
                printDeadCodeLines(f, definiteDeadCodeArray, "Private Definite Dead Code Variables:");
                printDeadCodeLines(f, possibleOccurenceArray, "Private Possible Dead Code Variables:");
            }
        }
    }

    private void checkProtectedMethods(List<ClassMethod<String, String>> unused){
        for(File f : FileHandler.uploadedFiles) {
            boolean[] definiteDeadCodeArray = new boolean[(int) f.length()];
            boolean[] possibleOccurenceArray = new boolean[(int) f.length()];
            for (ClassMethod<String, String> cm : unused) {
                if(cm.getClassName().equals(FileHandler.removeExtension(f.getName()))){
                    boolean definiteOccurence = false;
                    ArrayList<Integer> possibleOccurenceLineNums = new ArrayList<>();

                    try(BufferedReader br = new BufferedReader(new FileReader(f))) {
                        int lineIndex=0;
                        for(String line; (line = br.readLine()) != null; ) {
                            // if line != method call ever then aadd to dead code
                            if(line.contains(" ".concat(cm.getMethodName().concat("("))) || line.contains(".".concat(cm.getMethodName().concat("(")))) {
                                // if method call is a declaration
                                if (line.contains(Literals.PROTECTED) && line.contains(Literals.O_BRACKET) && line.contains(Literals.C_BRACKET) && !line.contains("new")) {
                                    possibleOccurenceLineNums.add(lineIndex);
                                }
                                else {
                                    definiteOccurence = true;
                                    break;
                                }
                            }
                            lineIndex++;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(!definiteOccurence) {
                        if (possibleOccurenceLineNums.size() > 1) {
                            for (int i = 0; i < possibleOccurenceLineNums.size(); i++) {
                                possibleOccurenceArray[possibleOccurenceLineNums.get(i)] = true;
                            }
                        }
                        else{
                            definiteDeadCodeArray[possibleOccurenceLineNums.get(0)] = true;
                        }
                    }
                }
            }
            printDeadCodeLines(f, definiteDeadCodeArray, "Protected Definite Dead Code Methods:");
            printDeadCodeLines(f, possibleOccurenceArray, "Protected Possible Dead Code Methods:");
        }
    }
    private void checkProtectedVariables(List<ClassVariable<String, String>> unused){
        for(File f : FileHandler.uploadedFiles) {
            boolean[] definiteDeadCodeArray = new boolean[(int) f.length()];
            boolean[] possibleOccurenceArray = new boolean[(int) f.length()];
            for (ClassVariable<String, String> cv : unused) {
                if(cv.getClassName().equals(FileHandler.removeExtension(f.getName()))){
                    boolean definiteOccurence = false;
                    ArrayList<Integer> possibleOccurenceLineNums = new ArrayList<>();

                    try(BufferedReader br = new BufferedReader(new FileReader(f))) {
                        int lineIndex=0;
                        for(String line; (line = br.readLine()) != null; ) {
                            // if line != method call ever then aadd to dead code
                            if(line.contains(cv.getVariableName())) {
                                // if method call is a declaration
                                // if line contains variable (not method)
                                if (line.contains(Literals.PROTECTED) && !line.contains(Literals.O_BRACKET) && !line.contains(Literals.C_BRACKET) && line.contains("new")) {
                                    possibleOccurenceLineNums.add(lineIndex);
                                }
                                else {
                                    definiteOccurence = true;
                                    break;
                                }
                            }
                            lineIndex++;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(!definiteOccurence) {
                        if (possibleOccurenceLineNums.size() > 1) {
                            for (int i = 0; i < possibleOccurenceLineNums.size(); i++) {
                                possibleOccurenceArray[possibleOccurenceLineNums.get(i)] = true;
                            }
                        }
                        else{
                            definiteDeadCodeArray[possibleOccurenceLineNums.get(0)] = true;
                        }
                    }
                }
            }
            printDeadCodeLines(f, definiteDeadCodeArray, "Protected Definite Dead Code Variables:");
            printDeadCodeLines(f, possibleOccurenceArray, "Protected Possible Dead Code Variables:");
        }
    }




    private void printDeadCodeLines(File f, boolean[] DeadCodeArray, String message){
        System.out.println("\n" + message);
        try { BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            for(int i=0; i<DeadCodeArray.length; i++){
                line = br.readLine();
                if(DeadCodeArray[i]) {
                    System.out.println((i+1) + " " + line.trim());
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean isInterface(File f) {
        try(BufferedReader br = new BufferedReader(new FileReader(f))) {
            for(String line; (line = br.readLine()) != null; ) {
                if(line.contains("public interface")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    private String getMethodDef(String line) {
        String cut = line.substring(0,line.indexOf(Literals.O_BRACKET)).trim();
        String[] cutSplit = cut.trim().split(" ");
        return cutSplit[ cutSplit.length - 1 ].trim();
    }
}
