package OperationsBundle;

import Algebra.Polynomial;

/**
 * AddOp implements the BinaryStrategy and defines the algorithm to add
 * together two Polynomial terms.
 */
public class AddOp implements BinaryStrategy {
    /**
     * Applies addition as a + b using the add() method defined in Polynomial.
     * @param a left Polynomial operand
     * @param b right Polynomial operand
     * @return  returns the result of a + b as a Polynomial
     */
    @Override
    public Polynomial apply(Polynomial a, Polynomial b) {
        return a.add(b);
    }

    /**
     * Returns the symbolic representation of addition ("+"). Used by the parser
     * and AST for debugging, display, construction of expressions.
     * @return the addition symbol ("+") as a String
     */
    @Override
    public String getSymbol() {
        return "+";
    }
}
