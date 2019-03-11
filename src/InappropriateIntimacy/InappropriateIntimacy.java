package InappropriateIntimacy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import FileProcessing.FileHandler;
import General.Literals;
import General.ClassObjectTuple;

import javax.swing.*;

public class InappropriateIntimacy extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;

	private List<ClassObjectTuple<String,String,String>> cml = new ArrayList<ClassObjectTuple<String, String,String>>();

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
		print();
	}
	
	public void print() {
		for(ClassObjectTuple<String,String, String> cot : cml) {
			System.out.println(cot.getFileName()+" : "+cot.getClassName()+" : "+cot.getObjectName()+" : "+cot.getMethods());
		}
	}
	
	public void lookForObjects() {
		for(File f : FileHandler.uploadedFiles) {
    		try(BufferedReader br = new BufferedReader(new FileReader(f))) {
    		    for(String line; (line = br.readLine()) != null; ) {
    		        if(isClassDef(line)) {
    		        	getObject(line, FileHandler.removeExtension(f.getName()));
    		        }else {
	    		        for(ClassObjectTuple<String,String, String> cot : cml) {
	    		        	if(cot.getFileName().equals(FileHandler.removeExtension(f.getName())) && line.contains(cot.getObjectName()+".")){
	    		        		cot.addMethodName(getMethodCall(line, cot.getObjectName()).trim());
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
		for(String s: line.trim().split(" ")) {
			if(s.contains(objectName+".")) {
				//TODO finish this
			}
		}
		return "";
	}

	//TODO: work with new approach
	public void getObject(String line, String fileName) {
		String className = line.split(" ")[0].trim();
		String objectName = line.split(" ")[1].trim();
		cml.add(new ClassObjectTuple<String, String, String>(fileName, className, objectName));
	}

	public boolean isClassDef(String line) {
		for(String cName: FileHandler.classes) {
			if(line.contains(cName) && line.contains("new") && line.contains(Literals.EQUALS)) {
				return true;
			}
		}
		return false;
	}
	
	
}