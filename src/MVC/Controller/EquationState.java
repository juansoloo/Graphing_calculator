package MVC.Controller;

import Algebra.EquationSolver;
import MVC.Model.EquationModel;

public class EquationState implements CalculatorState {
    @Override
    public void handleKey(String token, EquationModel model) {
        switch (token) {
            case "SOLVE_EQ" -> {
                try {
                    String expr = model.getInput();
                    if (expr.isEmpty()) return;

                    String sol = EquationSolver.solveForX(
                            expr,
                            model.getAddOp(),
                            model.getSubOp(),
                            model.getMulOp(),
                            model.getDivOp(),
                            model.getNegOp(),
                            model.getPowOp()
                    );
                    model.showSolution(sol);
                } catch (Exception ex) {
                    model.showError(ex.getMessage());
                }
            }
            case "C"   -> model.clear();
            case "DEL" -> model.deleteLast();
            default    -> model.appendToken(token);
        }
    }

    @Override
    public String getName() {
        return "EQUATION";
    }
}
