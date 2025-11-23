package MVC.View;

import MVC.Model.EquationModel;
import MVC.Utils.ModelListener;

import javax.swing.*;

public class DisplayView extends View implements ModelListener {
    private JPanel root;
    private JTextField displayField;

    public JComponent getComponent() {
        return root;
    }

    @Override
    public void modelChanged(EquationModel m) {
        displayField.setText(m.getDisplayText());
    }
}
