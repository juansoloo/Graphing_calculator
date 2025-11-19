package CompositePattern;

import Algebra.Polynomial;

public class LiteralOp implements Expression {
    private final int value;

    public LiteralOp(int value) {
        this.value = value;
    }

    @Override
    public Polynomial evaluate() {
        return Polynomial.constant(value);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}

