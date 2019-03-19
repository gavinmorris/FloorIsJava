package BloatedClass;

import javax.swing.*;

import General.Smells;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BloatedClass extends JButton implements ActionListener, Smells {

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
        int k=0;
        k++;
        k++;
        k++;
        k++;
    }

    int h;

	@Override
	public void report() {
		// TODO Auto-generated method stub
		
	}

}
