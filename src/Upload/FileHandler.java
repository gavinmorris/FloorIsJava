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
	public static List<String> getClasses(List<File> files) {
		List<String> classes = new ArrayList<String>();
		for(File file: files) {
			classes.add(file.getName().replace(".java", "").trim());
		}
		return classes;
	}
	
}
