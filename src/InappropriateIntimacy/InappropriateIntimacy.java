package InappropriateIntimacy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import FileProcessing.FileHandler;
import General.Literals;

public class InappropriateIntimacy {

	private List<String> methods = new ArrayList<String>();
	

	public void report() {

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
	
	public void getPublicVariables() {
		// TODO Auto-generated method stub
	}


	public boolean isUsed(String methodName) {
		
		return false;
	}


}