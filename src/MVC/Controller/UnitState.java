package MVC.Controller;

import MVC.Model.EquationModel;

public class UnitState implements CalculatorState {
    @Override
    public void handleKey(String token, EquationModel model) {
        switch (token) {
            case "=" -> model.solve(); //convert()
            case "C" -> model.clearUnit();
            case "DEL" -> model.deleteLastUnit();
            case "SWAP_UNIT" -> model.changeUnitState();
            case "SWAP_TYPE" -> model.changeTypeState();
            default -> {
                if (model.getUnitState() == EquationModel.UnitState.IMPERIAL) {
                    model.appendImperialToken(token);
                } else {
                    model.appendMetricToken(token);
                }
            }
        }
    }

    @Override
    public String getName() {
        return "UNIT";
    }
}
