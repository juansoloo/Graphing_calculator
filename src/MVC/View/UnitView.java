package MVC.View;

import MVC.Controller.CalculatorController;
import MVC.Model.EquationModel;
import MVC.Observer.ModelListener;

import javax.swing.*;

public class UnitView extends View implements ModelListener {
    private JPanel rootUnit;

    private JTextField imperialDisplay;
    private JTextField metricDisplay;
    private JComboBox<String> imperialCombo;
    private JComboBox<String> metricCombo;
    private JButton unitChangeButton;
    private JLabel equalText;
    private JButton typeChangeButton;

    public void connect(CalculatorController controller) {
        unitChangeButton.setActionCommand("SWAP_UNIT");
        unitChangeButton.addActionListener(
                e -> controller.handleKey(((JButton) e.getSource()).getActionCommand())
        );

        typeChangeButton.setActionCommand("SWAP_TYPE");
        typeChangeButton.addActionListener(
                e -> controller.handleKey(((JButton) e.getSource()).getActionCommand())
        );
    }

    public UnitView() {
        imperialCombo.addItem("IN");
        imperialCombo.addItem("FT");
        imperialCombo.addItem("YD");
        imperialCombo.addItem("MI");
        imperialCombo.addItem("F°");

        metricCombo.addItem("MM");
        metricCombo.addItem("CM");
        metricCombo.addItem("M");
        metricCombo.addItem("KM");
        metricCombo.addItem("C°");

        unitChangeButton.setText("IMPERIAL");
    }

    public JPanel getRootUnit() {
        return rootUnit;
    }

    public void modCombosForLength() {
        imperialCombo.removeAllItems();
        metricCombo.removeAllItems();

        imperialCombo.addItem("IN");
        imperialCombo.addItem("FT");
        imperialCombo.addItem("YD");
        imperialCombo.addItem("MI");

        metricCombo.addItem("MM");
        metricCombo.addItem("CM");
        metricCombo.addItem("M");
        metricCombo.addItem("KM");

        unitChangeButton.setText("IMPERIAL");
    }

    public void modCombosForTemperature() {
        imperialCombo.removeAllItems();
        metricCombo.removeAllItems();

        imperialCombo.addItem("F°");

        metricCombo.addItem("C°");

        unitChangeButton.setText("IMPERIAL");
    }

    @Override
    public void modelChanged(EquationModel m) {
        EquationModel.UnitState state = m.getUnitState();
        EquationModel.TypeState mode = m.getTypeState();

        imperialDisplay.setText(m.getImperialInput());
        metricDisplay.setText(m.getMetricInput());

        // 1) Which side is active?
        if (state == EquationModel.UnitState.IMPERIAL) {
            unitChangeButton.setText("IMPERIAL");
        } else {
            unitChangeButton.setText("METRIC");
        }

        // 2) Which purpose are we in? (same controls, different meaning)
        if (mode == EquationModel.TypeState.LENGTH) {
            typeChangeButton.setText("LENGTH");
            modCombosForLength();
        } else {
            typeChangeButton.setText("TEMP");
            modCombosForTemperature();
        }
    }

}
