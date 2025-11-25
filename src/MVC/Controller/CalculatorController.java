package MVC.Controller;

import MVC.Model.EquationModel;
import MVC.View.DisplayView;
import MVC.View.GraphView;
import MVC.View.KeypadView;
import MVC.View.UnitView;

import javax.swing.*;
import java.awt.*;

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
    private final UnitView unitView;
    private final JFrame frame;

    // state objects representing each mode
    private final CalculatorState basicState;
    private final CalculatorState equationState;
    private final CalculatorState graphState;
    private final CalculatorState unitState;
    private final DisplayView displayView;

    // current state
    private CalculatorState state;

    private final JPanel topArea;


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
            EquationModel model, KeypadView keypad, GraphView graphView, JFrame frame,
            DisplayView displayView, UnitView unitView, JPanel topArea) {
        this.model = model;
        this.keypad = keypad;
        this.graphView = graphView;
        this.frame = frame;
        this.displayView = displayView;
        this.unitView = unitView;
        this.topArea = topArea;


        // creates all instances of calculator modes
        this.equationState = new EquationState();
        this.basicState = new BasicState();
        this.graphState = new GraphState();
        this.unitState = new UnitState();

        // starts app in BASIC mode
        this.state = basicState;
        keypad.setBasicMode();
    }

    /**
     * Returns the current state (graphState) of the calculator
     * @return graphState
     */
    public boolean isGraph() {
        return state == graphState;
    }

    /**
     * Returns the current state (unitState) of the calculator
     * @return unitState
     */
    public boolean isUnit() {
        return state == unitState;
    }

    /**
     * Returns the current state (equationState) of the calculator
     * @return equationState
     */
    public boolean isEquation() {
        return state == equationState;
    }

    /**
     * Returns the current state (basicState) of the calculator
     * @return basicState
     */
    public boolean isBasic() {
        return state == basicState;
    }

    /**
     * Updates the UI according to the current state of the calculator
     */
    private void updateUI() {
        // Clear what's currently in the top area
        topArea.removeAll();

        if (isGraph()) {
            // GRAPH mode, show graph above keypad, display visible
            topArea.add(graphView.getRootGraph(), BorderLayout.CENTER);
            displayView.getComponent().setVisible(true);
        } else if (isUnit()) {
            // UNIT mode, show unit conversion view above keypad, hide display
            topArea.add(unitView.getRootUnit(), BorderLayout.CENTER);
            displayView.getComponent().setVisible(false);
        } else {
            // BASIC or EQUATION mode, no extra panel above keypad, display visible
            displayView.getComponent().setVisible(true);
        }

        topArea.revalidate();
        topArea.repaint();
        frame.pack();
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
                frame.pack();
                updateUI();
            }
            case "MODE_BASIC" -> {
                state = basicState;
                keypad.setBasicMode();
                updateUI();
            }
            case "MODE_UNIT" -> {
                state = unitState;
                keypad.setUnitMode();
                updateUI();
            }
            case "MODE_GRAPH" -> {
                state = graphState;
                keypad.setGraphMode();
                updateUI();
            }

            // forwards other tokens to state's handleKey()
            default -> state.handleKey(token, model);
        }
    }
}

