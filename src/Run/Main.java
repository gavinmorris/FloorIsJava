package Run;
import GUI.JFileChooserUploader;
import GUI.JSmellChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    public static JFrame frame;
    public static JFileChooserUploader panel;
    public static JSmellChooser smells;

    public static void main(String[] args) {
        frame = new JFrame(" ");
        frame.setLayout(new BorderLayout());
        panel = new JFileChooserUploader();
        smells = new JSmellChooser();

        frame.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );
        frame.getLayeredPane().add(panel,"Center");
        frame.getLayeredPane().add(smells);
        frame.setSize(500, 400);
        panel.setBounds(0, 0, 500, 100);
        smells.setBounds(0, 100, 500, 400);
        frame.setVisible(true);
    }
}
