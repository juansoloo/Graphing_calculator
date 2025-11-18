package Strategy;

import Algebra.Polynomial;

public interface BinaryOp {
    Polynomial apply(Polynomial a, Polynomial b);
    String getSymbol();
}
