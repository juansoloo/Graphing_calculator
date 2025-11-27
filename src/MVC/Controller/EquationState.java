package MVC.Controller;

import Algebra.EquationSolver;
import MVC.Model.EquationModel;

/**
 * EquationState defines how the controller behaves while the
 * calculator is in EQUATION mode. Inputs are treated as
 * symbolic expressions (quadratics, linear). Pressing "SOLVE"
 * triggers EquationSolver to solve for x.
 */
public class EquationState implements CalculatorState {
    /**
     * Handles buttons pressed in EQUATION mode
     * "SOLVE_EQ" parses and solve expression for x
     * "C" clears input
     * "DEL" deletes the last character
     * digits/operators are appended to input
     * @param token the string command from the keypad
     * @param model model being updated
     */
    @Override
    public void handleKey(String token, EquationModel model) {
        switch (token) {
            case "SOLVE_EQ" -> {
                try {
                    // get user input string (expression)
                    String expr = model.getInput();
                    if (expr.isEmpty()) {
                        return;
                    }

                    // solves for x using model strategy operators
                    String sol = EquationSolver.solveForX(
                            expr, model.getAddOp(),
                            model.getSubOp(),
                            model.getMulOp(),
                            model.getDivOp(),
                            model.getNegOp(),
                            model.getPowOp(),
                            model.getRootOp()
                    );

                    // displays solution in display view
                    model.showSolution(sol);
                } catch (Exception ex) {
                    model.showError(ex.getMessage());
                }
            }
            case "C" -> model.clear();
            case "DEL" -> model.deleteLast();
            default -> model.appendToken(token);
        }
    }

    /**
     * returns the current mode of the calculator
     * @return String current mode of calculator
     */
    @Override
    public String getName() {
        return "EQUATION";
    }
}
