package Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import FileProcessing.FileHandler;

public class FileParser {
	
	public static String getMethodDef(String line) {
		String cut = line.substring(0,line.indexOf(Literals.O_BRACKET)).trim();
		String[] cutSplit = cut.trim().split(" ");
		return cutSplit[ cutSplit.length - 1 ].trim();
	}

	public static boolean isInterface(File f) {
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
	
	public static boolean isClassDef(String line) {
		for(String cName: FileHandler.classes) {
			if(line.contains(cName) && line.contains("new") && line.contains(Literals.EQUALS)) {
				return true;
			}
		}
		return false;
	}
	
	public static String getMethodCall(String line, String objectName) {
		int startIndex = line.indexOf(objectName+".");
		String cut = line.substring(startIndex);
		int index = 0;
		int endIndex = 0;
		for(char c : cut.toCharArray()) {
			if(!(Character.isAlphabetic(c) || Character.isDigit(c))) {
				endIndex = index;
			}else {
				index++;
			}
		}
		return cut.substring(cut.indexOf(".")+1, endIndex);
	}
	public static boolean isComment(String line) {
		if(line.trim().startsWith("//")) {
			return true;
		}
		return false;
	}

	
}
