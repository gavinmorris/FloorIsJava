package DataOnlyClass;

import javax.swing.*;

import General.Smells;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataOnlyClass extends JButton implements ActionListener, Smells {

    public DataOnlyClass(){
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

	@Override
	public void report() {
		// TODO Auto-generated method stub
		
	}
}
