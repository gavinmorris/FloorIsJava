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
		checkMethods();
		print();
	}
	
	private void print() {
		for(ClassMethod<String, String> cm: unused) {
			System.out.println(cm.getClassName()+" : "+cm.getMethodName());
		}
	}
	
	private void lookForObjects() {
		for(File f : FileHandler.uploadedFiles) {
    		try(BufferedReader br = new BufferedReader(new FileReader(f))) {
    		    for(String line; (line = br.readLine()) != null; ) {
    		        if(FileParser.isClassDef(line)) {
    		        	getObject(line.trim(), FileHandler.removeExtension(f.getName()));
    		        }else {
	    		        for(ClassObjectTuple<String,String> cot : cml) {
	    		        	if(!cot.getClassName().equals(FileHandler.removeExtension(f.getName())) && line.contains(cot.getObjectName()+".") && line.contains(Literals.O_BRACKET) && line.contains(Literals.C_BRACKET)){
	    		        		String methodName = FileParser.getMethodCall(line, cot.getObjectName()).trim();
	    		        		if(!cot.methodExists(methodName)) {
	    		        			cot.addMethodName(methodName);
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
	

	private void getObject(String line, String fileName) {
		String className = line.split(" ")[0].trim();
		String objectName = line.split(" ")[1].trim();
		if(!fileName.equals(className)) {
			cml.add(new ClassObjectTuple<String, String>(className, objectName));
		}
	}
	
	private void checkMethods() {
		for(File f : FileHandler.uploadedFiles) {
			if(!FileParser.isInterface(f)) {
				try(BufferedReader br = new BufferedReader(new FileReader(f))) {
	    		    for(String line; (line = br.readLine()) != null; ) {
	    		    	if(line.contains(Literals.PUBLIC ) && line.contains(Literals.O_BRACKET) && line.contains(Literals.C_BRACKET)) {
	    		    		String method = FileParser.getMethodDef(line);
	    		    		if(!isUsed(method,FileHandler.removeExtension(f.getName())) && !method.equals(FileHandler.removeExtension(f.getName()))) {
	    		    			if(!unused.contains(method))
	    		    				unused.add(new ClassMethod<String, String>(FileHandler.removeExtension(f.getName()), method));
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