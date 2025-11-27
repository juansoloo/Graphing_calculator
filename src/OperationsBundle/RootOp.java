package OperationsBundle;

import Algebra.Polynomial;

public class RootOp implements UnaryStrategy {

    @Override
    public Polynomial apply(Polynomial p) {
        // only constants allowed
        if (p.degree() != 0) {
            throw new IllegalArgumentException("Square Root only supports integer constants");
        }

        int value = p.get(0);

        // root of negative constant is not allowed
        if (value < 0) {
            throw new IllegalArgumentException("Cannot solve square root of negative values");
        }

        int root = (int) Math.sqrt(value);

        // error checking, confirms is a square root
        if (root * root != value) {
            throw new IllegalArgumentException("Square root is not integer");
        }

        return Polynomial.constant(root);
    }

    @Override
    public String getSymbol() {
        return "√";
    }
}
