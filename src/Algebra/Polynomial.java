package Algebra;

import java.util.ArrayList;
import java.util.List;

public class Polynomial {
    // Coefficient list where index k stores the coefficient of x^k
    private final ArrayList<Integer> term = new ArrayList<>();

    /**
     * Private constructor used internally
     */
    private Polynomial() {}

    /**
     * Creates a constant polynomial fx = c
     * @param c constant value
     * @return polynomial with degree 0
     */
    public static Polynomial constant(int c) {
        Polynomial p = new Polynomial();
        p.set(0, c);
        return p.trim();
    }

    /**
     * Creates the polynomial fx = x, degree-1 polynomial
     * with coefficient 1 for x.
     * @return Polynomial x
     */
    public static Polynomial x() {
        Polynomial p = new Polynomial();
        p.set(1,1);
        return p;
    }

    /**
     * Builds a polynomial from a list of coefficients.
     * Each list index k represents the coefficient of x^k.
     * @param cs list of coefficients in increasing order
     * @return polynomial
     */
    public static Polynomial of(List<Integer> cs) {
        Polynomial p = new Polynomial();
        for (int k = 0; k < cs.size(); k++) {
            p.set(k, cs.get(k));
        }

        return p.trim();
    }

    /**
     * Returns the zero polynomial fx = 0
     */
    public static Polynomial zero() {
        return constant(0);
    }

    /**
     * Retrieves coefficient of x^k
     * Returns 0 if k is out of range
     * @param k exponent
     * @return int coefficient at index k
     */
    public int get(int k) {
        if (k >= 0 && k < term.size()) {
            return term.get(k);
        }
        return 0;
    }

    /**
     * Sets the coefficient of x^k to value v
     * Expands internal storage if needed
     * @param k exponent index
     * @param v coefficient value
     */
    public void set(int k, int v) {
        // expand list with zeros until index k is available
        while (term.size() <= k) {
            term.add(0);
        }

        // set coefficient at index k
        term.set(k, v);
    }

    /**
     * Finds the degree of the polynomial.
     * The degree is the highest power of x with a non-zero coefficient.
     * If every coefficient is zero, returns -1 to indicate a zero polynomial.
     * @return the degree of the polynomial
     */
    public int degree() {
        // loop from the highest deg to lowest
        for (int i = term.size() - 1; i >= 0; i--) {
            if (term.get(i) != 0) {
                return i;
            }
        }

        // all coefficients are zero, so the polynomial has no degree
        return -1;
    }

    /**
     * Removes trailing zeros from the list
     * @return this polynomial after trimming
     */
    private Polynomial trim() {
        int i = term.size() - 1;

        // Find last non-zero coefficient
        while(i >= 0 && term.get(i) == 0) {
            i--;
        }

        // Remove all coefficients beyond last non-zero index
        while(term.size() - 1 > i) {
            term.remove(term.size() - 1);
        }

        return this;
    }

    /**
     * Adds a value to the coefficient of x^k in the given polynomial.
     * Used during multiplication to accumulate partial products.
     * @param p polynomial whose coefficient is being updated
     * @param k power of x index in coefficient list
     * @param delta amount to add to the existing coefficient
     */
    private void addToCoeff(Polynomial p, int k, int delta) {
        p.set(k, p.get(k) + delta);
    }

    /**
     * Helper method to get a in ax^2 + bx + c
     * @return coefficient of ax^2
     */
    public int getA() {
        return get(2);
    }

    /**
     * Helper method to get b in ax^2 + bx + c
     * @return coefficient of bx
     */
    public int getB() {
        return get(1);
    }

    /**
     * Helper method to get c in ax^2 + bx + c
     * @return coefficient of c
     */
    public int getC() {
        return get(0);
    }

    /**
     * Adds two polynomials coefficient-wise and stores result
     * @param p polynomial to add
     * @return resulting polynomial
     */
    public Polynomial add(Polynomial p) {
        Polynomial curr = new Polynomial();

        // number of coefficients to check
        int n = Math.max(this.term.size(), p.term.size());

        // add coefficients a_k + b_k for all k
        for (int k = 0; k < n; k++) {
            curr.set(k, this.get(k) + p.get(k));
        }

        return curr.trim();
    }

