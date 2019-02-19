package DuplicatedCode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DuplicatedCode extends JButton implements ActionListener {

    public DuplicatedCode(){
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                report();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        //TODO
    }

    public void report(){
        System.out.println("\n\n ----- Duplicated Code ----- ");


    }
}
