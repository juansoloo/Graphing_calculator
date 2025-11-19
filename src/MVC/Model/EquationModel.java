package MVC.Model;

import Algebra.Polynomial;
import MVC.Utils.HistoryEntry;
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
    private final List<HistoryEntry> history = new ArrayList<>();

    private final BinaryStrategy addOp = new AddOp();
    private final BinaryStrategy subOp = new SubOp();
    private final BinaryStrategy mulOp = new MultOp();
    private final BinaryStrategy divOp = new DivOp();
    private final UnaryStrategy negOp = new NegOp();
    private final PowStrategy powOp = new PowOp();

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
                    new EquationParser(expr, addOp, subOp, mulOp, divOp, negOp, powOp);

            Polynomial p = parser.parse();
            lastResult = p;
            String result = p.toString();

            // history.add(new HistoryEntry(expression, result, System.currentTimeMillis()));

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

    public void removeListener(ModelListener l) {
        listeners.remove(l);
    }

    public java.util.List<HistoryEntry> getHistory() {
        return List.copyOf(history);
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

//    public void clearHistory() {
//        history.clear();
//        notifyListeners();
//    }

//    public void recall(int index) {
//        if (index >= 0 && index < history.size()) {
//            this.expression = history.get(index).expression();
//            notifyListeners();
//        }
//    }

//    private void addToHistory(String expr, String res) {
//        history.add(new HistoryEntry(expr, res, System.currentTimeMillis()));
//    }
//    public void setExpression(String expr) {
//        this.expression = expr;
//        notifyListeners();
//    }

    public void notifyListeners() {
        for (ModelListener l : listeners) {
            l.modelChanged(this);
        }
    }
}
