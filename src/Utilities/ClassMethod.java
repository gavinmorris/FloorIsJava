package Utilities;


public class ClassMethod<Class, Method> {
	private Class className;
    private Method methodName;
    
    public ClassMethod(Class className, Method methodName){
    	this.setClassName(className);
    	this.setMethodName(methodName);
    }

	public Method getMethodName() {
		return methodName;
	}

	public void setMethodName(Method methodName) {
		this.methodName = methodName;
	}

	public Class getClassName() {
		return className;
	}

	public void setClassName(Class className) {
		this.className = className;
	}
}
