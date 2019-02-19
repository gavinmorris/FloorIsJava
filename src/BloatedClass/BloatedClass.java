package BloatedClass;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BloatedClass extends JButton implements ActionListener {

    public BloatedClass(){
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        //TODO
    }
}
