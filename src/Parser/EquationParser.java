package Parser;

import Algebra.Polynomial;
import Parser.Lexer.TOK;
import Strategy.BinaryStrategy;
import Strategy.UnaryStrategy;
import Strategy.PowStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * A recursive decent parser for algebraic expressions. Consists
 * of integers, variable x, parentheses, unary minus, and the
 * binary operators (+, -, *, /, ^). The parser creates Polynomial
 * objects using injected strategy classes (addition, subtraction,
 * multiplication, division, negation, exponentiation). The parser
 * first tokenizes the input using the Lexer, then uses a pointer
 * p to walk through the token list.
 */
public class EquationParser {
    // input expression
    private final String src;
    // token buffer containing token stream
    private final List<Lexer.Token> toks = new ArrayList<>();
    // current position in toks
    private int p = 0;

    // Operator strategies from Strategy
    private final BinaryStrategy addOp;
    private final BinaryStrategy subOp;
    private final BinaryStrategy mulOp;
    private final BinaryStrategy divOp;
    private final UnaryStrategy  negOp;
    private final PowStrategy    powOp;

    /**
     * Constructor for parser, parsers for an expression
     * @param source Input string
     * @param addOp strategy for addition
     * @param subOp strategy for subtraction
     * @param mulOp strategy for multiplication
     * @param divOp strategy for division
     * @param negOp strategy for unary minus
     * @param powOp strategy for exponentiation
     */
    public EquationParser(String source,
                          BinaryStrategy addOp,
                          BinaryStrategy subOp,
                          BinaryStrategy mulOp,
                          BinaryStrategy divOp,
                          UnaryStrategy negOp,
                          PowStrategy powOp) {
        this.src   = source;
        this.addOp = addOp;
        this.subOp = subOp;
        this.mulOp = mulOp;
        this.divOp = divOp;
        this.negOp = negOp;
        this.powOp = powOp;
    }

    /**
     * Parsers the top level input, if string contains "=", parser interprets
     * it as an equation Left = Right and returns Left - Right
     * @return a Polynomial representing the left side minus the right side
     */
    public Polynomial parse() {
        String s = src.replaceAll("\\s+", "");
        int eq = s.indexOf('=');

        // If the expression has "=", parse both sides separately
        if (eq >= 0) {
            Polynomial L = parseSide(s.substring(0, eq));
            Polynomial R = parseSide(s.substring(eq + 1));
            return subOp.apply(L, R);
        } else {
            // pure expression no "="
            return parseSide(s);
        }
    }

    /**
     * Tokenizes a single side of an equation, then parses it using the
     * algorithm defined by parser.
     * @param expr
     * @return
     */
    private Polynomial parseSide(String expr) {
        toks.clear();
        p = 0;

        // Creates a fresh lexer for substring
        Lexer lex = new Lexer(expr);

        // Scans all token until EOF
        for (;;) {
            Lexer.Token t = lex.next();
            toks.add(t);
            if (t.tok == TOK.EOF) break;
        }

        // Parses full expression and ensures no trailing strings
        Polynomial result = parseSum();
        need(TOK.EOF, "Unexpected trailing input");
        return result;
    }

    /**
     * Parses a sum expression, handles addition and subtraction by parsing
     * an initial term followed by zero or more "+" or "-" pairs.
     * @return the Polynomial after applying all additions and subtractions
     */
    private Polynomial parseSum() {
        Polynomial acc = parseTerm();

        // Continues parsing as long as next token is + or -
        while (at(TOK.PLUS) || at(TOK.MINUS)) {
            boolean plus = at(TOK.PLUS);

            // consumes + or -
            eat();

            Polynomial rhs = parseTerm();

            // if plus, apply addOp, else subOp
            acc = plus ? addOp.apply(acc, rhs)
                    : subOp.apply(acc, rhs);
        }
        return acc;
    }

