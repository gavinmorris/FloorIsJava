package PrimtiveObsession;

import java.io.FileNotFoundException;
import java.io.IOException;
/*
 * Interface for Primitive Obsessions
 */
public interface PO {
	public int countPrimitiveTypes()throws FileNotFoundException, IOException;
	public int countClassObjects()throws FileNotFoundException, IOException;
	public void report()throws FileNotFoundException, IOException;
}
