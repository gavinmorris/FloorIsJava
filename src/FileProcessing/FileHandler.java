package FileProcessing;


import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public static ArrayList<File> uploadedFiles = new ArrayList<File>();
    public static ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
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
    public static ArrayList<Class<?>> getClasses(){
        List<String> classesName = new ArrayList<String>();
        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        for(File file: uploadedFiles) {
            String tmp = file.getPath();
            tmp = tmp.substring(tmp.indexOf("src")+4);
            String className = tmp.replaceAll("/", "\\\\");
            className = tmp.replaceAll("\\\\", ".");
            classesName.add(className.replace(".java", "").trim());
        }
        System.out.println(classesName.toString());
        for(String className: classesName) {
            Class<?> cls;
            try {
                cls = Class.forName(className);
                classes.add(cls);
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return classes;
    }

}