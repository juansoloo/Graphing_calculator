package MVC.View;

import MVC.Model.EquationModel;
import MVC.Utils.ModelListener;

public abstract class View implements ModelListener {
    @Override
    public abstract void update(EquationModel m);
    public abstract void render();
}
