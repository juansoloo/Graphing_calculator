package MVC.Controller;

import MVC.Model.EquationModel;

public class GraphState implements CalculatorState {
    @Override
    public void handleKey(String token, EquationModel model) {
        switch (token) {
            case "="   -> model.graph();
            case "C"   -> model.clear();
            case "DEL" -> model.deleteLast();
            default    -> model.appendToken(token);
        }
    }

    @Override
    public String getName() {
        return "GRAPHING";
    }
}
