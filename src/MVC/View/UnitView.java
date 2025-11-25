package MVC.View;

import MVC.Model.EquationModel;

import javax.swing.*;

public class UnitView extends View {
    private JComboBox unitCombo;
    private JTextField imperialDisplay;
    private JTextField metricDisplay;
    private JComboBox imperialCombo;
    private JComboBox metricCombo;
    private JButton unitChangeButton;
    private JLabel equalText;

    @Override
    public void modelChanged(EquationModel m) {

    }
}
