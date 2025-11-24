package MVC.Controller;

import MVC.Model.EquationModel;

/**
 * GraphState defines how the controller behaves while in GRAPHING
 * mode. User input is treated as a Polynomial expression. Pressing the
 * GRAPH ("=") button plots the expression
 */
public class GraphState implements CalculatorState {
    /**
     * Handles buttons pressed in GRAPH mode
     * "=" parses and graphs expression
     * "C" clears input
     * "DEL" deletes the last character
     * digits/operators are appended to input
     * @param token the string command from the keypad
     * @param model model being updated
     */
    @Override
    public void handleKey(String token, EquationModel model) {
        switch (token) {
            case "="   -> model.graph();
            case "C"   -> model.clear();
            case "DEL" -> model.deleteLast();
            default    -> model.appendToken(token);
        }
    }

    /**
     * returns the current mode of the calculator
     * @return String current mode of calculator
     */
    @Override
    public String getName() {
        return "GRAPHING";
    }
}
