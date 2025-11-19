package CompositePattern;

import Algebra.Polynomial;
import Strategy.BinaryStrategy;

public class BinaryOpNode implements Expression{
    private final Expression left;
    private final Expression right;
    private final BinaryStrategy op; // the algorithm used to compute (add, sub, mult)
    
    public BinaryOpNode(Expression left, Expression right, BinaryStrategy op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }
    
    @Override
    public Polynomial evaluate() {
        Polynomial l = left.evaluate();
        Polynomial r = right.evaluate();
        return op.apply(l,r);
    }
    
    @Override
    public String toString() {
        return "(" + left + " " + op.getSymbol() + " " + right + ")"; 
    }
}
