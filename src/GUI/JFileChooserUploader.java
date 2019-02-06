package GUI;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.*;
import java.util.*;
import javax.swing.JPanel;

import FileProcessing.FileHandler;
import Smells.PrimitiveObsession;

public class JFileChooserUploader extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	JButton go;
   
	JFileChooser chooser;
	String choosertitle;
   
	public JFileChooserUploader() {
	    go = new JButton("Upload Project");
	    go.addActionListener(this);
	    add(go);
	}
	
	public void actionPerformed(ActionEvent e) {
        
	    chooser = new JFileChooser(); 
	    chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle(choosertitle);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		//
		// disable the "All files" option.
		//
		chooser.setAcceptAllFileFilterUsed(false);
		//    
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
		  System.out.println("getCurrentDirectory(): " 
		     +  chooser.getCurrentDirectory());
		  System.out.println("getSelectedFile() : " 
		     +  chooser.getSelectedFile());
		  ArrayList<Path> pathList = new ArrayList<Path>();
		  //loops through directory and gets paths of java files
		  try {
			  Files.walk(Paths.get(chooser.getSelectedFile().getPath()))
		        .filter(Files::isRegularFile)
		        .forEach(pathList::add);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		  System.out.println("-------Java Files filtered---------");
		  //process the files and add to file list
		  for ( Path p : pathList ) {
			    FileHandler.addNewFile(p);
		  }
		  //print the added files
		  for(File f: FileHandler.uploadedFiles) {
			  System.out.println(f.getName());
		  }
		  //Look into files and load them for Primitive Obsession smell test
		  PrimitiveObsession po = new PrimitiveObsession(FileHandler.uploadedFiles);
		    try {
		    	//count the primitive types in the whole project
				System.out.println("Number of Primitve Types of code:"+po.countPrimitiveTypes());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		  //look through the files and grab the names of the classes
		  ArrayList<String> classList = (ArrayList<String>) FileHandler.getClasses(FileHandler.uploadedFiles);
		  System.out.println("-------Classes--------");
		  for(String f: classList) {
			  System.out.println(f);
		  }
		}
		else {
		  System.out.println("No Selection ");
		}
	}

	public Dimension getPreferredSize(){
		return new Dimension(200, 200);
	}
    
	public static void main(String s[]) {
	    JFrame frame = new JFrame("");
		JFileChooserUploader panel = new JFileChooserUploader();
		frame.addWindowListener(
		  new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		      System.exit(0);
		      }
		    }
		  );
		frame.getContentPane().add(panel,"Center");
	    frame.setSize(panel.getPreferredSize());
	    frame.setVisible(true);
	}
}
