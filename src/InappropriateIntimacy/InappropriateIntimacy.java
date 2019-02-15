package InappropriateIntimacy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import FileProcessing.FileHandler;
import General.Literals;

public class InappropriateIntimacy {

	private List<String> methods = new ArrayList<String>();
	Map<String, Integer> methodUse = new Hashtable<String, Integer>();
	

	public void report() {
		getPublicMethods();
    	fillHashTable();
    	checkIfUsed();
    	System.out.println(methodUse.toString());
	}
	
	public void getPublicMethods() {
		for(File f : FileHandler.uploadedFiles) {
    		try(BufferedReader br = new BufferedReader(new FileReader(f))) {
    		    for(String line; (line = br.readLine()) != null; ) {
    		    	checkLine(line);
    		    }
    		} catch (IOException e) {
				e.printStackTrace();
			}
    	}
	}

	public void checkLine(String line) {
		if(line.contains(Literals.PUBLIC) && (line.contains(Literals.O_BRACKET) && line.contains(Literals.C_BRACKET))) {
			String className = getMethodName(line);
			methods.add(className);
		}
	}

	private String getMethodName(String line) {
		String[] methodName = line.substring(0,line.indexOf(Literals.O_BRACKET)).split(" ");
		return methodName[methodName.length-1].trim();
	}
	
	private void fillHashTable() {
		for(String m: methods) {
			methodUse.put(m, 0);
		}
	}

	
	
	public boolean checkIfUsed() {
		for(File f : FileHandler.uploadedFiles) {
    		try(BufferedReader br = new BufferedReader(new FileReader(f))) {
    		    for(String line; (line = br.readLine()) != null; ) {
    		    	processLine(line);
    		    }
    		} catch (IOException e) {
				e.printStackTrace();
			}
    	}
		return false;
	}

	private void processLine(String line) {
		for(String m: methods) {
			if(line.contains(m)) {
				int val = methodUse.containsKey(m) ? methodUse.get(m) : 0;
				methodUse.put(m, val+1);
				break;
			}
		}
		
		
	}


}