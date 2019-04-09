package GodComplex;

import javax.swing.*;

import FileProcessing.FileHandler;
import Utilities.Literals;
import Utilities.Smells;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GodComplex extends JButton implements ActionListener , Smells{
	
	private int numberOfMethods = 0; 
	private int numberOfLines = 0;
	private String output = "-------God Complex-------<br>";
	private String fileName = "<br>";
	
//basing on whether a class is a god class or not based on how many methods are in it and how many lines of code there are
	//an average class has at most 30 methods, anymore than 30 and it is a potential god class
	//if it has 50 then it is quite likely its a god class since its probably lost its single responsibility at that point

    public GodComplex(){
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	report();
            }
            
            
        });
    }

//    public void printWelcomeMessage() {
//    	
//    	System.out.println("----------GOD CLASS----------\n\n");
//    	System.out.println("an average class dosent have more than 30 methods and has around 900 lines of code, anything above that is a potenial god class\n");
//    	System.out.println("if the class has between 30 and 50 methods, it is a potential god class and should be checked out\n");
//    	System.out.println("if the class has over 50 methods then it rasies a serious flag and it is quite likely that it is a god class");
//    	System.out.println("\n\n\n");
//    	
//    }
    
    public void printResult() {
    	
    	if(numberOfMethods >= 30 && numberOfMethods < 50) {
    		output += "there are " + numberOfMethods + " methods in this class, an average class has at most 30"
    				+ " methods in it, this class is a potential god class and should be looked at</br >";
    	}
    	
    	if(numberOfMethods > 50) {
    		output += " there are " + numberOfMethods + " methods in this class, an average class has at most 30 methods"
    			+ " in it, this class should be checked out as a potential god class</br >";
    		 
    	}
    	
    	if(numberOfLines >= 900 && numberOfLines < 1500) {
    		output += "there are " + numberOfLines + " lines in this class, an average class has around 900 or so lines"
    				 + " this class should be checked out as a potential god class </br >";
    	}
    	
    	if(numberOfLines > 1500) {
    		output += "there are " + numberOfLines + " lines in this class, an average class has around 900 or so lines"
    				+ " this class may be a god class and should be re-evaluated </br >";
    	}
    	
    	
    	output += numberOfMethods + " methods  and " + numberOfLines + " lines </br >";
    	System.out.println(output);
    	
    }
     
    public void report() {
    	
    	//printWelcomeMessage();
    	//output = "";
    	for(File f: FileHandler.uploadedFiles) {
    		
    		output = "<br>";
    		fileName = "<br>";
    		numberOfMethods = 0;
    		numberOfLines = 0;
    		fileName += FileHandler.removeExtension(f.getName()) + "</br>";
    		System.out.println(fileName);
    		
    		//System.out.println(fileName + "\n");
    		
    		
    		try(BufferedReader br = new BufferedReader(new FileReader(f))){
    		
    			for(String line; (line = br.readLine()) != null;) {
    				
    				countNumberOfLines();
    				//check to see if its a method decalaration or not
    				if(line.contains(Literals.PUBLIC) || line.contains(Literals.PRIVATE)
   						 || line.contains(Literals.PROTECTED) || line.contains("void")) {
    					
    					if(line.contains("(") && line.contains(")") && line.contains("{")) {
       					 
   						 countNumberOfMethods();
   						 
   						 
   					 	}
    					
    				}
    				
    			}
    			
    			printResult();
    			
    		
    		} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	
}

