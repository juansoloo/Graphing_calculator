package MVC.Controller;

import MVC.Model.EquationModel;
import MVC.View.GraphView;
import MVC.View.KeypadView;

import javax.swing.*;

public class CalculatorController {
    private final EquationModel model;
    private final KeypadView keypad;
    private final GraphView graphView;
    private final JFrame frame;

    private final CalculatorState basicState;
    private final CalculatorState equationState;
    private final CalculatorState graphState;
    // private final CalculatorState unitState;

    private CalculatorState state;

    public CalculatorController(EquationModel model, KeypadView keypad, GraphView graphView, JFrame frame) {
        this.model = model;
        this.keypad = keypad;
        this.graphView = graphView;
        this.frame = frame;

        this.equationState = new EquationState();
        this.basicState = new BasicState();
        this.graphState = new GraphState();
        //this.unitState = new UnitState();

        this.state = basicState;
        keypad.setBasicMode();
    }

    public void handleKey(String token) {
        switch (token) {
            case "MODE_EQ" -> {
                state = equationState;
                keypad.setEquationMode();
                graphView.getRoot().setVisible(false);
                frame.pack();
            }
            case "MODE_BASIC" -> {
                state = basicState;
                keypad.setBasicMode();
                graphView.getRoot().setVisible(false);
                frame.pack();
            }
//            case "MODE_UNIT" -> {
//                state = unitState;
//                keypad.setUnitMode();
//                graphView.getRoot().setVisible(false);
//                frame.pack();
//            }
            case "MODE_GRAPH" -> {
                state = graphState;
                keypad.setGraphMode();
                graphView.getRoot().setVisible(true);
                frame.pack();
            }
            default -> state.handleKey(token, model);
        }
    }
}

