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
	private String output = "-------God Complex-------\n";
	private String fileName = "";
	
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


    
    public void printResult() {
    	
  
    	if(numberOfMethods > 30 || numberOfLines > 900) {
    		output += numberOfMethods + " methods  and " + numberOfLines + " lines \n";
    		output += "there are too many methods in this class, its godly in a bad way \n";
    		
    	}
    	
    	else {
    	output += numberOfMethods + " methods  and " + numberOfLines + " lines \n";
    	
    	}
    	
    }
     
    public void report() {
    	
    	
    	for(File f: FileHandler.uploadedFiles) {
    		
    		output = "\n";
    		fileName = "\n";
    		numberOfMethods = 0;
    		numberOfLines = 0;
    		fileName += FileHandler.removeExtension(f.getName()) + "\n";
    		System.out.println(fileName);
    		
    		
    		
    		
    		try(BufferedReader br = new BufferedReader(new FileReader(f))){
    		
    			for(String line; (line = br.readLine()) != null;) {
    				
    				countNumberOfLines();
    				//check to see if its a method decalaration or not
    				if(line.contains(Literals.PUBLIC) || line.contains(Literals.PRIVATE)
   						 || line.contains(Literals.PROTECTED) || line.contains("void")) {
    					
    					if(line.contains(Literals.O_BRACKET) && line.contains(Literals.C_BRACKET) && line.contains(Literals.O_Curly)) {
       					 
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

