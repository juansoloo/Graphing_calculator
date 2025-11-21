package MVC.Controller;

import MVC.Model.EquationModel;

public class GraphingState implements CalculatorState {
    @Override
    public void handleKey(String token, EquationModel model) {
        switch (token) {

        }
    }

    @Override
    public String getName() {
        return "GRAPHING";
    }
}
