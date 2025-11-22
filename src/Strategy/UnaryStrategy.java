package Strategy;

import Algebra.Polynomial;

/**
 * UnaryStrategy defines the strategy for unary operations on polynomials, such
 * as negation (additive inverse) or square roots. This interface is part of the
 * strategy pattern, it encapsulates the algorithm for applying a unary operator
 * so the parser and AST can perform unary operations without depending on a
 * specific implementation. By isolating unary operations in a strategy, we can
 * vary how we compute the operations without changing the parsing or evaluation
 * logic.
 */
public interface UnaryStrategy {
    Polynomial apply(Polynomial p);
    String getSymbol();
}

