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
import General.Literals;
import General.ClassObjectTuple;

import javax.swing.*;

public class InappropriateIntimacy extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;

	private List<ClassObjectTuple<String,String>> cml = new ArrayList<ClassObjectTuple<String,String>>();
	private List<String> unused = new ArrayList<String>();
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
		for(String methods: unused) {
			System.out.println(methods);
		}
	}
	
	private void lookForObjects() {
		for(File f : FileHandler.uploadedFiles) {
    		try(BufferedReader br = new BufferedReader(new FileReader(f))) {
    		    for(String line; (line = br.readLine()) != null; ) {
    		        if(isClassDef(line)) {
    		        	getObject(line.trim(), FileHandler.removeExtension(f.getName()));
    		        }else {
	    		        for(ClassObjectTuple<String,String> cot : cml) {
	    		        	if(!cot.getClassName().equals(FileHandler.removeExtension(f.getName())) && line.contains(cot.getObjectName()+".") && line.contains(Literals.O_BRACKET) && line.contains(Literals.C_BRACKET)){
	    		        		String methodName = getMethodCall(line, cot.getObjectName()).trim();
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
	
	private String getMethodCall(String line, String objectName) {
		int startIndex = line.indexOf(objectName+".");
		String cut = line.substring(startIndex);
		int endIndex = cut.indexOf("(");
		return cut.substring(cut.indexOf(".")+1, endIndex);
	}

	//TODO: work with new approach
	private void getObject(String line, String fileName) {
		String className = line.split(" ")[0].trim();
		String objectName = line.split(" ")[1].trim();
		if(!fileName.equals(className)) {
			cml.add(new ClassObjectTuple<String, String>(className, objectName));
		}
	}

	private boolean isClassDef(String line) {
		for(String cName: FileHandler.classes) {
			if(line.contains(cName) && line.contains("new") && line.contains(Literals.EQUALS)) {
				return true;
			}
		}
		return false;
	}
	
	private void checkMethods() {
		for(File f : FileHandler.uploadedFiles) {
			if(!isInterface(f)) {
				try(BufferedReader br = new BufferedReader(new FileReader(f))) {
	    		    for(String line; (line = br.readLine()) != null; ) {
	    		    	if(line.contains(Literals.PUBLIC ) && line.contains(Literals.O_BRACKET) && line.contains(Literals.C_BRACKET)) {
	    		    		String method = getMethodDef(line);
	    		    		if(!isUsed(method,FileHandler.removeExtension(f.getName())) && !method.equals(FileHandler.removeExtension(f.getName()))) {
	    		    			if(!unused.contains(method))
	    		    				unused.add(method);
	    		    		}
	    		    	}
	    		    }
	    		} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
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

	private String getMethodDef(String line) {
		String cut = line.substring(0,line.indexOf(Literals.O_BRACKET)).trim();
		String[] cutSplit = cut.trim().split(" ");
		return cutSplit[ cutSplit.length - 1 ].trim();
	}
	
}