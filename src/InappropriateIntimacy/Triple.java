package InappropriateIntimacy;

public class Triple<ClassName,MethodName,Occurrence> {
    private ClassName b;
    private MethodName l;
    private Occurrence o;
    
    public Triple(ClassName b, MethodName l, Occurrence o){
        this.b = b;
        this.l = l;
        this.o = o;
    }
    
    public ClassName getClassName(){
    	return b;
    }
    
    public MethodName getMethodName(){ 
    	return l;
    }
    
    public Occurrence getOcurrence(){
    	return o;
    }
    public void setClassName(ClassName b){ 
    	this.b = b;
    }
    
    public void setMethodName(MethodName l){
    	this.l = l;
    }
    
    public void setOcurrence(Occurrence o) {
    	this.o = o;
    }
}
