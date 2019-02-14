package PrimtiveObsession;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import FileProcessing.FileHandler;
import General.Literals;


public class PrimitiveObsession implements PO{

    private List<String> classes = FileHandler.classes;

    public void report() {
    	checkLines();
	    System.out.println("Primitive Data Types: "+primitiveDataTypes);
	    System.out.println("Class Objects: "+ classObjects);
    }
    private int primitiveDataTypes= 0;
    private int classObjects = 0;
    
    public void countPDT() {
    	primitiveDataTypes++;
    }
    public void countCO() {
    	classObjects++;
    }

    public void checkLines() {
    	for(File f : FileHandler.uploadedFiles) {
    		try(BufferedReader br = new BufferedReader(new FileReader(f))) {
    		    for(String line; (line = br.readLine()) != null; ) {
    		        if(line.contains(PrimtiveDataTypes.BOOLEAN) && line.contains(Literals.EQUALS)) {
    		        	countPDT();
    		        }
    		        else if(line.contains(PrimtiveDataTypes.BYTE) && line.contains(Literals.EQUALS)) {
    		        	countPDT();
    		        }
    		        else if(line.contains(PrimtiveDataTypes.CHAR) && line.contains(Literals.EQUALS)) {
    		        	countPDT();
    		        }
    		        else if(line.contains(PrimtiveDataTypes.DOUBLE) && line.contains(Literals.EQUALS)) {
    		        	countPDT();
    		        }
    		        else if(line.contains(PrimtiveDataTypes.FLOAT) && line.contains(Literals.EQUALS)) {
    		        	countPDT();
    		        }
    		        else if(line.contains(PrimtiveDataTypes.INT) && line.contains(Literals.EQUALS)) {
    		        	countPDT();
    		        }
    		        else if(line.contains(PrimtiveDataTypes.LONG) && line.contains(Literals.EQUALS)) {
    		        	countPDT();
    		        }
    		        else if(line.contains(PrimtiveDataTypes.SHORT) && line.contains(Literals.EQUALS)) {
    		        	countPDT();
    		        }
    		        else if(classCheck(line)) {
    		        	countCO();
    		        }
    		    }
    		} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    
    public boolean classCheck(String line) {
    	boolean classDefFound = false;
    	for(String cd : classes) {
    		if((line.contains(cd) && line.contains(Literals.EQUALS)) || line.contains(cd+".")) {
    			classDefFound = true;
    			break;
    		}
    	}
    	return classDefFound;
    }
    

}