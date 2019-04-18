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
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import Utilities.ClassMethod;
import Utilities.ClassObjectTuple;
import Utilities.FileParser;
import Utilities.Literals;

public class BloatedClass extends JButton implements ActionListener, Smells {

	
	private int numberOfMethods = 0;
	private int linesInFile = 0;
	private int methodLineStart = 0;
	private int methodLineFinish = 0;
	public static String output =  "-------Methods that are too long.---------\n";
	
	private String method = "";
	private List<ClassMethod<String, String>> unused = new ArrayList<ClassMethod<String, String>>();

	
	
	
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
       
    }
    
   public void report() {
	   lookForBloatedMethods();
	   printReport();
	   
   }
 
    
    public void lookForBloatedMethods()  {
    	
    	for(File f : FileHandler.uploadedFiles) {
    		
			numberOfMethods = 0;
			linesInFile = 0;
			methodLineStart = 0;
			methodLineFinish = 0;
			
			   		
    		try(BufferedReader br = new BufferedReader(new FileReader(f))){
    			
    			
    			 Stack<Integer> closingBrackets = new Stack<Integer>();
    			 Stack<Integer> openingBrackets = new Stack<Integer>();
    		
    			 for(String line; (line = br.readLine()) != null;) {
    				 linesInFile++;
    				
    				 if(!FileParser.isComment(line)) {
    				 
    				 //checks if its the method declaration

    					 if(checkAccessSpecifier(line)) {
    					 
    					 	if(line.contains(Literals.O_BRACKET) && line.contains(Literals.C_BRACKET) && line.contains(Literals.O_Curly)) {
    					 
    					 	 method = FileParser.getMethodDef(line);
    						 numberOfMethods();
    						 methodLineStart = linesInFile;
 
    					 	}
    						 	 
    					 }
						
    				 
    				 if(numberOfMethods >= 1) {
 	 
    				 if(checkLineForOpeningCurlyBracket(line)) {
						 openingBrackets.push(1);
						//System.out.println("opening : " + method + " : " + linesInFile + " : " + openingBrackets.size() + "\n");
						
					 }
					 					 
    				  if(checkLineForClosingCurlyBracket(line)) {
						 closingBrackets.push(1);
						// System.out.println("Closing : " + method + " : " + linesInFile + " : " + closingBrackets.size() + "\n");
						 
					 }
    				 
    			}
    				 
				 if(!(openingBrackets.empty()) && !(closingBrackets.empty())) {
					 if(openingBrackets.size() == closingBrackets.size()) {
						 methodLineFinish = linesInFile;
						if(checkSizeOfMethod(methodLineFinish, methodLineStart)) {
							unused.add(new ClassMethod<String, String>(FileHandler.removeExtension(f.getName()), method));
						}
						 
						 while(!openingBrackets.empty()) {
							try {
							 openingBrackets.pop();
							}catch (EmptyStackException e) {
							}
							
						 }
						 
						 while(!closingBrackets.empty()) {
							 try {
								 closingBrackets.pop();
								}catch (EmptyStackException e) {
								}
							 

						 }
						 
					 }
    				     	
    			 }
				 
    		}//iscomment
				 				 
    	 } //end for loop
    				     				    			
    }//end buffer 
    		
    		
    		
 
    		catch (IOException e) { //18 opening
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    		

    	}//end file
    	
    	//printReport();
		
		//unused.clear();
    			 
    }//end method
    
    
  
    
    
    public boolean checkLineForOpeningCurlyBracket(String line) {
    	if (line.contains(Literals.O_Curly)) {
    		return true;
    	}
    	else return false;
    }
    
    public boolean checkLineForClosingCurlyBracket(String line) {
    	if (line.contains(Literals.C_Curly)) {
    		return true;
    	}
    	else return false;
    }
    
    public boolean checkAccessSpecifier(String line) {
    	if(line.contains(Literals.PUBLIC) || line.contains(Literals.PRIVATE) || line.contains(Literals.PROTECTED)) {
    		return true;
    	}
    	else return false;
    }
       	
    public boolean checkSizeOfMethod(int finish, int start) {
    	int sizeOfMethod = 0;
    	sizeOfMethod = finish - start;
    	if(sizeOfMethod >= 50) {
    		return true;
    		
    	}
    	
    	return false;
    }
    
    public void printReport() {
    	
    	for(ClassMethod<String, String> cm: unused) {
    		
    		output +=  cm.getClassName()+" : "+cm.getMethodName()+ "\n";
    		System.out.println(output);
    		
		}
    }
    

    
    public void numberOfMethods() {
		numberOfMethods++;
    }
    
}
