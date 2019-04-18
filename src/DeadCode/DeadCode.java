package DeadCode;

import FileProcessing.FileHandler;
import InappropriateIntimacy.InappropriateIntimacy;
import Utilities.ClassMethod;
import Utilities.Literals;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeadCode extends JButton implements ActionListener {

    public DeadCode(){
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                report();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
    }

    public int testNum;

    public String report() {

        InappropriateIntimacy II = new InappropriateIntimacy();

        II.print();
        String output="\n\n\nDead Code Checker\n\n";;

        output += "-------Public Methods never called---------" + "\n";
//        System.out.println("-------Public Methods never called---------");
        output += checkIfMethodIsCalledInClass(II.getUnusedPublicMethods(), Literals.PUBLIC, true);
        output += "-------Private Methods never called---------" + "\n";
//        System.out.println("-------Private Methods never called---------");
        output += checkIfMethodIsCalledInClass(new ArrayList<>(), Literals.PRIVATE, true);
        output += "-------Protected Methods never called---------" + "\n";
//        System.out.println("-------Protected Methods never called---------");
        output += checkIfMethodIsCalledInClass(II.getUnusedProtectedMethods(), Literals.PROTECTED, true);
        output += "-------Public Variables never used---------" + "\n";
//        System.out.println("-------Public Variables never used---------");
        output += checkIfMethodIsCalledInClass(II.getUnusedPublicVariables(), Literals.PUBLIC, false);
        output += "-------Private Variables never used---------" + "\n";
//        System.out.println("-------Private Variables never used---------");
        output += checkIfMethodIsCalledInClass(new ArrayList<>(), Literals.PRIVATE, false);
        output += "-------Protected Variables never used---------" + "\n";
//        System.out.println("-------Protected Variables never used---------");
        output += checkIfMethodIsCalledInClass(II.getUnusedProtectedVariables(), Literals.PROTECTED, false);

        System.out.println(output);
        return output;
    }

    private String checkIfMethodIsCalledInClass(List<ClassMethod<String, String>> unused, String accessSpecifier, boolean isMethod){
        String str="";
        for(File f : FileHandler.uploadedFiles) {
            if(FileHandler.uploadedFiles.size() == 1 || accessSpecifier.equals(Literals.PRIVATE)){
                unused = new ArrayList<>();
//            if(!isInterface(f)) {
//                System.out.println("yi");
                try (BufferedReader br = new BufferedReader(new FileReader(f))) {
//                    System.out.println("ya");
//                    int k=0;
                    for (String line; (line = br.readLine()) != null; ) {
//                        k++;
//                        System.out.println("ye");
                        if(isMethod) {
                            if (line.contains(accessSpecifier) && line.contains(Literals.O_BRACKET) && line.contains(Literals.C_BRACKET) && !line.contains("new")) {
                                unused.add(new ClassMethod<>(FileHandler.removeExtension(f.getName()), getMethodDef(line)));
//                                System.out.println("k: " + k);
                            }
                        }
                        else{
                            if (line.contains(accessSpecifier) && !line.contains(Literals.O_BRACKET) && !line.contains(Literals.C_BRACKET) && line.contains("new")) {
                                unused.add(new ClassMethod<>(FileHandler.removeExtension(f.getName()), getMethodDef(line)));
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
//            }
//                System.out.println(f.getName() + " Unused " + accessSpecifier + ":");
//                for(int i=0; i<unused.size(); i++){
//                    System.out.println(unused.get(i).getClassName() + " " + unused.get(i).getMethodName());
//                }
            }
            int fileLength=0;
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                while (br.readLine() != null) {
                    fileLength++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            boolean[] definiteDeadCodeArray = new boolean[fileLength];
            boolean[] possibleOccurenceArray = new boolean[fileLength];
            for (ClassMethod<String, String> cm : unused) {
                if (cm.getClassName().equals(FileHandler.removeExtension(f.getName()))) {
                    boolean definiteOccurence = false;
                    ArrayList<Integer> possibleOccurenceLineNums = new ArrayList<>();

                    try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                        int lineIndex = 0;
                        for (String line; (line = br.readLine()) != null; ) {
                            if (isMethod) {
                                // if line != method call ever then aadd to dead code
                                if (line.contains(cm.getMethodName().concat("("))) {
                                    // if method call is a declaration
                                    if (line.contains(accessSpecifier) && line.contains(Literals.O_BRACKET) && line.contains(Literals.C_BRACKET) && !line.contains("new")) {
                                        possibleOccurenceLineNums.add(lineIndex);
                                    } else {
                                        definiteOccurence = true;
                                        break;
                                    }
                                }
                                lineIndex++;
                            } else {
                                // if line != method call ever then aadd to dead code
                                if (line.contains(cm.getMethodName())) {
                                    // if method call is a declaration
                                    if (line.contains(accessSpecifier) && line.contains(Literals.O_BRACKET) && line.contains(Literals.C_BRACKET) && !line.contains("new")) {
                                        possibleOccurenceLineNums.add(lineIndex);
                                    } else {
                                        definiteOccurence = true;
                                        break;
                                    }
                                }
                                lineIndex++;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (!definiteOccurence) {
                        if (possibleOccurenceLineNums.size() > 1) {
                            for (int i = 0; i < possibleOccurenceLineNums.size(); i++) {
                                possibleOccurenceArray[possibleOccurenceLineNums.get(i)] = true;
                            }
                        } else {
                            definiteDeadCodeArray[possibleOccurenceLineNums.get(0)] = true;
                        }
                    }
                }
            }
            if(isMethod) {
                str += printDeadCodeLines(f, definiteDeadCodeArray, f.getName() + ": " + accessSpecifier + "Methods Definite Dead Code:");
                str += printDeadCodeLines(f, possibleOccurenceArray, f.getName() + ": " + accessSpecifier + "Methods Possible Dead Code:");
            }
            else{
                str += printDeadCodeLines(f, definiteDeadCodeArray, f.getName() + ": " + accessSpecifier + "Variables Definite Dead Code:");
                str += printDeadCodeLines(f, possibleOccurenceArray, f.getName() + ": " + accessSpecifier + "Variables Possible Dead Code:");
            }
        }
        return str;
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
    private void checkPublicVariables(List<ClassMethod<String, String>> unused){
        for(File f : FileHandler.uploadedFiles) {
            boolean[] definiteDeadCodeArray = new boolean[(int) f.length()];
            boolean[] possibleOccurenceArray = new boolean[(int) f.length()];
            for (ClassMethod<String, String> cv : unused) {
                if(cv.getClassName().equals(FileHandler.removeExtension(f.getName()))){
                    boolean definiteOccurence = false;
                    ArrayList<Integer> possibleOccurenceLineNums = new ArrayList<>();

                    try(BufferedReader br = new BufferedReader(new FileReader(f))) {
                        int lineIndex=0;
                        for(String line; (line = br.readLine()) != null; ) {
                            // if line != method call ever then aadd to dead code
                            if(line.contains(cv.getClassName())) {
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
                List<ClassMethod<String, String>> variables = new ArrayList<>();
                try(BufferedReader br = new BufferedReader(new FileReader(f))) {
                    for(String line; (line = br.readLine()) != null; ) {
                        // if line contains a variable (not a method) add to list
                        if(line.contains(Literals.PRIVATE) && !line.contains(Literals.O_BRACKET) && !line.contains(Literals.C_BRACKET) && line.contains("new")) {
                            variables.add(new ClassMethod<>(FileHandler.removeExtension(f.getName()), getMethodDef(line)));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                boolean[] definiteDeadCodeArray = new boolean[(int) f.length()];
                boolean[] possibleOccurenceArray = new boolean[(int) f.length()];
                for (ClassMethod<String, String> cv : variables) {
                    if(cv.getClassName().equals(FileHandler.removeExtension(f.getName()))){
                        boolean definiteOccurence = false;
                        ArrayList<Integer> possibleOccurenceLineNums = new ArrayList<>();

                        try(BufferedReader br = new BufferedReader(new FileReader(f))) {
                            int lineIndex=0;
                            for(String line; (line = br.readLine()) != null; ) {
                                // if line != method call ever then aadd to dead code
                                if(line.contains(cv.getMethodName())) {
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
    private void checkProtectedVariables(List<ClassMethod<String, String>> unused){
        for(File f : FileHandler.uploadedFiles) {
            boolean[] definiteDeadCodeArray = new boolean[(int) f.length()];
            boolean[] possibleOccurenceArray = new boolean[(int) f.length()];
            for (ClassMethod<String, String> cv : unused) {
                if(cv.getClassName().equals(FileHandler.removeExtension(f.getName()))){
                    boolean definiteOccurence = false;
                    ArrayList<Integer> possibleOccurenceLineNums = new ArrayList<>();

                    try(BufferedReader br = new BufferedReader(new FileReader(f))) {
                        int lineIndex=0;
                        for(String line; (line = br.readLine()) != null; ) {
                            // if line != method call ever then aadd to dead code
                            if(line.contains(cv.getMethodName())) {
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






    private String printDeadCodeLines(File f, boolean[] DeadCodeArray, String message){
        String str = "";
        boolean printMessage = true;
        try { BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            for(int i=0; i<DeadCodeArray.length; i++){
                line = br.readLine();
                if(DeadCodeArray[i]) {
                    if(printMessage) {
                        str += message + "\n";
//                            System.out.println(message);
                        printMessage = false;
                    }
                    str += /*(i+1) + " " + */line.trim() + "\n";
//                    System.out.println((i+1) + " " + line.trim());
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!printMessage) str += "\n"; //System.out.println();
        return str;
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
