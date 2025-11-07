package MVC;

import java.util.ArrayList;
import java.util.List;

public class EquationModel {
    private final List<ModelListener> listeners = new ArrayList<>();
    private final List<HistoryEntry> history = new ArrayList<>();
    private String expression = "";
    private String result = "";
    private String error = "";

//    public void setExpression(String expr) {
//        this.expression = expr;
//        notifyListeners();
//    }

    public void appendToken(String token) {
        this.expression += token;
        notifyListeners();
    }
    public void clear() {
        this.expression = "";
        this.result = "";
        this.error = "";
        notifyListeners();
    }

    // call Strategy pattern later
    public void solve() {
        this.result = expression;
        addToHistory(expression, result);
        notifyListeners();
    }

    public String getExpression() {
        return expression;
    }

    public String getResult() {
        return result;
    }

    public java.util.List<HistoryEntry> getHistory() {
        return List.copyOf(history);
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

    private void addToHistory(String expr, String res) {
        history.add(new HistoryEntry(expr, res, System.currentTimeMillis()));
    }

    public void addListener(ModelListener l) {
        listeners.add(l);
    }

//    public void removeListener(ModelListener l) {
//        listeners.remove(l);
//    }

    public void notifyListeners() {
        for (ModelListener l : listeners) {
            l.update(this);
        }
    }
}
