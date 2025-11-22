package MVC.Model;

import Algebra.Polynomial;
import MVC.Utils.ModelListener;
import Parser.EquationParser;
import Strategy.*;

import java.util.ArrayList;
import java.util.List;

public class EquationModel {
    private final StringBuilder input = new StringBuilder();   // what user is typing
    private Polynomial lastResult = Polynomial.zero();         // last computed result
    private String lastError = null;

    private final List<ModelListener> listeners = new ArrayList<>();

    private final BinaryStrategy addOp = new AddOp();
    private final BinaryStrategy subOp = new SubOp();
    private final BinaryStrategy mulOp = new MultOp();
    private final BinaryStrategy divOp = new DivOp();
    private final UnaryStrategy negOp = new NegOp();
    private final PowStrategy powOp = new PowOp();
    private final UnaryStrategy rootOp = new RootOp();

    public void appendToken(String token) {
        lastError = null;
        input.append(token);
        notifyListeners();
    }

    public void clear() {
        input.setLength(0);
        lastError = null;

        lastResult = Polynomial.constant(0);

        notifyListeners();
    }

    public void deleteLast() {
        if (input.length() > 0) {
            input.deleteCharAt(input.length() - 1);
            notifyListeners();
        }
    }

    public void solve() {
        lastError = null;
        try {
            String expr = input.toString();
            if (expr.isEmpty()) {
                return;
            }

            EquationParser parser =
                    new EquationParser(expr, addOp, subOp, mulOp, divOp, negOp, powOp, rootOp);

            Polynomial p = parser.parse();
            lastResult = p;

            input.setLength(0);
            input.append(p.toString());
        } catch (Exception e) {
            lastError = e.getMessage();
        }

        notifyListeners();
    }

    public void addListener(ModelListener l) {
        listeners.add(l);
    }

    public String getDisplayText() {
        if (lastError != null) {
            return "ERR: " + lastError;
        }

        if (input.length() > 0) {
            return input.toString();
        }

        return lastResult.toString();
    }

    public Polynomial getLastResult() {
        return lastResult;
    }

    // calls methods for equation solving -------------------

    public String getInput() {
        return input.toString();
    }

    public BinaryStrategy getAddOp() {
        return addOp;
    }

    public BinaryStrategy getSubOp() {
        return subOp;
    }

    public BinaryStrategy getMulOp() {
        return mulOp;
    }

    public BinaryStrategy getDivOp() {
        return divOp;
    }

    public UnaryStrategy getNegOp() {
        return negOp;
    }

    public PowStrategy getPowOp() {
        return powOp;
    }

    public UnaryStrategy getRootOp() {
        return rootOp;
    }
    public void showSolution(String s) {
        lastError = null;
        input.setLength(0);
        input.append(s);
        notifyListeners();
    }

    public void showError(String msg) {
        lastError = msg;
        notifyListeners();
    }

    public void graph() {
        notifyListeners();
    }

    public void notifyListeners() {
        for (ModelListener l : listeners) {
            l.modelChanged(this);
        }
    }
}
