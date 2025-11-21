package MVC.Utils;

import MVC.Model.EquationModel;

// observer
public interface ModelListener {
    void modelChanged(EquationModel m);
}
