package Smells;

import java.util.List;

public class PrimitiveObsession {
	
	private List<String> classes;
	
	public PrimitiveObsession(List<String> classList) {
		this.classes = classList;
	}
	
	public int countPrimitiveTypes() {
		int numOfPts = 0;
		return numOfPts;
	}
	
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
