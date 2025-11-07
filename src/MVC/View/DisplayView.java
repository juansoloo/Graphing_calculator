package MVC.View;

import MVC.Model.EquationModel;

import javax.swing.*;

public class DisplayView extends View {
    private JPanel root;
    private JTextField displayField;

    public JComponent getComponent() {
        return root;
    }

    @Override
    public void update(EquationModel m) {
        String text = (m.getResult() != null && !m.getResult().isEmpty())
                ? m.getResult()
                : (m.getExpression() == null ? "" : m.getExpression());
        displayField.setText(text);
    }

    @Override
    public void render() {
        // might need
    }
}
