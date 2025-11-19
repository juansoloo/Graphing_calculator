package Strategy;

import Algebra.Polynomial;

/**
 * BinaryStrategy defines the algorithm for how two terms are combined.
 * This interface is part of the strategy pattern, which defines the
 * concrete implementations of all the operators (AddOp, SubOp, MultOp, DivOp, etc.)
 * used by the calculator. This interface encapsulates a binary operation that shares a
 * common interface. By isolating binary operations in a strategy, we can vary how we
 * compute the operations without changing the parsing or evaluation logic.
 */
public interface BinaryStrategy {
    Polynomial apply(Polynomial a, Polynomial b);
    String getSymbol();
}
