package Upload;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
	
	public static ArrayList<File> uploadedFiles = new ArrayList<File>();
	
	public static void addNewFile(Path path){
		if(isFileTypeJava(path.toString()))
			uploadedFiles.add(new File(path.toString()));
		
	}
	
	public static boolean isFileTypeJava(String path) {
		if(path.endsWith(".java"))
			return true;
		else {
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
