package General;

import java.util.ArrayList;

public class ClassObjectTuple<ClassName,ObjectName> {
	
    private ClassName className;
    private ObjectName objectName;
    private ArrayList<String> methods;
    
    public ClassObjectTuple(ClassName className, ObjectName objectName){
        this.setClassName(className);
        this.setObjectName(objectName);
        this.methods = new ArrayList<String>();
    }
    
    public void addMethodName(String methodName) {
    	methods.add(methodName);
    }

    
	public ClassName getClassName() {
		return className;
	}

	public void setClassName(ClassName className) {
		this.className = className;
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
	public ArrayList<String> getMethodsList(){
		return methods;
	}

	public boolean methodExists(String methodName) {
		if(methods.contains(methodName)) {
			return true;
		}
		return false;
	}
    
    
}
