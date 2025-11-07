package MVC.Controller;

import MVC.Model.EquationModel;

public class CalculatorController  {
    private final EquationModel model;

    public CalculatorController(EquationModel model) {
        this.model = model;
    }

    public void actionPerformed(String token) {
        switch (token) {
            case "=" -> model.solve();
            case "C" -> model.clear();
            default -> model.appendToken(token);
        }
    }
}
