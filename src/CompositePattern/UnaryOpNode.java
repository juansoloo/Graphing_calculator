package CompositePattern;

import Algebra.Polynomial;

public class UnaryOp implements Expression {
    private final Expression child;
    private final UnaryOp op;

    public UnaryOp(Expression child, UnaryOp op) {
        this.child = child;
        this.op = op;
    }

    @Override
    public Polynomial evaluate() {
        return op.apply(child.evaluate());
    }
}

