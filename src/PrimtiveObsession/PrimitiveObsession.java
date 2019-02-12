package PrimtiveObsession;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import FileProcessing.FileHandler;


public class PrimitiveObsession implements PO{
	
	private List<Class<?>> classes = FileHandler.classes;

	public void report() {
		int primCount = 0;
		int notPrimCount = 0;
		for(Class<?> c : classes) {
			for(Field f: c.getFields()) {
				if(f.getType().isPrimitive()) {
					primCount++;
				}else {
					notPrimCount++;
				}
			}
		}
		System.out.println("---------Primitve Obsession Report");
		System.out.println("Primtive Types in project: "+ primCount);
		System.out.println("Class Obejcts in project: "+notPrimCount);
	}
	
	
}
