package CompositePattern;

import Algebra.Polynomial;

public class VariableOp implements Expression {
    public VariableOp() {}

    @Override
    public Polynomial evaluate() {
        return Polynomial.x();
    }

    @Override
    public String toString() {
        return "x";
    }
}
