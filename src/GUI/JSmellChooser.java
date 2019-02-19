package GUI;

import BloatedClass.BloatedClass;
import DataOnlyClass.DataOnlyClass;
import DeadCode.DeadCode;
import DuplicatedCode.DuplicatedCode;
import GodComplex.GodComplex;
import InappropriateIntimacy.InappropriateIntimacy;
import LazyClass.LazyClass;
import PrimtiveObsession.PrimitiveObsession;
import TooManyLiterals.TooManyLiterals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class JSmellChooser extends JPanel {

    private InappropriateIntimacy inappropriateIntimacyButton;
    private PrimitiveObsession primitiveObsessionButton;
    private BloatedClass bloatedClassButton;
    private GodComplex godComplexButton;
    private TooManyLiterals tooManyLiteralsButton;
    private LazyClass lazyClassButton;
    private DataOnlyClass dataOnlyClassButton;
    private DuplicatedCode duplicatedCodeButton;
    private DeadCode deadCodeButton;

    public JSmellChooser() {
        this.setSize(500, 300);

        inappropriateIntimacyButton = new InappropriateIntimacy();
        inappropriateIntimacyButton.setText("Inappropriate Intimacy");
        this.add(inappropriateIntimacyButton);

        primitiveObsessionButton = new PrimitiveObsession();
        primitiveObsessionButton.setText("Primitive Obsession");
        this.add(primitiveObsessionButton);

        bloatedClassButton = new BloatedClass();
        bloatedClassButton.setText("Bloated Class");
        this.add(bloatedClassButton);

        godComplexButton = new GodComplex();
        godComplexButton.setText("God Complex");
        this.add(godComplexButton);

        tooManyLiteralsButton = new TooManyLiterals();
        tooManyLiteralsButton.setText("Too Many Literals");
        this.add(tooManyLiteralsButton);

        lazyClassButton = new LazyClass();
        lazyClassButton.setText("Lazy Class");
        this.add(lazyClassButton);

        dataOnlyClassButton = new DataOnlyClass();
        dataOnlyClassButton.setText("Data Only Class");
        this.add(dataOnlyClassButton);

        duplicatedCodeButton = new DuplicatedCode();
        duplicatedCodeButton.setText("Duplicated Code");
        this.add(duplicatedCodeButton);

        deadCodeButton = new DeadCode();
        deadCodeButton.setText("Dead Code");
        this.add(deadCodeButton);
    }
}
