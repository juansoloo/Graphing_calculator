package MVC;

public abstract class View implements ModelListener {
    @Override
    public abstract void update(EquationModel m);
    public abstract void render();
}
