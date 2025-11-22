package Strategy;

import Algebra.Polynomial;

/**
 * SubOp defines the specific algorithm for subtracting two terms, using the
 * interface BinaryStrategy.
 */
public class SubOp implements BinaryStrategy {
    /**
     * Applies subtraction by using polynomial addition and multiplication.
     * The expression a - b is calculated as a + (b * -1), using the methods
     * add() and mulConst() defined in Polynomial.
     * @param a left polynomial operand
     * @param b right polynomial operand
     * @return the result of a - b as a Polynomial
     */
    @Override
    public Polynomial apply(Polynomial a, Polynomial b) {
        return a.add(b.mulConst(-1));
    }

    /**
     * Returns the symbolic representation of subtraction ("-"). Used by the parser
     * and AST for debugging, display, construction of expressions.
     * @return the subtraction symbol ("-") as a String
     */
    @Override
    public String getSymbol() {
        return "-";
    }
}
