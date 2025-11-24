package MVC.Observer;

import MVC.Model.EquationModel;

/**
 * ModelListener is the Observer interface for the MVC pattern.
 * View implement this so they can be notified whenever the model
 * changes state. This model calls ModelChanged() on all listeners
 * after any update.
 */
public interface ModelListener {
    void modelChanged(EquationModel m);
}
