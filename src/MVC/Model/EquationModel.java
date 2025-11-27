package MVC.Model;

import Algebra.Polynomial;
import MVC.Observer.ModelListener;
import OperationsBundle.*;
import Parser.EquationParser;

import java.util.ArrayList;
import java.util.List;

/**
 * EquationModel is the model for the MVC pattern. It
 * stores the user input, tracks last computed result,
 * handles parsing, solving, and graphing. Notifies all
 * the views whenever the model changes state.
 */
public class EquationModel {
    // current expression user is typing
    private final StringBuilder input = new StringBuilder();

    // string builder for imperial and metric units in UNIT mode
    private final StringBuilder imperialInput = new StringBuilder();
    private final StringBuilder metricInput = new StringBuilder();
    private TypeState activeTypeState = TypeState.LENGTH;

    // sets the current state of UnitView to IMPERIAL;
    private UnitState activeUnitState = UnitState.IMPERIAL;

    // Last computed polynomial result
    private Polynomial lastResult = Polynomial.zero();

    // last error message
    private String lastError = null;

    // Array of all observers (DisplayView, GraphView, KeypadView, UnitView)
    private final List<ModelListener> listeners = new ArrayList<>();

    // Operator Strategies used by the parser
    private final BinaryStrategy addOp = new AddOp();
    private final BinaryStrategy subOp = new SubOp();
    private final BinaryStrategy mulOp = new MultOp();
    private final BinaryStrategy divOp = new DivOp();
    private final UnaryStrategy negOp = new NegOp();
    private final PowStrategy powOp = new PowOp();
    private final UnaryStrategy rootOp = new RootOp();

    // Polynomial used bt the GraphView
    private Polynomial graphPoly = null;

    /**
     * Appends token to the current input (digit, operator, variable, etc.)
     * @param token
     */
    public void appendToken(String token) {
        lastError = null;
        input.append(token);
        notifyListeners();
    }

    /**
     * Clears the current input, resets lastResult
     */
    public void clear() {
        input.setLength(0);
        lastError = null;

        lastResult = Polynomial.zero();

        notifyListeners();
    }

    /**
     * Deletes the last character of the current input
     */
    public void deleteLast() {
        if (input.length() > 0) {
            input.deleteCharAt(input.length() - 1);
            notifyListeners();
        }
    }

    /**
     * Parses the input expression into a Polynomial and updates lastResult.
     * Replaces input with the polynomial's string form, if there's and
     * error, it stores the message.
     */
    public void solve() {
        lastError = null;
        try {
            String expr = input.toString();
            if (expr.isEmpty()) {
                return;
            }

            EquationParser parser =
                    new EquationParser(
                            expr, addOp, subOp, mulOp, divOp, negOp, powOp, rootOp);

            Polynomial p = parser.parse();
            lastResult = p;

            input.setLength(0);
            input.append(p.toString());
        } catch (Exception e) {
            lastError = e.getMessage();
        }

        notifyListeners();
    }

    /**
     * Adds listeners to listeners arrayList
     * @param l
     */
    public void addListener(ModelListener l) {
        listeners.add(l);
    }

    /**
     * Returns the correct string for DisplayView (input, last result, error)
     * @return String (input, last result, error)
     */
    public String getDisplayText() {
        if (lastError != null) {
            return "ERR: " + lastError;
        }

        if (input.length() > 0) {
            return input.toString();
        }

        return lastResult.toString();
    }

    /**
     * Returns the current expression in the models StringBuilder input
     * @return string current expression in model
     */
    public String getInput() {
        return input.toString();
    }

    /**
     * Getter for addition strategy
     * @return addOp
     */
    public BinaryStrategy getAddOp() {
        return addOp;
    }

    /**
     * Getter for subtraction strategy
     * @return subOp
     */
    public BinaryStrategy getSubOp() {
        return subOp;
    }

    /**
     * Getter for multiplication strategy
     * @return mulOp
     */
    public BinaryStrategy getMulOp() {
        return mulOp;
    }

    /**
     * Getter for division strategy
     * @return divOp
     */
    public BinaryStrategy getDivOp() {
        return divOp;
    }

    /**
     * Getter for negative strategy
     * @return negOp
     */
    public UnaryStrategy getNegOp() {
        return negOp;
    }

    /**
     * Getter for power strategy
     * @return powOp
     */
    public PowStrategy getPowOp() {
        return powOp;
    }

    /**
     * Getter for square root strategy
     * @return rootOp
     */
    public UnaryStrategy getRootOp() {
        return rootOp;
    }

    /**
     * Displays the solved solution in DisplayView
     * @param s solution to display
     */
    public void showSolution(String s) {
        lastError = null;
        input.setLength(0);
        input.append(s);
        notifyListeners();
    }

    /**
     * Displays an error message in DisplayView
     * @param msg error message to display
     */
    public void showError(String msg) {
        lastError = msg;
        notifyListeners();
    }

    /**
     * Converts the current input into a polynomial that the
     * GraphView can plot
     */
    public void graph() {
        // resets error message and polynomial
        lastError = null;
        graphPoly = null;

        // gets current user input as string
        String expr = input.toString();

        // try catch creates the parser instance, and parses the polynomial and graphs it
        // else stores error message
        try {
            EquationParser parser =
                    new EquationParser(expr, addOp, subOp, mulOp, divOp, negOp, powOp, rootOp);
            graphPoly = parser.parse();
        } catch (Exception e) {
            lastError = e.getMessage();
            graphPoly = null;
        }

        notifyListeners();
    }

    /**
     * Returns the polynomial to be graphed
     * @return Polynomial to be graphed
     */
    public Polynomial getGraph() {
        return graphPoly;
    }

    //------------------------------------------------------------

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
        notifyListeners();
    }

    public void changeTypeState() {
        EquationModel.TypeState current = getTypeState();
        if (current == EquationModel.TypeState.LENGTH) {
            setTypeState(EquationModel.TypeState.TEMPERATURE);
        } else {
            setTypeState(EquationModel.TypeState.LENGTH);
        }
    }

    public void setTypeState(TypeState typeState) {
        if (this.activeTypeState != typeState) {
            this.activeTypeState = typeState;
            notifyListeners();
        }
    }

    public void appendImperialToken(String token) {
        lastError = null;
        imperialInput.append(token);
        notifyListeners();
    }

    public void appendMetricToken(String token) {
        lastError = null;
        metricInput.append(token);
        notifyListeners();
    }

    public String getImperialInput() {
        return imperialInput.toString();
    }

    public String getMetricInput() {
        return metricInput.toString();
    }

    public void clearUnit() {
        imperialInput.setLength(0);
        metricInput.setLength(0);

        notifyListeners();
    }

    public void deleteLastUnit() {
        StringBuilder active =
                (getUnitState() == UnitState.IMPERIAL)
                        ? imperialInput
                        : metricInput;

        if (active.length() > 0) {
            active.deleteCharAt(active.length() - 1);
            notifyListeners();
        }
    }

 //-----------------------------------------------------------------


    /**
     * Notifies all views that the model has changed state
     */
    public void notifyListeners() {
        for (ModelListener l : listeners) {
            l.modelChanged(this);
        }
    }
}
