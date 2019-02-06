package Smells;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class PrimitiveObsession {
	
	private List<File> files;
	
	public PrimitiveObsession(List<File> fileList) {
		this.files = fileList;
	}
	public int countPrimitiveTypes() throws FileNotFoundException, IOException {
		int numOfPts = 0;
		for(File f: files) {
			try(BufferedReader br = new BufferedReader(new FileReader(f))) {
			    for(String line; (line = br.readLine()) != null; ) {
			        numOfPts += countInt(line);
			        numOfPts += countDouble(line);
			        numOfPts += countFloat(line);
			        numOfPts += countString(line);
			        numOfPts += countBool(line);
			        numOfPts += countChar(line);
			    }
			}
		}
		return numOfPts;
	}
	
	//helper methods
	private int countInt(String line) {
		int countInt= 0;
		if (line.toLowerCase().indexOf(PrimitiveDataTypes.INT.toLowerCase()) != -1 )
			   countInt++;
		return countInt;
	}	
	private int countDouble(String line){
		int countDouble = 0;
		if (line.toLowerCase().indexOf(PrimitiveDataTypes.DOUBLE.toLowerCase()) != -1 )
			countDouble++;
		return countDouble;
	}
	private int countFloat(String line){
		int countFloat = 0;
		if (line.toLowerCase().indexOf(PrimitiveDataTypes.FLOAT.toLowerCase()) != -1 )
			countFloat++;
		return countFloat;
	}
	private int countString(String line){
		int countString = 0;
		if (line.toLowerCase().indexOf(PrimitiveDataTypes.STRING.toLowerCase()) != -1 )
			countString++;
		return countString;
	}
	private int countBool(String line){
		int countBool = 0;
		if (line.toLowerCase().indexOf(PrimitiveDataTypes.BOOL.toLowerCase()) != -1 )
			countBool++;
		return countBool;
	}
	private int countChar(String line){
		int countChar = 0;
		if (line.toLowerCase().indexOf(PrimitiveDataTypes.CHAR.toLowerCase()) != -1 )
			countChar++;
		return countChar;
	}
}
