package MVC.Controller;

import MVC.Model.EquationModel;


public interface CalculatorState {
    void handleKey(String token, EquationModel model);
    String getName();
}
