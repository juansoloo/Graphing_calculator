package MVC.Controller;

import MVC.Model.EquationModel;

/**
 * BasicState defines how the controller behaves when the
 * calculator state is in BASIC mode. Digits and Operators
 * are appended to the model inputs, and keys like "=" trigger
 * fire specific model methods.
 */
public class BasicState implements CalculatorState {
    /**
     * handles pressed keys in BASIC mode.
     * "=" solves expression
     * "C" clears the input
     * "DEL" deletes the last character
     * digits/operators are appended to input
     * @param token the string command from the keypad
     * @param model model being updated
     */
    @Override
    public void handleKey(String token, EquationModel model) {
        switch (token) {
            case "="   -> model.solve();
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
        return "BASIC";
    }
}
