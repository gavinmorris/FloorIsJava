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
	//private int tooLong = 0;
	private int methodLineStart = 0;
	private int methodLineFinish = 0;
	private String output =  "-------Methods that are too long.---------<br >";
	private String method = "";
	private List<ClassMethod<String, String>> unused = new ArrayList<ClassMethod<String, String>>();
	//private String fileName;
	
	
	
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
    
   
 
    
    public void report()  {
    	//printWelcomeMessage();
    	for(File f : FileHandler.uploadedFiles) {
    		
			numberOfMethods = 0;
			//tooLong = 0;
			linesInFile = 0;
			methodLineStart = 0;
			methodLineFinish = 0;
			//fileName = f.getName();
			   		
    		try(BufferedReader br = new BufferedReader(new FileReader(f))){
    			
    			// System.out.println(fileName + "\n");
    			 Stack<Integer> closingBrackets = new Stack<Integer>();
    			 Stack<Integer> openingBrackets = new Stack<Integer>();
    		
    			 for(String line; (line = br.readLine()) != null;) {
    				 linesInFile++;
    				
    				 if(!FileParser.isComment(line)) {
    				 
    				 //checks if its the method declaration
//    				 if(line.contains(Literals.PUBLIC) || line.contains(Literals.PRIVATE)
//    						 || line.contains(Literals.PROTECTED) || line.contains("void")) {
    					 if(checkModifier(line)) {
    					 
    					 	if(line.contains("(") && line.contains(")") && line.contains("{")) {
    					 
    					 	 method = FileParser.getMethodDef(line);
    					 	 //System.out.println(method + "wow\n");
    						 numberOfMethods();
    						 methodLineStart = linesInFile;
 
    					 	}
    						 	 
    					 }
//						 if(linesInMethod != 0) {
//							 if(line.contains(Literals.PUBLIC)) {
//								 continue;
//							 }else {
//								 countLinesInMethod();
//							 }
//						 }
    				 
    				 if(numberOfMethods >= 1) {
 	 
    				 if(checkLineForOpeningCurlyBracket(line)) {
						 openingBrackets.push(1);
						
					 }
					 					 
    				  if(checkLineForClosingCurlyBracket(line)) {
						 closingBrackets.push(1);
						 
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
    		
    		printReport();
    		
    		unused.clear();

    	}//end file
    	
    			 
    }//end method
  
    
    
    public boolean checkLineForOpeningCurlyBracket(String line) {
    	if (line.contains("{")) {
    		return true;
    	}
    	else return false;
    }
    
    public boolean checkLineForClosingCurlyBracket(String line) {
    	if (line.contains("}")) {
    		return true;
    	}
    	else return false;
    }
    
    public boolean checkModifier(String line) {
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
    	//output += "Number of Methods over 50 lines : " + tooLong + "</br >";
    	
    	for(ClassMethod<String, String> cm: unused) {
    		
    		output += cm.getClassName()+" : "+cm.getMethodName()+ "</br >";
		}
    }
    
//    public void printWelcomeMessage() {
//    	System.out.println("--------Bloated Code--------\n\n");
//    	System.out.println("The average method length is around 30 lines, so if your method is over 30 lines then its getting too long.\n"
//    			+ "If your method is 50 lines or over than it is likely that there is some bloat somewhere in that method and it should be \n"
//    			+ "checked out to see if there is any redundant logic or unnecessary varibles \n");
//    }
//    

    
    public void numberOfMethods() {
		numberOfMethods++;
    }
    
}
