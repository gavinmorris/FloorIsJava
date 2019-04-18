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
	


    public GodComplex(){
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	report();
            }
            
            
        });
    }


    
    public void printResult(String fileName) {
    	
  
    	if(numberOfMethods > 30 || numberOfLines > 900) {
    		output += "-------------" + fileName + "-------------\n";
    		output += numberOfMethods + " methods  and " + numberOfLines + " lines \n";
    		output += "there are too many methods in this class, its godly in a bad way \n";
    		
    	}
    	
    	else {
    	output += "-------------" + fileName + "-------------\n";
    	output += numberOfMethods + " methods and " + numberOfLines + " lines \n";
    	output += "not enough methods to classify that it is a god class.\n";
    	
    	
    	}
    	
    }
     
    public void report() {
    	
    	
    	for(File f: FileHandler.uploadedFiles) {
    		
    		output = "\n";
    		fileName = "\n";
    		numberOfMethods = 0;
    		numberOfLines = 0;
    		fileName += FileHandler.removeExtension(f.getName()) + "\n";
    		
    		
    		
    		
    		
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
    			
    			
    			printResult(fileName);
    		
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

