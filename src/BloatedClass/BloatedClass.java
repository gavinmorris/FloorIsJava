package BloatedClass;

import javax.swing.*;

import FileProcessing.FileHandler;
import Utilities.ClassObjectTuple;
import Utilities.Literals;
import Utilities.Smells;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BloatedClass extends JButton implements ActionListener, Smells {

	private int linesInMethod = 0;
	private int numberOfMethods = 0;
	private String s;
	
    public BloatedClass(){
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
				report();

            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        //TODO
        int k=0;
        k++;
        k++;
        k++;
        k++;
    }
    
   
 
    
    public void report() {
    	System.out.println("--------Bloated Code--------\n\n");
    	for(File f : FileHandler.uploadedFiles) {
    		
    		
    		//int methodLineCount = 0;
			//int numberOfMethods = 0;
			numberOfMethods = 0;
			linesInMethod = 0;
    		
    		try(BufferedReader br = new BufferedReader(new FileReader(f))){
    			
    			 String FileName = f.getName();
    			 System.out.println(FileName + "\n");
    		
    			 for(String line; (line = br.readLine()) != null;) {
    				 	
    				 //checks if its the method declaration
    				 if(line.contains(Literals.PUBLIC) || line.contains(Literals.PRIVATE)
    						 || line.contains(Literals.PROTECTED) || line.contains("void")) {
    					 
    					 	if(line.contains("(") && line.contains(")") && line.contains("{")) {
    					 
    						 numberOfMethods();
    						 countLinesInMethod();
    						 
    					 	}
    						 	 
    					 }
						 if(linesInMethod != 0) {
							 if(line.contains(Literals.PUBLIC)) {
								 continue;
							 }else {
								 countLinesInMethod();
							 }
						 }
    				 
    				// System.out.println("there are " + linesInMethod + " lines");
    				// System.out.println("there are " + linesInMethod + " lines");
    				 }
    				 
    				
    			
    			 } catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		
    	}
    			 
    			

    		
    	
    	
    }
    
    
    	
    	
    
    
    public void countLinesInMethod() {
    	 linesInMethod++;
    	// System.out.println("there are " + linesInMethod + " lines in this method");
    }
    
    public void numberOfMethods() {
    	
		numberOfMethods++;
		System.out.println("there are " + numberOfMethods + " methods in this class \n");
    }
    
   
   

    //int h;
    //can go through each file and go through each line or can get all the methods of each file 
    //and then go through each line in each method
    //gonna take 50 lines as a benchmark and say that after 50 lines the method is too long
    //look for loop unrolling, long if statements/ switch statements
    //

}
