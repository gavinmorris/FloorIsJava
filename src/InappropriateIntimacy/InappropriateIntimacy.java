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

	private List<Class<?>> classes = FileHandler.classes;
	private List<File> files = FileHandler.uploadedFiles;


	@Override
	public void report() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getPublicMethods() {
		ArrayList<String> iAM = new ArrayList<String>();
		for(Class<?> c : classes) {
			for(Method m: c.getDeclaredMethods()) {
				if(Modifier.isPublic(m.getModifiers())){
					if(!isMethodUsed(c.getSimpleName(), m.getName())) {
						iAM.add(m.getName());
					}
				}
			}
		}
		System.out.println("-----------Inappropriate Intimacy Report----------");
		System.out.println("The following methods should be private:");
		for(String m : iAM) {
			System.out.println(m);
		}
	}

	@Override
	public void getPublicVariables() {
		// TODO Auto-generated method stub
	}


	public boolean isMethodUsed(String className, String methodName) {
		boolean possible = false;
		boolean used = false;
		for(File f: files) {
			if(!f.getName().contains(className)) {
				try(BufferedReader br = new BufferedReader(new FileReader(f))) {
					for(String line; (line = br.readLine()) != null; ) {
						if(line.contains(className)) {
							possible= true;
						}
						if(possible) {
							if(line.contains(methodName)) {
								used = true;
							}
						}
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return used;
	}


}