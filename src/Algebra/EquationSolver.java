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
public class EquationSolver {
    /**
     * Private constructor for EquationSolver
     */
    private EquationSolver() {}

    /**
     * Solves an equation for x, parser reads the expression string,
     * converts the equation into a polynomial and moves any terms to
     * the left side if any are to the right of "=". Checks the degree of
     * equation, i.e. what type of equation it is,
     * ex degree 2: 2x^2 + 2x + 3 (quadratic)
     * degree 1: 2x + 3 (linear)
     * If degree 1, solves by x = -b / a
     * If degree 2, solves by quadratic formula x = (-b +- sqrt(b^2 - 4ac)) / (2a)
     * If the discriminant is neg, returns complex roots.
     * Only linear and quadratic equations are supported.
     * @param expr expression to evaluate
     * @param addOp addition strategy from strategy pattern
     * @param subOp subtraction strategy from strategy pattern
     * @param mulOp multiplication strategy from strategy pattern
     * @param divOp division strategy from strategy pattern
     * @param negOp unary minus strategy from strategy pattern
     * @param powOp power strategy from strategy pattern
     * @return String solutions for x after solving
     */
    public static String solveForX(
            String expr,
            BinaryStrategy addOp,
            BinaryStrategy subOp,
            BinaryStrategy mulOp,
            BinaryStrategy divOp,
            UnaryStrategy negOp,
            PowStrategy powOp,
            UnaryStrategy rootOp) {

        // Parse the equation and converts it to a polynomial Px = 0
        EquationParser parser =
                new EquationParser(expr, addOp, subOp, mulOp, divOp, negOp, powOp, rootOp);
        Polynomial poly = parser.parse();

        // Check the degree of the polynomial (1 linear, 2 quadratic)
        int deg = poly.degree();

        // If its linear ax + b = 0
        if (deg == 1) {
            // coefficient of x
            int a = poly.getB();
            // constant term
            int b = poly.getC();

            if (a == 0) {
                throw new IllegalArgumentException("Not a valid linear equation in x");
            }

            // x = -b / a
            double x = -b / (double) a;
            return "x = " + x;

        // It its quadratic ax^2 + bx + c = 0
        } else if (deg == 2) {
            // gets terms for a, b, c
            int a = poly.getA();
            int b = poly.getB();
            int c = poly.getC();

            if (a == 0) {
                throw new IllegalArgumentException("Not a valid quadratic equation");
            }

            // discriminant
            double disc = b * b - 4.0 * a * c;

            // If discriminant is negative, calculates complex answers
            if (disc < 0) {
                double real = -b / (2.0 * a);
                double imag = Math.sqrt(-disc) / (2.0 * a);
                return String.format("x = %.4f ± %.4fi", real, imag);
            } else {
                // else calculates real roots
                double sqrtD = Math.sqrt(disc);
                double x1 = (-b + sqrtD) / (2.0 * a);
                double x2 = (-b - sqrtD) / (2.0 * a);
                return String.format("x1 = %.4f, x2 = %.4f", x1, x2);
            }
        }

        // error handling, if anything other than liner or quad return error
        throw new IllegalArgumentException("Only linear and quadratic equations are supported");
    }
}
