package MVC.Controller;

import MVC.Model.EquationModel;
import MVC.Model.UnitModel;

public class UnitState implements CalculatorState  {
    @Override
    public void handleKey(String token, EquationModel model) {
        switch (token) {
            case "=" -> model.convert();
            case "C" ->  model.clearUnit();
            case "DEL" ->  model.deleteLastUnit();
            case "SWAP_UNIT" ->  model.changeUnitState();
            case "SWAP_TYPE" ->  model.changeTypeState();
            default -> {
                if ( model.getUnitState() == UnitModel.UnitState.IMPERIAL) {
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
