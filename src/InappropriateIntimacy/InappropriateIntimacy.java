package InappropriateIntimacy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import FileProcessing.FileHandler;

public class InappropriateIntimacy implements InterfaceII{

	private List<String> classes = FileHandler.classes;
	private List<File> files = FileHandler.uploadedFiles;


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