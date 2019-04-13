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
	//protected and public methods
	private List<ClassMethod<String, String>> unusedPublicMethods = new ArrayList<ClassMethod<String, String>>();
	private List<ClassMethod<String, String>> unusedProtectedMethods = new ArrayList<ClassMethod<String, String>>();
	private List<ClassMethod<String, String>> unusedProtectedVariables = new ArrayList<ClassMethod<String, String>>();
	private List<ClassMethod<String, String>> unusedPublicVariables = new ArrayList<ClassMethod<String, String>>();

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
		lookForObjects();
		checkPublicMethods();
		checkPublicVariables();
	}

	
	public List<ClassMethod<String, String>> getUnusedProtectedMethods() {
		report();
		return unusedProtectedMethods;
	}
	public List<ClassMethod<String, String>> getUnusedPublicMethods() {
		report();
		return unusedProtectedMethods;
	}
	
	public List<ClassMethod<String, String>> getUnusedProtectedVariables() {
		report();
		return unusedProtectedVariables;
	}
	
	public List<ClassMethod<String, String>> getUnusedPublicariables() {
		report();
		return unusedPublicVariables;
	}
	
	private void print() {
		for(ClassMethod<String, String> cm: unusedPublicMethods) {
			System.out.println(cm.getClassName()+" : "+cm.getMethodName());
		}
		for(ClassMethod<String, String> cm: unusedProtectedMethods) {
			System.out.println(cm.getClassName()+" : "+cm.getMethodName());
		}
		for(ClassMethod<String, String> cm: unusedPublicVariables) {
			System.out.println(cm.getClassName()+" : "+cm.getMethodName());
		}
		for(ClassMethod<String, String> cm: unusedProtectedVariables) {
			System.out.println(cm.getClassName()+" : "+cm.getMethodName());
		}
	}
	
	
	private void lookForObjects() {
		//loop through files
		for(File f : FileHandler.uploadedFiles) {
			//file cannot be an interface
			if(!FileParser.isInterface(f)) {
				//for static methods
				String className = FileHandler.removeExtension(f.getName());
				cml.add(new ClassObjectTuple<String, String>(className, className));
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
			    		        	if(!cot.getClassName().equals(FileHandler.removeExtension(f.getName())) && line.contains(cot.getObjectName()+".")){
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
		    		    	if((line.trim().startsWith(Literals.PUBLIC) || line.trim().startsWith(Literals.PROTECTED)) && line.trim().contains(Literals.O_BRACKET) && line.trim().contains(Literals.C_BRACKET)) {
		    		    		//get method name
		    		    		String method = FileParser.getMethodDef(line);
		    		    		//check if this method has been used and that the method is not a constructor
		    		    		if(!isUsed(method,FileHandler.removeExtension(f.getName())) && !method.equals(FileHandler.removeExtension(f.getName()))) {
		    		    			if(!unusedProtectedMethods.contains(method) &&  line.trim().startsWith(Literals.PROTECTED))
		    		    				unusedProtectedMethods.add(new ClassMethod<String, String>(FileHandler.removeExtension(f.getName()), method));
		    		    			if(!unusedPublicMethods.contains(method) &&  line.trim().startsWith(Literals.PUBLIC))
		    		    				unusedPublicMethods.add(new ClassMethod<String, String>(FileHandler.removeExtension(f.getName()), method));
		    		    		
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
	
	private void checkPublicVariables() {
		for(File f : FileHandler.uploadedFiles) {
			//if the class is not an interface, proceed
			if(!FileParser.isInterface(f)) {
				try(BufferedReader br = new BufferedReader(new FileReader(f))) {
	    		    for(String line; (line = br.readLine()) != null; ) {
	    		    	//if line is not a comment, proceed
	    		    	if(!FileParser.isComment(line)) {
	    		    		//if line starts with public and doesn't contain brackets;
	    		    		if((line.trim().startsWith(Literals.PUBLIC) || line.trim().startsWith(Literals.PROTECTED)) && !(line.contains(Literals.O_BRACKET) || line.contains(Literals.C_BRACKET))&&line.contains(Literals.EQUALS)) {
	    		    			//get variable definition
	    		    			String variable = FileParser.getVariableDef(line.trim());
	    		    			if(!isUsed(variable,FileHandler.removeExtension(f.getName())) && !variable.equals(FileHandler.removeExtension(f.getName()))) {
		    		    			if(!unusedPublicVariables.contains(variable) &&line.trim().startsWith(Literals.PUBLIC))
		    		    				unusedPublicVariables.add(new ClassMethod<String, String>(FileHandler.removeExtension(f.getName()), variable));
		    		    			if(!unusedProtectedVariables.contains(variable) &&line.trim().startsWith(Literals.PROTECTED))
		    		    				unusedProtectedVariables.add(new ClassMethod<String, String>(FileHandler.removeExtension(f.getName()), variable));
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