package LazyClass;

import javax.swing.*;

import Utilities.Smells;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LazyClass extends JButton implements ActionListener, Smells {

    public LazyClass(){
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
