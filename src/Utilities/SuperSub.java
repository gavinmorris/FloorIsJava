package Utilities;

public class SuperSub<SuperClass, SubClass> {
	private SuperClass superClassName;
    private SubClass subClassName;
    
    public SuperSub(SuperClass superClassName, SubClass subClassName){
    	this.setSuperClassName(superClassName);
    	this.setSubClassName(subClassName);
    }

	public SuperClass getSuperClassName() {
		return superClassName;
	}

	public void setSuperClassName(SuperClass superClassName) {
		this.superClassName = superClassName;
	}

	public SubClass getSubClassName() {
		return subClassName;
	}

	public void setSubClassName(SubClass subClassName) {
		this.subClassName = subClassName;
	}
    
    
}
