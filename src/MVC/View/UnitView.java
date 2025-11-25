package MVC.View;

import MVC.Model.EquationModel;

import javax.swing.*;

public class UnitView extends View {
    private JPanel rootUnit;

    private JComboBox unitCombo;
    private JTextField imperialDisplay;
    private JTextField metricDisplay;
    private JComboBox imperialCombo;
    private JComboBox metricCombo;
    private JButton unitChangeButton;
    private JLabel equalText;

    public UnitView() {

    }

    public JPanel getRootUnit() {
        return rootUnit;
    }

    @Override
    public void modelChanged(EquationModel m) {}
}
