package MVC.View;

import MVC.Model.EquationModel;
import MVC.Observer.ModelListener;

public abstract class View implements ModelListener {
    @Override
    public abstract void modelChanged(EquationModel m);
}
