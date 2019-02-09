package PrimtiveObsession;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;


public class PrimitiveObsession implements PO{
	
	private List<File> files;
	private List<String> classes;
	
	public PrimitiveObsession(List<File> fileList,  List<String> classList) {
		this.files = fileList;
		this.classes = classList;
	}
	public int countPrimitiveTypes() throws FileNotFoundException, IOException {
		int numOfPts = 0;
		for(File f: files) {
			try(BufferedReader br = new BufferedReader(new FileReader(f))) {
			    for(String line; (line = br.readLine()) != null; ) {
			        if(line.contains(PrimitiveDataTypes.INT))
			        	numOfPts++;
			        else if(line.contains(PrimitiveDataTypes.DOUBLE))
			        	numOfPts++;
			        else if(line.contains(PrimitiveDataTypes.FLOAT))
			        	numOfPts++;
			        else if(line.contains(PrimitiveDataTypes.CHAR))
			        	numOfPts++;
			        else if(line.contains(PrimitiveDataTypes.STRING))
			        	numOfPts++;
			        else if(line.contains(PrimitiveDataTypes.BOOL))
			        	numOfPts++;
			    }
			}
		}
		return numOfPts;
	}
	
	
	public int countClassObjects() throws FileNotFoundException, IOException {
		int numOfClassObj=0;
		for(File f: files) {
			try(BufferedReader br = new BufferedReader(new FileReader(f))) {
			    for(String line; (line = br.readLine()) != null; ) {
			        for(String c: classes) {
			        	if((line.contains(c) && line.contains("new")) || line.contains(c+".")) {
			        		numOfClassObj++;
			        		break;
			        	}
			        }
			    }
			}
		}
		return numOfClassObj;
	}
	
	public void report() throws FileNotFoundException, IOException {
		System.out.println("---------Primitve Obsession Report");
		System.out.println("Primtive Types in project: "+ countPrimitiveTypes());
		System.out.println("Class Obejcts in project: "+countClassObjects());
	}


	
	
}
