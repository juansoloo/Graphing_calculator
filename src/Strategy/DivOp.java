package Strategy;

import Algebra.Polynomial;

/**
 * DivOp implements the BinaryStrategy and defines the algorithm to
 * implement division between two polynomial terms. Only division by a
 * constant is supported.
 */
public class DivOp implements BinaryStrategy {
    /**
     * Performs polynomial division where the divisor must be a constant.
     * Only cases of the form (Polynomial / c) are supported. If the divisor
     * polynomial has degree > 0, or if c = 0, an error is thrown.
     * @param a the dividend polynomial
     * @param b the divisor polynomial,  must be degree 0
     * @return the polynomial resulting from dividing each coefficient of a by c
     * @throws IllegalArgumentException if divisor is not degree 0 or equals 0
     */
    @Override
    public Polynomial apply(Polynomial a, Polynomial b) {
        // degree of divisor
        int deg = b.degree();

        // allows only division by constants
        if (deg != 0) {
            throw new IllegalArgumentException("Only division by constants is supported");
        }

        // gets constant divisor value
        int divisor = b.get(0);

        // disallows division by zero
        if (divisor == 0) {
            throw new IllegalArgumentException("Division by zero");
        }

        // init the result as zero polynomial
        Polynomial result = Polynomial.zero();
        for (int i = 0; i <= a.degree(); i++) {
            result.set(i, a.get(i) / divisor);
        }

        return result;
    }

    /**
     * Returns the symbolic representation of the division operator ("/"). Used by
     * the parser and AST for debugging, display, construction of expressions.
     * @return the division operator symbol ("/") as a String
     */
    @Override
    public String getSymbol() {
        return "/";
    }
}