    /**
     * Multiplies the polynomial by a constant scalar
     * @param k scalar multiplier
     * @return scaled polynomial
     */
    public Polynomial mulConst(int k) {
        // reset Polynomial
        Polynomial r = new Polynomial();

        // loop through all coefficients and scale them
        for (int i = 0; i <= degree(); i++) {
            r.set(i, get(i) * k);
        }

        return r.trim();
    }

    /**
     * Multiplies two polynomials using coefficient convolution
     * @param o other polynomial
     * @return product polynomial
     */
    public Polynomial mul(Polynomial o) {
        // degrees of each polynomial
        int d1 = this.degree();
        int d2 = o.degree();

        // if either polynomial is zero, result is zero
        if (d1 < 0 || d2 < 0) {
            return Polynomial.constant(0);
        }

        // stores the result of the multiplication
        Polynomial result = Polynomial.constant(0);

        // loops through coefficients of first polynomial
        for (int i = 0; i <= d1; i++) {
            int ai = this.get(i);

            // skip if term is zero
            if (ai == 0) {
                continue;
            }

            // loops through coefficients of second polynomial
            for (int j = 0; j <= d2; j++) {
                int bj = o.get(j);

                // skip if term is zero
                if (bj == 0) {
                    continue;
                }

                // i + j is the resulting power of x
                // add contribution ai * bj to the coefficient of x^(i+j)
                addToCoeff(result, i + j, ai * bj);
            }
        }
        // removes trailing zeros
        return result.trim();
    }

    /**
     * Raises the polynomial to a non-negative integer power using
     * simple repeated multiplication. Multiply the polynomial by
     * itself e times.
     * @param e exponent
     * @return polynomial raised to power e
     */
    public Polynomial pow(int e) {
        // negative exponents not supported
        if (e < 0) {
            throw new IllegalArgumentException("Negative exponent not supported");
        }

        // returns constant if exponent is 0
        if (e == 0) {
            return Polynomial.constant(1);
        }

        // start with this polynomial
        Polynomial result = this;

        // multiply e - 1 times
        for (int i = 1; i < e; i++) {
            result = result.mul(this);
        }

        return result;
    }

    /**
     * Evaluates the entire polynomial at a specific x-value.
     * Computes the sum of all terms
     * @param x  the value to plug into polynomial
     * @return int the numeric result of solving polynomial at x
     */
    public double evaluate(double x) {
        double sum = 0.0;

        // loop through each coefficient and compute a_k * x^k
        for (int k = 0; k < term.size(); k++) {
            int coeff = term.get(k);
            sum += coeff * Math.pow(x, k);
        }
        return sum;
    }

    /**
     * Converts polynomial to human readable string
     * Ex: "3x^2 - x + 5"
     * @return formatted polynomial string
     */
    @Override
    public String toString() {
        int d = degree();
        if (d < 0) return "0";

        StringBuilder sb = new StringBuilder();

        // loops through highest degree down to 0, ensuring expression in print in std form
        for (int k = d; k >= 0; k--) {

            // coefficient
            int c = get(k);

            // skips and continues to next term if degree is 0
            if (c == 0) continue;

            boolean firstTerm;

            // checks if first term has been added
            if (sb.length() == 0) {
                firstTerm = true;
            } else {
                firstTerm = false;
            }

            // absolut value of coefficient
            int abs = Math.abs(c);

            // adds correct sign to string
            if (firstTerm) {
                if (c < 0) sb.append("-");
            } else {
                sb.append(c < 0 ? " - " : " + ");
            }

            // builds the term depending on the power of x
            if (k == 0) {
                // Constant term
                sb.append(abs);
            } else {
                // x or x^k term, prints coefficient only if not 1
                if (abs != 1) {
                    sb.append(abs);
                }
                sb.append("x");

                // add exponent if exists
                if (k != 1) {
                    sb.append("^").append(k);
                }
            }
        }

        return sb.toString();
    }
}
