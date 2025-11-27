package OperationsBundle;

import Algebra.Polynomial;

/**
 * MultOp implements the BinaryStrategy interface and defines the algorithm
 * for applying a multiplication to two polynomial terms.
 */
public class MultOp implements BinaryStrategy {
    /**
     * Multiplication is defined by using the method mul() defined in Polynomial.
     * It is calculated as a * b.
     * @param a left Polynomial operand
     * @param b right Polynomial operand
     * @return the result of a * b as a Polynomial
     */
    @Override
    public Polynomial apply(Polynomial a, Polynomial b) {
        return a.mul(b);
    }

    /**
     * Returns the symbolic representation of the multiplication ("*"). Used by
     * the parser and AST for debugging, display, construction of expressions.
     * @return the multiplication symbol ("*") as a String
     */
    @Override
    public String getSymbol() {
        return "*";
    }
}
