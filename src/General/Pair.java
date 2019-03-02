package General;

public class Pair<ClassName, ObjectName> {
	private ClassName className;
	private ObjectName objectName;
	
	public Pair(ClassName cn, ObjectName on) {
		this.className = cn;
		this.objectName =on;
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
}
