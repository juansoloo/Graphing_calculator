package OperationsBundle;

import Algebra.Polynomial;

/**
 * PowStrategy defines the behavior for applying an  exponent and a polynomial
 * term. This interface is part of the Strategy pattern, it encapsulates the
 * algorithm used for computing a power operation (a^b) so that the parser and
 * AST can perform exponents without depending on a specific implementation.
 * By isolating exponents in a strategy, we can vary how we compute the
 * power operations without changing the parsing or evaluation logic.
 */
public interface PowStrategy {
    Polynomial apply(Polynomial base, int exponent);
    String getSymbol();
}
