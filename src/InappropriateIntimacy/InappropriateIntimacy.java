package InappropriateIntimacy;

import java.io.File;
import java.util.List;

import FileProcessing.FileHandler;

public class InappropriateIntimacy implements InterfaceII{
	
	private List<File> files = FileHandler.uploadedFiles;
	private List<Class<?>> classes = FileHandler.classes;

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

	@Override
	public void checkIfUsed(String line) {
		// TODO Auto-generated method stub
		
	}
	
}
