package InappropriateIntimacy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import FileProcessing.FileHandler;

import javax.swing.*;

public class InappropriateIntimacy extends JButton implements InterfaceII, ActionListener {

	private List<String> classes = FileHandler.classes;
	private List<File> files = FileHandler.uploadedFiles;

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

	@Override
	public void report() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getPublicMethods() {

	}

	@Override
	public void getPublicVariables() {
		// TODO Auto-generated method stub
	}


	public boolean isMethodUsed(String className, String methodName) {
		return false;
	}


}