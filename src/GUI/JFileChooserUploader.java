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

import Upload.FileHandler;

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
		  //Recursively loops through directory
		  try {
			  Files.walk(Paths.get(chooser.getCurrentDirectory().getPath()))
		        .filter(Files::isRegularFile)
		        .forEach(pathList::add);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  System.out.println("-------Java Files filtered---------");
		  for ( Path p : pathList ) {
			    FileHandler.addNewFile(p);
		  }
		  for(File f: FileHandler.uploadedFiles) {
			  System.out.println(f.getName());
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
