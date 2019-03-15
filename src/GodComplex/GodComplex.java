package GodComplex;

import javax.swing.*;

import BloatedClass.AccessSpecifiers;
import FileProcessing.FileHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GodComplex extends JButton implements ActionListener {
	
	private int numberOfMethods = 0; 
	private int numberOfLines = 0;
	
//basing on whether a class is a god class or not based on how many methods are in it and how many lines of code there are
	//an average class has at most 30 methods, anymore than 30 and it is a potential god class
	//if it has 50 then it is quite likely its a god class since its probably lost its single responsibility at that point
    public GodComplex(){
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	try {
        			report();
        		} catch (FileNotFoundException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		} catch (IOException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}
            }
            
            
        });
    }

    public void printWelcomeMessage() {
    	
    	System.out.println("----------GOD CLASS----------\n\n");
    	System.out.println("an average class dosent have more than 30 methods and has around 900 lines of code, anything above that is a potenial god class\n");
    	System.out.println("if the class has between 30 and 50 methods, it is a potential god class and should be checked out\n");
    	System.out.println("if the class has over 50 methods then it rasies a serious flag and it is quite likely that it is a god class");
    	System.out.println("\n\n\n");
    	
    }
    
    public void printResult() {
    	
    	if(numberOfMethods >= 30 && numberOfMethods < 50) {
    		System.out.println("there are " + numberOfMethods + " methods in this class, an average class has at most 30"
    				+ " methods in it, this class is a potential god class and should be looked at\n");
    	}
    	
    	if(numberOfMethods > 50) {
    		System.out.println(" ther are " + numberOfMethods + " methods in this class, an average class has at most 30 methods"
    			+ " in it, this class should be checked out as a potential god class\n");
    		 
    	}
    	
    	if(numberOfLines >= 900 && numberOfLines < 1500) {
    		System.out.println("there are " + numberOfLines + " lines in this class, an average class has around 900 or so lines"
    				 + " this class should be checked out as a potential god class\n");
    	}
    	
    	if(numberOfLines > 1500) {
    		System.out.println("there are " + numberOfLines + " lines in this class, an average class has around 900 or so lines"
    				+ " this class may be a god class and should be re-evaluated\n ");
    	}
    	
    	
    	System.out.println(numberOfMethods + " methods and " + numberOfLines + " lines \n");
    	
    }
     
    public void report() throws FileNotFoundException, IOException {
    	
    	printWelcomeMessage();
    	
    	for(File f: FileHandler.uploadedFiles) {
    		
    		numberOfMethods = 0;
    		numberOfLines = 0;
    		String fileName =FileHandler.removeExtension(f.getName());
    		System.out.println(fileName + "\n");
    		
    		
    		try(BufferedReader br = new BufferedReader(new FileReader(f))){
    		
    			for(String line; (line = br.readLine()) != null;) {
    				
    				countNumberOfLines();
    				//check to see if its a method decalaration or not
    				if(line.contains(AccessSpecifiers.PUBLIC) || line.contains(AccessSpecifiers.PRIVATE)
   						 || line.contains(AccessSpecifiers.PROTECTED) || line.contains("void")) {
    					
    					if(line.contains("(") && line.contains(")") && line.contains("{")) {
       					 
   						 countNumberOfMethods();
   						 
   						 
   					 	}
    					
    				}
    				
    			}
    			
    			printResult();
    			
    		
    		}
    	}
    }
    
   
    
    
    public void countNumberOfMethods() {
    	numberOfMethods++;
    
    }
    
    public void countNumberOfLines() {
    	numberOfLines++;
    	
    }


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	//all these methods were purely for testing purposes
	
/*	public void a () {

	}
	
public void a30 () {
		
	}

public void a29 () {
	
}

public void a28 () {
	
}

public void a27 () {
	
}

public void a26 () {
	
}

public void a25 () {
	
}

public void a24 () {
	
}

public void a23 () {
	
}

public void a22 () {
	
}


public void a21 () {
	
}


public void a20 () {
	
}

public void a19 () {
	
}


public void a18 () {
	
}


public void a17 () {
	
}

public void a16 () {
	
}


public void a15 () {
	
}


public void a14 () {
	
}


public void a13 () {
	
}


public void a12 () {
	
}


public void a111 () {
	
}


public void a10 () {
	
}


public void a9 () {
	
}



public void a8 () {
	
}


public void a7 () {
	
}



public void a6 () {
	
}


public void a5 () {
	
}



public void a4 () {
	
}



public void a3 () {
	
}



public void a2 () {
	
}



public void a11 () {
	
}*/



}

