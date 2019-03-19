package Utilities;


public class ClassVariable<Class, Variable> {
    private Class className;
    private Variable variableName;

    public ClassVariable(Class className, Variable variableName){
        this.setClassName(className);
        this.setVariableName(variableName);
    }

    public Variable getVariableName() {
        return variableName;
    }

    public void setVariableName(Variable variableName) {
        this.variableName = variableName;
    }

    public Class getClassName() {
        return className;
    }

    public void setClassName(Class className) {
        this.className = className;
    }
}
