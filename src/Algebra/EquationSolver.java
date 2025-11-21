package Algebra;

import Parser.EquationParser;
import Strategy.BinaryStrategy;
import Strategy.UnaryStrategy;
import Strategy.PowStrategy;

/**
 * Solves equations of the form fx = gx for x by
 * parsing to a Polynomial Px = fx - gx and then
 * applying linear / quadratic formulas.
 */
public final class EquationSolver {

    private EquationSolver() {}

    public static String solveForX(
            String expr,
            BinaryStrategy addOp,
            BinaryStrategy subOp,
            BinaryStrategy mulOp,
            BinaryStrategy divOp,
            UnaryStrategy  negOp,
            PowStrategy    powOp) {

        EquationParser parser =
                new EquationParser(expr, addOp, subOp, mulOp, divOp, negOp, powOp);

        Polynomial poly = parser.parse(); // represents LHS - RHS = 0
        int deg = poly.degree();

        if (deg == 1) {
            int a = poly.getB();
            int b = poly.getC();
            if (a == 0) {
                throw new IllegalArgumentException("Not a valid linear equation in x");
            }
            double x = -b / (double) a;
            return "x = " + x;

        } else if (deg == 2) {
            int a = poly.getA();
            int b = poly.getB();
            int c = poly.getC();
            if (a == 0) {
                throw new IllegalArgumentException("Not a valid quadratic equation");
            }

            double disc = b * b - 4.0 * a * c;
            if (disc < 0) {
                double real = -b / (2.0 * a);
                double imag = Math.sqrt(-disc) / (2.0 * a);
                return String.format("x = %.4f ± %.4fi", real, imag);
            } else {
                double sqrtD = Math.sqrt(disc);
                double x1 = (-b + sqrtD) / (2.0 * a);
                double x2 = (-b - sqrtD) / (2.0 * a);
                return String.format("x1 = %.4f, x2 = %.4f", x1, x2);
            }
        }

        throw new IllegalArgumentException("Only linear and quadratic equations are supported");
    }
}
