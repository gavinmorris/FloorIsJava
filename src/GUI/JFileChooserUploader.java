package GUI;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.*;
import java.util.*;
import java.util.List;

import FileProcessing.FileHandler;
import InappropriateIntimacy.InappropriateIntimacy;
import PrimtiveObsession.PrimitiveObsession;
import Utilities.ClassObjectTuple;
import Utilities.Literals;
import Utilities.SuperSub;

public class JFileChooserUploader extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	JButton go;

	JFileChooser chooser;
	String choosertitle;

	public JFileChooserUploader() {
		this.setSize(500, 100);
		go = new JButton("Upload Project");
		go.addActionListener(this);
		this.add(go);
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
			//process the files and add to file list
			for ( Path p : pathList ) {
				FileHandler.addNewFile(p);
			}

			FileHandler.classes = FileHandler.getClasses();
			
			//get subclasses
			for(File f : FileHandler.uploadedFiles) {
				try(BufferedReader br = new BufferedReader(new FileReader(f))) {
	    		    for(String line; (line = br.readLine()) != null; ) {
	    		        if(line.contains("extends") && line.contains("class")) {
	    		        	String superClass = FileHandler.getSuperclass(line);
	    		        	if(FileHandler.classes.contains(superClass)) {
	    		        		FileHandler.supersub.add(new SuperSub<String, String>(superClass, FileHandler.removeExtension(f.getName())));
	    		        	}
	    		        }
	    		    }
	    		} catch (IOException e1) {
					e1.printStackTrace();
				}
	    	}

//			InappropriateIntimacy obj = new InappropriateIntimacy();
//			obj.getPublicMethods();
//			//Look into files and load them for Primitive Obsession smell test
//			PrimitiveObsession po = new PrimitiveObsession();
//			po.report();
//
//			InappropriateIntimacy obj = new InappropriateIntimacy();
//			obj.report();
//			//Look into files and load them for Primitive Obsession smell test
//			PrimitiveObsession po = new PrimitiveObsession();
//			po.report();
		}
		else {
			System.out.println("No Selection ");
		}
	}

	public Dimension getPreferredSize(){
		return new Dimension(500, 100);
	}


}