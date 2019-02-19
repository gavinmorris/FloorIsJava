package InappropriateIntimacy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import javax.swing.*;

public class InappropriateIntimacy extends JButton implements ActionListener {

	private List<String> methods = new ArrayList<String>();
	private List<String> variables = new ArrayList<String>();

	Map<String, Integer> methodUse = new Hashtable<String, Integer>();
	Map<String, Integer> variableUse = new Hashtable<String, Integer>();

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
		fillHashTable();
		checkIfUsed();
		System.out.println("-------Public Methods to be made private.---------");
		print();
	}

	private void print() {
		for (Map.Entry<String, Integer> entry : methodUse.entrySet()){
			if(entry.getValue() <= 1) {
				System.out.println(entry.getKey());
			}
		}
		for (Map.Entry<String, Integer> entry : variableUse.entrySet()){
			if(entry.getValue() <= 1) {
				System.out.println(entry.getKey());
			}
		}
	}

	private void getPublicMethods() {
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

	private void checkLine(String line) {
		if(line.contains(Literals.PUBLIC) && (line.contains(Literals.O_BRACKET) && line.contains(Literals.C_BRACKET)) && !(line.contains(Literals.GREATER_THAN)&&line.contains(Literals.LESS_THAN))) {
			String methodName = getMethodName(line);
			methods.add(methodName);
		}
		else if(line.contains(Literals.PUBLIC) && line.contains(Literals.EQUALS)) {
			String varName = getVariableName(line);
			variables.add(varName);
		}
	}

	private String getVariableName(String line) {
		String[] methodName = line.substring(0,line.indexOf(Literals.EQUALS)).split(" ");
		return methodName[methodName.length-1].trim();
	}

	private String getMethodName(String line) {
		String[] methodName = line.substring(0,line.indexOf(Literals.O_BRACKET)).split(" ");
		return methodName[methodName.length-1].trim();
	}

	private void fillHashTable() {
		for(String m: methods) {
			methodUse.put(m, 0);
		}
		for(String v: variables) {
			variableUse.put(v, 0);
		}
	}

	private boolean checkIfUsed() {
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
			}
		}
		for(String v: variables) {
			if(line.contains(v)) {
				int val = variableUse.containsKey(v) ? variableUse.get(v) : 0;
				variableUse.put(v, val+1);
			}
		}
	}


}