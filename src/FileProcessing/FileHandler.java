	package FileProcessing;


import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import Utilities.SuperSub;

public class FileHandler {

    public static ArrayList<File> uploadedFiles = new ArrayList<File>();
    public static ArrayList<String> classes = new ArrayList<String>();
    public static ArrayList<SuperSub<String, String>> supersub = new ArrayList<SuperSub<String, String>>();
    
    public static void addNewFile(Path path){
        if(isFileTypeJava(path.toString()))
        	uploadedFiles.add(new File(path.toString()));
    }
    public static boolean isFileTypeJava(String path) {
        if(path.endsWith(".java"))
            return true;
        else {
            return false;
        }
    }
    public static ArrayList<String> getClasses(){
        for(File file: uploadedFiles) {
            String path = file.getName();
            classes.add(path.replace(".java", "").trim());
        }
        return classes;
    }
    
    
    public static String removeExtension(String fileName) {
    	return fileName.replace(".java", "").trim();
    }
    
	public static String getClassObject(String line, String className) {
		String[] strArr = line.substring(line.indexOf(className)).split(" ");
		return strArr[1].trim();
	}
	
	public static String getSuperclass(String line) {
		int startIndex = line.indexOf("extends");
		return line.substring(startIndex).split(" ")[1].trim();

	}
	
}