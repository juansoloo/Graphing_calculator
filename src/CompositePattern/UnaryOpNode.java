package CompositePattern;

import Algebra.Polynomial;
import Strategy.UnaryStrategy;

public class UnaryOpNode implements Expression {
    private final Expression child;
    private final UnaryStrategy op;

    public UnaryOpNode(UnaryStrategy op, Expression child) {
        this.child = child;
        this.op = op;
    }

    @Override
    public Polynomial evaluate() {
        return op.apply(child.evaluate());
    }
}

