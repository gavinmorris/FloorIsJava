

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Test {
	
	public static ArrayList<File> uploadedFiles = new ArrayList<File>();
	
	public void addNewFile(File newFile){
		if(isFileTypeJava(newFile))
			uploadedFiles.add(newFile);
		else {
			System.out.println("File was not added.");
		}
	}
	
	public boolean isFileTypeJava(File newFile) {
		String fileName = newFile.getName();
		if(fileName.substring(fileName.length() - 4).equals("java"))
			return true;
		else {
			System.out.println("Not a valid file type. Must be a .java file");
			return false;
		}
	}
	
	public void filesToString() {
		for(File thisFile: uploadedFiles) {
			try {
				List<String> lines = Files.readAllLines(Paths.get(thisFile.getAbsolutePath()), StandardCharsets.UTF_8);
				for(String line: lines) {
					System.out.println(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
