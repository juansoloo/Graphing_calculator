package Strategy;

import Algebra.Polynomial;

/**
 * NegOp implements the UnaryStrategy interface and defines the algorithm
 * for applying a negation (additive inverse) to a polynomial.
 * Negation is implemented by multiplying the polynomial by -1, which
 * flips the sign of term.
 */
public class NegOp implements UnaryStrategy {
    /**
     * Applies negation operation to the Polynomial term p, by multiplying by
     * -1 using the mulConst() method from Polynomial.
     * @param p the polynomial term
     * @return the result of negating p (-p)
     */
    @Override
    public Polynomial apply(Polynomial p) {
        return p.mulConst(-1);
    }

    /**
     * Returns the symbolic representation of the unary operation ("-"). Used by
     * the parser and AST for debugging, display, construction of expressions.
     * @return the unary minus operator symbol ("-") as a String
     */
    @Override
    public String getSymbol() {
        return "-";
    }
}
