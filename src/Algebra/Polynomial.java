package Algebra;

import java.util.ArrayList;
import java.util.List;

public class Polynomial {
    // Coefficient list where index k stores the coefficient of x^k
    private final ArrayList<Integer> term = new ArrayList<>();

    /**
     * Creates a constant polynomial p(x) = c
     * @param c constant value
     * @return polynomial with degree 0
     */
    public static Polynomial constant(int c) {
        Polynomial p = new Polynomial();
        p.set(0, c);
        return p.trim();
    }

    /**
     * Creates the polynomial p(x) = x
     * @return a degree-1 polynomial with coefficient 1 on x
     */
    public static Polynomial x() {
        Polynomial p = new Polynomial();
        p.set(1,1);
        return p;
    }

    /**
     * Constructs a polynomial from a list of coefficients
     * List index k corresponds to coefficient of x^k
     * @param cs list of coefficients
     * @return trimmed polynomial
     */
    public static Polynomial of(List<Integer> cs) {
        Polynomial p = new Polynomial();
        for (int k = 0; k < cs.size(); k++) {
            p.set(k, cs.get(k));
        }

        return p.trim();
    }

    /**
     * Private constructor used internally
     */
    private Polynomial() {}

    /**
     * Returns the zero polynomial p(x) = 0
     */
    public static Polynomial zero() {
        return constant(0);
    }

    /**
     * Retrieves coefficient of x^k
     * Returns 0 if k is out of range
     * @param k exponent
     * @return coefficient at index k
     */
    public int get(int k) {
        return (k < 0 || k >= term.size()) ? 0 : term.get(k);
    }

    /**
     * Computes the degree of the polynomial
     * Degree is the highest exponent with a non-zero coefficient
     * Returns -1 for the zero polynomial
     * @return polynomial degree
     */
    public int degree() {
        for (int i = term.size() - 1; i >= 0; i--) {
            if (term.get(i) != 0) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Adds two polynomials coefficient-wise
     * @param p polynomial to add
     * @return resulting polynomial
     */
    public Polynomial add(Polynomial p) {
        Polynomial curr = new Polynomial();
        int n = Math.max(this.term.size(), p.term.size());
        for (int k = 0; k < n; k++) {
            curr.set(k, this.get(k) + p.get(k));
        }

        return curr.trim();
    }


    /**
     * Sets the coefficient of x^k to value v
     * Expands internal storage if needed
     * @param k exponent index
     * @param v coefficient value
     */
    public void set(int k, int v) {
        while (term.size() <= k) {
            term.add(0);
        }

        term.set(k, v);
    }

    /**
     * Removes trailing zeros from the internal list
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
     * Converts polynomial to human-readable string
     * Example: "3x^2 - x + 5"
     * @return formatted polynomial string
     */
    @Override
    public String toString() {
        int d = degree();
        if (d < 0) return "0";

        StringBuilder sb = new StringBuilder();

        for (int k = d; k >= 0; k--) {
            int c = get(k);
            if (c == 0) continue;

            // Add sign
            if (sb.isEmpty()) {
                if (c < 0) sb.append("-");
            } else {
                sb.append(c < 0 ? " - " : " + ");
            }

            int abs = Math.abs(c);

            // Handle constant or x-power formatting
            if (k == 0) sb.append(abs);
            else if (k == 1) sb.append(abs == 1 ? "x" : abs + "x");
            else sb.append(abs == 1 ? "x^" + k : abs + "x^" + k);
        }
        return sb.toString();
    }

    /**
     * Multiplies two polynomials using coefficient convolution
     * @param o other polynomial
     * @return product polynomial
     */
    public Polynomial mul(Polynomial o) {
        int d1 = this.degree();
        int d2 = o.degree();
        if (d1 < 0 || d2 < 0) {
            return Polynomial.constant(0);
        }

        Polynomial r = Polynomial.constant(0);
        for (int i = 0; i <= d1; i++) {
            int ai = this.get(i);
            if (ai == 0) continue;
            for (int j = 0; j <= d2; j++) {
                int bj = o.get(j);
                if (bj == 0) continue;
                int old = r.get(i + j);
                r.set(i + j, old + ai * bj);
            }
        }
        return r.trim();
    }


    /**
     * Raises the polynomial to a non-negative integer power using
     * fast exponentiation (binary exponentiation)
     * @param e exponent
     * @return polynomial raised to power e
     */
    public Polynomial pow(int e) {
        if (e < 0) {
            throw new IllegalArgumentException("Negative exponent not supported");
        }
        if (e == 0) return Polynomial.constant(1);
        if (e == 1) return this;

        Polynomial base = this;
        Polynomial res  = Polynomial.constant(1);
        int k = e;

        // Binary exponentiation loop
        while (k > 0) {
            if ((k & 1) == 1) {
                res = res.mul(base);
            }
            base = base.mul(base);
            k >>= 1;
        }
        return res;
    }

    /**
     * Checks structural equality of two polynomials
     * Trailing zeros are ignored
     * @param o object to compare
     * @return true if coefficients match
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Polynomial other)) return false;

        // compare after trimming trailing zeros
        int d1 = this.degree();
        int d2 = other.degree();

        int max = Math.max(d1, d2);
        for (int k = 0; k <= max; k++) {
            if (this.get(k) != other.get(k)) return false;
        }
        return true;
    }


    /**
     * Multiplies the polynomial by a constant scalar
     * @param k scalar multiplier
     * @return scaled polynomial
     */
    public Polynomial mulConst(int k) {
        Polynomial r = new Polynomial();
        int d = this.degree();

        if (d < 0) return Polynomial.constant(0);  // zero polynomial

        for (int i = 0; i <= d; i++) {
            r.set(i, this.get(i) * k);
        }

        return r.trim();
    }
}
