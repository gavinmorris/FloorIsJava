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
import General.Pair;
import General.Triple;

import javax.swing.*;

public class InappropriateIntimacy extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;
	private List<String> methods = new ArrayList<String>();
	private List<String> variables = new ArrayList<String>();
	private List<String> objects = new ArrayList<String>();

	private List<Triple<String,String, Integer>> mTriples = new ArrayList<Triple<String,String, Integer>>();
	private List<Pair<String, String>> oPair = new ArrayList<Pair<String, String>>();

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
		getPublicMethods();
		System.out.println(objects.toString());
		checkIfUsed();
		System.out.println("-------Public Methods to be made private.---------");
		print();
	}

	private void print() {
		for(Triple<String,String, Integer> t: mTriples) {
			if(t.getOcurrence() <= 1) {
				System.out.println(t.getClassName()+" : "+t.getMethodName());
			}
		}
	}

	private void getPublicMethods() {
		for(File f : FileHandler.uploadedFiles) {
			try(BufferedReader br = new BufferedReader(new FileReader(f))) {
				for(String line; (line = br.readLine()) != null; ) {
					checkLine(line, FileHandler.removeExtension(f.getName()));
					getClassObjects(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void getClassObjects(String line) {
		for(String className: FileHandler.classes) {
			if(line.contains(className) && line.contains("new") && line.contains(Literals.EQUALS)) {
				oPair.add(new Pair<String, String>(className, FileHandler.getClassObject(line, className)));
			}
		}
	}

	private void checkLine(String line, String fileName) {
		if(line.contains(Literals.PUBLIC) && (line.contains(Literals.O_BRACKET) && line.contains(Literals.C_BRACKET)) && !(line.contains(Literals.GREATER_THAN)&&line.contains(Literals.LESS_THAN))) {
			String methodName = getMethodName(line);
			if(!exists(methodName, fileName)) {
				Triple<String, String, Integer> cmp = new Triple<String, String, Integer>(fileName, methodName,0);
				mTriples.add(cmp);
				methods.add(methodName);
			}
		}
		else if(line.contains(Literals.PUBLIC) && line.contains(Literals.EQUALS)) {
			String varName = getVariableName(line);
			if(!exists(varName, fileName)) {
				Triple<String, String, Integer> cmp = new Triple<String, String, Integer>(fileName, varName,0);
				mTriples.add(cmp);
				variables.add(varName);
			}
		}
	}

	private boolean exists(String methodName, String fileName) {
		for(Triple<String, String, Integer> t :mTriples) {
			if(t.getClassName().equals(fileName) && t.getMethodName().equals(methodName)) {
				return true;
			}
		}
		return false;
	}

	private String getVariableName(String line) {
		String[] methodName = line.substring(0,line.indexOf(Literals.EQUALS)).split(" ");
		return methodName[methodName.length-1].trim();
	}

	private String getMethodName(String line) {
		String[] methodName = line.substring(0,line.indexOf(Literals.O_BRACKET)).split(" ");
		return methodName[methodName.length-1].trim();
	}

	private boolean checkIfUsed() {
		for(File f : FileHandler.uploadedFiles) {
			try(BufferedReader br = new BufferedReader(new FileReader(f))) {
				for(String line; (line = br.readLine()) != null; ) {
					processLine(line, FileHandler.removeExtension(f.getName()));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	private void processLine(String line, String fileName) {
		for(String m: methods) {
			if(line.contains(m)) {
				for(Pair<String,String> p: oPair) {
					if(fileName.equals(p.getClassName()) && getObjectName(line, m).equals(p.getObjectName())) {
						incrementValue(m);
					}
				}
			}
		}
		for(String v: variables) {
			if(line.contains(v)) {
				incrementValue(v);
			}
		}
	}

	private String getObjectName(String line, String methodName) {
		String objectName= null;
		String str[] = line.split(" ");
		for(String l: str) {
			if(l.contains(methodName)) {
				objectName = l.replace("."+methodName,"");
			}
		}
		return objectName.trim();
	}

	private void incrementValue(String m) {
		for(Triple<String, String, Integer> t :mTriples) {
			if(t.getMethodName().equals(m)) {
				t.setOcurrence(t.getOcurrence()+1);
			}
		}
	}

}