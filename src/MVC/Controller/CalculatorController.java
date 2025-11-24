package MVC.Controller;

import MVC.Model.EquationModel;
import MVC.View.GraphView;
import MVC.View.KeypadView;

import javax.swing.*;

/**
 * CalculatorController connects the Model and Views and manages how user input is
 * routed through the app. It uses the state pattern to switch between different
 * calculator states (BASIC, GRAPHING, EQUATION, AND UNIT conversion states.
 * All buttons pressed from keypad a flows into this controller.
 */
public class CalculatorController {
    private final EquationModel model;
    private final KeypadView keypad;
    private final GraphView graphView;
    private final JFrame frame;

    // state objects representing each mode
    private final CalculatorState basicState;
    private final CalculatorState equationState;
    private final CalculatorState graphState;
    // private final CalculatorState unitState;

    // current state
    private CalculatorState state;

    /**
     * Constructor for CalculatorController. It receives the model, keypad view,
     * graph view, and the root app frame. All calculator states are created here,
     * and the controller begins BASIC mode by default.
     * @param model the EquationModel
     * @param keypad keypad view that sends user button input
     * @param graphView graphing panel used by Graph mode
     * @param frame main app window
     */
    public CalculatorController(
            EquationModel model, KeypadView keypad, GraphView graphView, JFrame frame) {
        this.model = model;
        this.keypad = keypad;
        this.graphView = graphView;
        this.frame = frame;

        // creates all instances of calculator modes
        this.equationState = new EquationState();
        this.basicState = new BasicState();
        this.graphState = new GraphState();
        //this.unitState = new UnitState();

        // starts app in BASIC mode
        this.state = basicState;
        keypad.setBasicMode();
    }

    /**
     * Routes every button press received from the keypad. Mode tokens
     * received changes the state of the calculator, all other tokens
     * are sent the active states handleKey() method.
     * @param token action command associated with pressed button
     */
    public void handleKey(String token) {
        switch (token) {
            // sets the state according to token and resizes frame.
            // sets certain to not visible depending on state
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

            // forwards other tokens to state's handleKey()
            default -> state.handleKey(token, model);
        }
    }
}