    /**
     * Parses a term expression. A term is a factor followed by zero or more
     * multiplications or divisions, including implicit multiplication.
     * @return the resulting Polynomial after evaluating the term
     */
    private Polynomial parseTerm() {
        // Parse the initial factor of the term
        Polynomial acc = parseFactor();

        // Consumes factors as long as a multiplication condition holds
        while (true) {
            // Explicit multiplication "*"
            if (at(TOK.MUL)) {
                eat();
                Polynomial rhs = parseFactor();
                acc = mulOp.apply(acc, rhs);

            // Explicit division "/"
            } else if (at(TOK.DIV)) {
                eat();
                Polynomial rhs = parseFactor();
                acc = divOp.apply(acc, rhs);

            // Implicit multiplication (ie 2x, x(x+1), etc.)
            } else if (startsImplicitMult()) {
                Polynomial rhs = parseFactor();
                acc = mulOp.apply(acc, rhs);
            } else {
                break;
            }
        }
        return acc;
    }

    /**
     * Parses a factor. A factor may begin with one or more unary minus signs,
     * followed by a primary expression, and optionally a power operation.
     * @return the resulting Polynomial after applying negation and exponentiation
     */
    private Polynomial parseFactor() {
        // Tracks whether the factor is negated; multiple "-" flip the sign
        boolean neg = false;

        // Consume all unary minus tokens (e.g., --x, ---5)
        while (at(TOK.MINUS)) {
            // flip sign for each minus
            neg = !neg;
            eat();
        }

        // Parse the underlying primary expression (number, variable, or parenthesized expression)
        Polynomial base = parsePrimary();

        // Handle optional exponentiation: base "^" integer
        if (at(TOK.POWER)) {
            eat();
            need(TOK.INT, "Exponent must be integer");

            // exponent value
            int e = eat().val;
            base = powOp.apply(base, e);
        }

        // Apply unary negation if an odd number of "-" was found
        return neg ? negOp.apply(base) : base;
    }

    /**
     * Parses a primary expression. A primary is one of the following:
     *   - an integer constant
     *   - the variable x
     *   - a subexpression enclosed in parentheses
     * @return the Polynomial representation of the primary
     */
    private Polynomial parsePrimary() {
        // Integer literal, return constant polynomial
        if (at(TOK.INT)) {
            int v = eat().val;
            return Polynomial.constant(v);
        }

        // Variable "x" return polynomial representing x
        if (at(TOK.X)) {
            eat();
            return Polynomial.x();
        }

        // expression enclosed in parentheses, parse recursively as a full sum
        if (at(TOK.LEFT_PAREN)) {
            // consumes "("
            eat();
            // parses inner expression
            Polynomial inside = parseSum();
            need(TOK.RIGHT_PAREN, "Missing ')'");
            //consumes ")"
            eat();
            return inside;
        }
        throw new IllegalArgumentException("Primary expected at: " + peek());
    }

    /**
     * Determines whether implicit multiplication should happen.
     * Implicit multiplication happens when a factor begins without an
     * explicit operator, such as 2x, (x+1)(x-2), or x2.
     * @return true if the next token can start a factor, indicating
     * multiplication should be assumed
     */
    private boolean startsImplicitMult() {
        // Looks ahead at the next token type
        TOK t = peek().tok;

        // Implicit multiplication is allowed when the next token
        // starts a primary expression: INT, X, or '('
        return t == TOK.INT || t == TOK.X || t == TOK.LEFT_PAREN;
    }

    /**
     * Returns the current look ahead token without consuming it
     * @return the token at the current position p
     */
    private Lexer.Token peek() {
        return toks.get(p);
    }

    /**
     * Consumes and returns the current token, advancing the parser position
     * @return the token at position p before incrementing
     */
    private Lexer.Token eat()  {
        return toks.get(p++);
    }

    /**
     * Checks whether the current token matches the given token type
     * @param t the token type to compare against
     * @return true if the current token has type t
     */
    private boolean at(TOK t)  {
        // Compare the look ahead token's type with the expected type
        return peek().tok == t;
    }

    /**
     * Ensures that the current token matches the expected type. If not,
     * an error is thrown with the provided message and the unexpected token.
     * @param t the required token type
     * @param msg the error message to emit if the check fails
     */
    private void need(TOK t, String msg) {
        if (!at(t)) throw new IllegalArgumentException(msg + " (found " + peek() + ")");
    }
}
