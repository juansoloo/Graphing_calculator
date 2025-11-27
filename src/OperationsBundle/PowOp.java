package OperationsBundle;

import Algebra.Polynomial;

/**
 * PowOp defines the specific algorithm for solving exponentiation,
 * between a polynomial term and a integer exponent. Uses the interface
 * PowStrategy.
 */

public class PowOp implements PowStrategy {
    /**
     * Applies exponentiation between a polynomial term and integer exponent
     * using the method pow() defines in Polynomial.
     * @param base the polynomial base
     * @param exponent integer exponent
     * @return returns the result of base^exponent as a Polynomial
     */
    @Override
    public Polynomial apply(Polynomial base, int exponent) {
        return base.pow(exponent);
    }

    /**
     * Returns the symbolic representation of power. Used by the parser
     * and AST for debugging, display, construction of expressions.
     * @return the exponentiation symbol ("^") as a String
     */
    @Override
    public String getSymbol() {
        return "^";
    }
}
