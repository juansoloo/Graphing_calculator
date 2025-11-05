package MVC;

public class HistoryView extends View {
    @Override
    public void update(EquationModel m) {
        System.out.println("History size= " + m.getHistory().size());
    }

    @Override
    public void render() {
        // UI list using Swing later
    }
}
