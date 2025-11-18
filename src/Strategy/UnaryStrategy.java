package Strategy;

import Algebra.Polynomial;

public interface UnaryOp {
    Polynomial apply(Polynomial p);
    String getSymbol();
}
