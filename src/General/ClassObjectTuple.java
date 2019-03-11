package General;

import java.util.ArrayList;

public class ClassObjectTuple<FileName,ClassName,ObjectName> {
	
	private FileName fileName;
    private ClassName className;
    private ObjectName objectName;
    private ArrayList<String> methods;
    
    public ClassObjectTuple(FileName fileName, ClassName className, ObjectName objectName){
    	this.setFileName(fileName);
        this.setClassName(className);
        this.setObjectName(objectName);
        this.methods = new ArrayList<String>();
    }
    
    public void addMethodName(String methodName) {
    	methods.add(methodName);
    }

    public FileName getFileName() {
    	return fileName;
    }
    
	public ClassName getClassName() {
		return className;
	}

	public void setClassName(ClassName className) {
		this.className = className;
	}
	
	public void setFileName(FileName fileName) {
		this.fileName = fileName;
	}

	public ObjectName getObjectName() {
		return objectName;
	}

	public void setObjectName(ObjectName objectName) {
		this.objectName = objectName;
	}
	
	public String getMethods() {
		return methods.toString();
	}
    
    
}
