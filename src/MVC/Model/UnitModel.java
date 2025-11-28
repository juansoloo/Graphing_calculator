package MVC.Model;

public class UnitModel {
    private final StringBuilder imperialInput = new StringBuilder();
    private final StringBuilder metricInput = new StringBuilder();
    private TypeState activeTypeState = TypeState.LENGTH;

    // sets the current state of UnitView to IMPERIAL;
    private UnitState activeUnitState = UnitState.IMPERIAL;

    private String imperialUnit = "FT";
    private String metricUnit = "M";

    public enum UnitState {
        IMPERIAL,
        METRIC
    }

    public enum TypeState {
        LENGTH,
        TEMPERATURE
    }

    public UnitState getUnitState() {
        return activeUnitState;
    }

    public TypeState getTypeState() {
        return activeTypeState;
    }

    public void changeUnitState() {
        activeUnitState = (activeUnitState == UnitState.IMPERIAL)
                ? UnitState.METRIC
                : UnitState.IMPERIAL;
    }

    public void changeTypeState() {
        TypeState current = getTypeState();
        if (current == TypeState.LENGTH) {
            setTypeState(TypeState.TEMPERATURE);
        } else {
            setTypeState(TypeState.LENGTH);
        }
    }

    public void setTypeState(TypeState typeState) {
        if (this.activeTypeState != typeState) {
            this.activeTypeState = typeState;
        }
    }

    public void appendImperialToken(String token) {
        imperialInput.append(token);
    }

    public void appendMetricToken(String token) {
        metricInput.append(token);
    }

    public String getImperialInput() {
        return imperialInput.toString();
    }

    public String getMetricInput() {
        return metricInput.toString();
    }

    public void setImperialInput(String value) {
        imperialInput.setLength(0);
        imperialInput.append(value);
    }

    public void setMetricInput(String value) {
        metricInput.setLength(0);
        metricInput.append(value);
    }

    public String getImperialUnit() {
        return imperialUnit;
    }

    public String getMetricUnit() {
        return metricUnit;
    }

    public void setImperialUnit(String imperialUnit) {
        this.imperialUnit = imperialUnit;
    }

    public void setMetricUnit(String metricUnit) {
        this.metricUnit = metricUnit;
    }

    public void clearUnit() {
        imperialInput.setLength(0);
        metricInput.setLength(0);
    }

    public void deleteLastUnit() {
        StringBuilder active =
                (getUnitState() == UnitState.IMPERIAL)
                        ? imperialInput
                        : metricInput;

        if (active.length() > 0) {
            active.deleteCharAt(active.length() - 1);
        }
    }

}
