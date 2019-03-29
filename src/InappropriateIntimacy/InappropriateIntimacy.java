package InappropriateIntimacy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import FileProcessing.FileHandler;
import Utilities.ClassMethod;
import Utilities.ClassObjectTuple;
import Utilities.FileParser;
import Utilities.Literals;
import Utilities.Smells;
import Utilities.SuperSub;

import javax.swing.*;

public class InappropriateIntimacy extends JButton implements ActionListener, Smells {

	private static final long serialVersionUID = 1L;

	private List<ClassObjectTuple<String,String>> cml = new ArrayList<ClassObjectTuple<String,String>>();
	private List<ClassMethod<String, String>> unused = new ArrayList<ClassMethod<String, String>>();

	public InappropriateIntimacy(){
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				report();
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		report();
		
	}
	
	public void report() {
		System.out.println("-------Public Methods to be made private.---------");
		lookForObjects();
		lookForVariables();
		checkPublicMethods();
		print();
	}

	private void lookForVariables() {
		for(File f : FileHandler.uploadedFiles) {
    		try(BufferedReader br = new BufferedReader(new FileReader(f))) {
    		    for(String line; (line = br.readLine()) != null; ) {
    		    	
    		    }
    		} catch (IOException e) {
				e.printStackTrace();
			}
    	}
		
	}
	
	public List<ClassMethod<String, String>> getUnused() {
		report();
		return unused;
	}
	
	private void print() {
		for(ClassMethod<String, String> cm: unused) {
			System.out.println(cm.getClassName()+" : "+cm.getMethodName());
		}
	}
	
	
	private void lookForObjects() {
		//loop through files
		for(File f : FileHandler.uploadedFiles) {
			//file cannot be an interface
			if(!FileParser.isInterface(f)) {
	    		try(BufferedReader br = new BufferedReader(new FileReader(f))) {
	    		    for(String line; (line = br.readLine()) != null; ) {
	    		    	//if line is not a comment proceed
	    		    	if(!FileParser.isComment(line)) {
	    		    		//if this line contains a class definition, proceed
		    		        if(FileParser.isClassDef(line)) {
		    		        	//get object and add to list
		    		        	getObject(line.trim(), FileHandler.removeExtension(f.getName()));
		    		        }else {
		    		        	//if not a class def check for object call, loop through the call 
			    		        for(ClassObjectTuple<String,String> cot : cml) {
			    		        	//make sure that the class of the object does not match the file it is looking in
			    		        	if(!cot.getClassName().equals(FileHandler.removeExtension(f.getName())) && line.contains(cot.getObjectName()+".") && line.contains(Literals.O_BRACKET) && line.contains(Literals.C_BRACKET)){
			    		        		//get method name
			    		        		String methodName = FileParser.getMethodCall(line, cot.getObjectName()).trim();
			    		        		//if the method has not already been added it, add the method
			    		        		if(!cot.methodExists(methodName)) {
			    		        			cot.addMethodName(methodName);
			    		        		}
			    		        	}
		    		        	}
		    		        }
	    		    	}
	    		    }
	    		} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	private void getObject(String line, String fileName) {
		String className = line.split(" ")[0].trim();
		String objectName = line.split(" ")[1].trim();
		if(!fileName.equals(className)) {
			cml.add(new ClassObjectTuple<String, String>(className, objectName));
		}
	}
	
	private void checkPublicMethods() {
		for(File f : FileHandler.uploadedFiles) {
			//if the class is not an interface, proceed
			if(!FileParser.isInterface(f)) {
				try(BufferedReader br = new BufferedReader(new FileReader(f))) {
	    		    for(String line; (line = br.readLine()) != null; ) {
	    		    	//if line is not a comment, proceed
	    		    	if(!FileParser.isComment(line)) {
	    		    		//check if it is a public method
		    		    	if(line.contains(Literals.PUBLIC ) && line.contains(Literals.O_BRACKET) && line.contains(Literals.C_BRACKET)) {
		    		    		//get method name
		    		    		String method = FileParser.getMethodDef(line);
		    		    		//check if this method has been used and that the method is not a constructor
		    		    		if(!isUsed(method,FileHandler.removeExtension(f.getName())) && !method.equals(FileHandler.removeExtension(f.getName()))) {
		    		    			if(!unused.contains(method))
		    		    				unused.add(new ClassMethod<String, String>(FileHandler.removeExtension(f.getName()), method));
		    		    		}
		    		    	}
	    		    	}
	    		    }
	    		} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private boolean isUsed(String methodDef, String className) {
		for(ClassObjectTuple<String, String> cot: cml) {
			if(cot.getClassName().equals(className)) {
				if(cot.getMethodsList().contains(methodDef)) {
					return true;
				}
			}
		}
		return false;
	}

}