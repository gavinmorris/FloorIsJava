package DeadCode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeadCode extends JButton implements ActionListener {

    public DeadCode(){
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("working");
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        //TODO
    }
}
