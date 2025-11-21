package MVC.Controller;

import MVC.Model.EquationModel;
import MVC.View.KeypadView;

public class CalculatorController {
    private final EquationModel model;
    private final KeypadView keypad;

    private final CalculatorState basicState;
    private final CalculatorState equationState;

    private CalculatorState state;

    public CalculatorController(EquationModel model, KeypadView keypad) {
        this.model = model;
        this.keypad = keypad;

        this.equationState = new EquationState();
        this.basicState = new BasicState();

        this.state = basicState;
        keypad.setBasicMode();
    }

    public void handleKey(String token) {
        switch (token) {
            case "MODE_EQ" -> {
                state = equationState;
                keypad.setEquationMode();
            }
            case "MODE_BASIC" -> {
                state = basicState;
                keypad.setBasicMode();
            }
//            case "MODE_UNIT" -> {
//                state = unitState;
//                keypad.setUnitMode();
//            }
//            case "MODE_GRAPH" -> {
//                state = graphState;
//                keypad.setGraphMode();
//            }
            default -> state.handleKey(token, model);
        }
    }
}

