package MVC.View;

import MVC.Model.EquationModel;

import javax.swing.*;

public class UnitVIew implements View {

    private JComboBox unitType;
    private JTextField textField1;
    private JTextField textField2;
    private JComboBox comboBox2;
    private JComboBox comboBox1;

    @Override
    public void modelChanged(EquationModel m) {
        //
    }
}
