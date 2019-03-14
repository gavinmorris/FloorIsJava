package DeadCode;

import FileProcessing.FileHandler;
import General.ClassMethod;
import General.ClassObjectTuple;
import General.ClassVariable;
import General.Literals;
import InappropriateIntimacy.InappropriateIntimacy;

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
        System.out.println("-------Public Methods to be made private.---------");
        checkPublicMethods(new InappropriateIntimacy().report());
        checkPrivateMethods();
        checkProtectedMethods(new InappropriateIntimacy().report());
        checkPublicVariables(new ArrayList<ClassVariable<String, String>>());
        checkPrivateVariables();
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

            System.out.println("\nPublic Definite Dead Code:");
            try { BufferedReader br = new BufferedReader(new FileReader(f));
                String line;
                for(int i=0; i<definiteDeadCodeArray.length; i++){
                    line = br.readLine();
                    if(definiteDeadCodeArray[i]) {
                        System.out.println((i+1) + " " + line.trim());
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("\nPublic Possible Dead Code:");
            try { BufferedReader br = new BufferedReader(new FileReader(f));
                String line;
                for(int i=0; i<possibleOccurenceArray.length; i++){
                    line = br.readLine();
                    if(possibleOccurenceArray[i]) {
                        System.out.println((i+1) + " " + line.trim());
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

            System.out.println("\nPublic Definite Dead Code (Variables):");
            try { BufferedReader br = new BufferedReader(new FileReader(f));
                String line;
                for(int i=0; i<definiteDeadCodeArray.length; i++){
                    line = br.readLine();
                    if(definiteDeadCodeArray[i]) {
                        System.out.println((i+1) + " " + line.trim());
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("\nPublic Possible Dead Code:");
            try { BufferedReader br = new BufferedReader(new FileReader(f));
                String line;
                for(int i=0; i<possibleOccurenceArray.length; i++){
                    line = br.readLine();
                    if(possibleOccurenceArray[i]) {
                        System.out.println((i+1) + " " + line.trim());
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

                System.out.println("\nPrivate Definite Dead Code:");
                try { BufferedReader br = new BufferedReader(new FileReader(f));
                    String line;
                    for(int i=0; i<definiteDeadCodeArray.length; i++){
                        line = br.readLine();
                        if(definiteDeadCodeArray[i]) {
                            System.out.println((i+1) + " " + line.trim());
                        }
                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("\nPrivate Possible Dead Code:");
                try { BufferedReader br = new BufferedReader(new FileReader(f));
                    String line;
                    for(int i=0; i<possibleOccurenceArray.length; i++){
                        line = br.readLine();
                        if(possibleOccurenceArray[i]) {
                            System.out.println((i+1) + " " + line.trim());
                        }
                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                        if(line.contains(Literals.PRIVATE) && line.contains(Literals.O_BRACKET) && line.contains(Literals.C_BRACKET) && !line.contains("new")) {
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
                                if(line.contains(" ".concat(cv.getVariableName().concat("("))) || line.contains(".".concat(cv.getVariableName().concat("(")))) {
                                    // if method call is a declaration
                                    // if line contains a variable (not a method) add to list
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

                System.out.println("\nPrivate Definite Dead Code Variables:");
                try { BufferedReader br = new BufferedReader(new FileReader(f));
                    String line;
                    for(int i=0; i<definiteDeadCodeArray.length; i++){
                        line = br.readLine();
                        if(definiteDeadCodeArray[i]) {
                            System.out.println((i+1) + " " + line.trim());
                        }
                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("\nPrivate Possible Dead Code Variables:");
                try { BufferedReader br = new BufferedReader(new FileReader(f));
                    String line;
                    for(int i=0; i<possibleOccurenceArray.length; i++){
                        line = br.readLine();
                        if(possibleOccurenceArray[i]) {
                            System.out.println((i+1) + " " + line.trim());
                        }
                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

            System.out.println("\nPublic Definite Dead Code:");
            try { BufferedReader br = new BufferedReader(new FileReader(f));
                String line;
                for(int i=0; i<definiteDeadCodeArray.length; i++){
                    line = br.readLine();
                    if(definiteDeadCodeArray[i]) {
                        System.out.println((i+1) + " " + line.trim());
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("\nPublic Possible Dead Code:");
            try { BufferedReader br = new BufferedReader(new FileReader(f));
                String line;
                for(int i=0; i<possibleOccurenceArray.length; i++){
                    line = br.readLine();
                    if(possibleOccurenceArray[i]) {
                        System.out.println((i+1) + " " + line.trim());
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                            if(line.contains(" ".concat(cv.getVariableName().concat("("))) || line.contains(".".concat(cv.getVariableName().concat("(")))) {
                                // if method call is a declaration
                                // if line contains variable (not method)
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

            System.out.println("\nPublic Definite Dead Code Variables:");
            try { BufferedReader br = new BufferedReader(new FileReader(f));
                String line;
                for(int i=0; i<definiteDeadCodeArray.length; i++){
                    line = br.readLine();
                    if(definiteDeadCodeArray[i]) {
                        System.out.println((i+1) + " " + line.trim());
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("\nPublic Possible Dead Code Variables:");
            try { BufferedReader br = new BufferedReader(new FileReader(f));
                String line;
                for(int i=0; i<possibleOccurenceArray.length; i++){
                    line = br.readLine();
                    if(possibleOccurenceArray[i]) {
                        System.out.println((i+1) + " " + line.trim());
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





//    private void print() {
//        for(ClassMethod<String, String> cm: unused) {
//            System.out.println(cm.getClassName()+" : "+cm.getMethodName());
//        }
//    }
//
//    private void lookForObjects() {
//        for(File f : FileHandler.uploadedFiles) {
//            try(BufferedReader br = new BufferedReader(new FileReader(f))) {
//                for(String line; (line = br.readLine()) != null; ) {
//                    if(isClassDef(line)) {
//                        getObject(line.trim(), FileHandler.removeExtension(f.getName()));
//                    }else {
//                        for(ClassObjectTuple<String,String> cot : cml) {
//                            if(!cot.getClassName().equals(FileHandler.removeExtension(f.getName())) && line.contains(cot.getObjectName()+".") && line.contains(Literals.O_BRACKET) && line.contains(Literals.C_BRACKET)){
//                                String methodName = getMethodCall(line, cot.getObjectName()).trim();
//                                if(!cot.methodExists(methodName)) {
//                                    cot.addMethodName(methodName);
//                                }
//                            }
//                        }
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private String getMethodCall(String line, String objectName) {
//        int startIndex = line.indexOf(objectName+".");
//        String cut = line.substring(startIndex);
//        int endIndex = cut.indexOf("(");
//        return cut.substring(cut.indexOf(".")+1, endIndex);
//    }
//
//    //TODO: work with new approach
//    private void getObject(String line, String fileName) {
//        String className = line.split(" ")[0].trim();
//        String objectName = line.split(" ")[1].trim();
//        if(!fileName.equals(className)) {
//            cml.add(new ClassObjectTuple<String, String>(className, objectName));
//        }
//    }
//
//    private boolean isClassDef(String line) {
//        for(String cName: FileHandler.classes) {
//            if(line.contains(cName) && line.contains("new") && line.contains(Literals.EQUALS)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private void checkMethods() {
//        for(File f : FileHandler.uploadedFiles) {
//            if(!isInterface(f)) {
//                try(BufferedReader br = new BufferedReader(new FileReader(f))) {
//                    for(String line; (line = br.readLine()) != null; ) {
//                        if(line.contains(Literals.PUBLIC ) && line.contains(Literals.O_BRACKET) && line.contains(Literals.C_BRACKET)) {
//                            String method = getMethodDef(line);
//                            if(!isUsed(method,FileHandler.removeExtension(f.getName())) && !method.equals(FileHandler.removeExtension(f.getName()))) {
//                                if(!unused.contains(method))
//                                    unused.add(new ClassMethod<String, String>(FileHandler.removeExtension(f.getName()), method));
//                            }
//                        }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
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
//
//    private boolean isUsed(String methodDef, String className) {
//        for(ClassObjectTuple<String, String> cot: cml) {
//            if(cot.getClassName().equals(className)) {
//                if(cot.getMethodsList().contains(methodDef)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
    private String getMethodDef(String line) {
        String cut = line.substring(0,line.indexOf(Literals.O_BRACKET)).trim();
        String[] cutSplit = cut.trim().split(" ");
        return cutSplit[ cutSplit.length - 1 ].trim();
    }
}
