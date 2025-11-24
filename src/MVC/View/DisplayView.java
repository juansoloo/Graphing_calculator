package MVC.View;

import MVC.Model.EquationModel;
import MVC.Observer.ModelListener;

import javax.swing.*;

/**
 * DisplayView is the view that shows the user's input and results.
 * Whenever the user presses a button (digit, operator, variable),
 * the EquationModel updates, and this view updates.
 * It listens to the EquationModel and updates the text field whenever
 * the model's input, result, or error message changes.
 */
public class DisplayView extends View implements ModelListener {
    private JPanel root;
    private JTextField displayField;

    /**
     * Returns the root swing component used by the view so the
     * controller can insert it.
     * @return root
     */
    public JComponent getComponent() {
        return root;
    }

    /**
     * Whenever the model changes state, the DisplayView updates the
     * displayField with the model's display string. This can be the
     * current input, result, or error.
     * @param m model
     */
    @Override
    public void modelChanged(EquationModel m) {
        displayField.setText(m.getDisplayText());
    }
}
