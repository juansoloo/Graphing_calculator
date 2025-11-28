package MVC.View;

import MVC.Controller.CalculatorController;
import MVC.Model.EquationModel;
import MVC.Model.UnitModel;
import MVC.Observer.ModelListener;

import javax.swing.*;

public class UnitView extends View implements ModelListener {
    private JPanel rootUnit;

    private EquationModel model;
    private JTextField imperialDisplay;
    private JTextField metricDisplay;
    private JComboBox<String> imperialCombo;
    private JComboBox<String> metricCombo;
    private JButton unitChangeButton;
    private JLabel equalText;
    private JButton typeChangeButton;

    private UnitModel.TypeState lastMode = null;

    public void connect(EquationModel model, CalculatorController controller) {
        unitChangeButton.setActionCommand("SWAP_UNIT");
        unitChangeButton.addActionListener(
                e -> controller.handleKey(((JButton) e.getSource()).getActionCommand())
        );

        typeChangeButton.setActionCommand("SWAP_TYPE");
        typeChangeButton.addActionListener(
                e -> controller.handleKey(((JButton) e.getSource()).getActionCommand())
        );


        imperialCombo.addActionListener(e -> {
            String unit = (String) imperialCombo.getSelectedItem();
            if (unit != null) {
                model.getUnitModel().setImperialUnit(unit);
            }
        });

        metricCombo.addActionListener(e -> {
            String unit = (String) metricCombo.getSelectedItem();
            if (unit != null) {
                model.getUnitModel().setMetricUnit(unit);
            }
        });
    }

    public UnitView() {
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
        UnitModel.UnitState state = m.getUnitState();
        UnitModel.TypeState mode = m.getTypeState();

        imperialDisplay.setText(m.getImperialInput());
        metricDisplay.setText(m.getMetricInput());

        // 1) Active side label
        if (state == UnitModel.UnitState.IMPERIAL) {
            unitChangeButton.setText("IMPERIAL");
        } else {
            unitChangeButton.setText("METRIC");
        }

        // 2) Mode (and only rebuild combos when mode actually changes)
        if (mode != lastMode) {
            if (mode == UnitModel.TypeState.LENGTH) {
                typeChangeButton.setText("LENGTH");
                modCombosForLength();
            } else {
                typeChangeButton.setText("TEMP");
                modCombosForTemperature();
            }
            lastMode = mode;
        }

        // 3) Sync combo selection with what the model says
        UnitModel um = m.getUnitModel();
        imperialCombo.setSelectedItem(um.getImperialUnit());
        metricCombo.setSelectedItem(um.getMetricUnit());
    }

}
