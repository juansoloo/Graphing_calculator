package Parser;

/**
 * A lexer class to take string input and separate it
 * into individual tokens with a defined type. Removes whitespace and scans
 * characters one by one, producing typed tokens. The parser uses this class to
 * read input without dealing with the characters itself.
 */
public class Lexer {
    private final String str;
    private int i = 0;

    /**
     * Token types used by the parser. Each enum value represents a
     * distinct lexical unit which are integers, variables, operators,
     * parentheses, or end-of-input.
     */
    public enum TOK {
        INT,
        X,
        PLUS,
        MINUS,
        MUL,
        DIV,
        POWER,
        ROOT,
        LEFT_PAREN,
        RIGHT_PAREN,
        EOF
    }

    /**
     * The Token class associates the token type and the optional integer value.
     * Integer tokens store their numeric value and all others store 0
     */
    public static class Token {
        public final TOK tok; // token (number, operator, (), eof, etc.)
        public final int val;

        /**
         * Constructor for Tokens that are integers
         * @param tok token type
         * @param val token value
         */
        public Token(TOK tok, int val) {
            this.tok = tok;
            this.val = val;
        }

        /**
         * Constructor for Tokens that are not integers
         * @param tok Token type
         */
        public Token(TOK tok) {
            this(tok, 0);
        }

        /**
         * Pretty prints tokens that are integers as INT(x)
         * @return the string representation of the token as INT(x)
         */
        @Override
        public String toString() {
            return tok == TOK.INT ? "INT(" + val + ")" : tok.name();
        }
    }

    /**
     * Constructs the lexer by removing all whitespaces to prepare for scanning
     * the expression from left to right.
     * @param src input expression type by user
     */
    public Lexer(String src) {
        this.str = src.replaceAll("\\s+", "");
    }

    /**
     * Scans the next token in the input, handles multi digit integers, operators,
     * parentheses, and EOF.
     * @return the next token in the sequence
     */
    public Token next() {
        // checks if current index is greater than the length of expression and returns EOF if true
        if (i >= str.length()) {
            return new Token(TOK.EOF);
        }

        // current character at index i
        char c = str.charAt(i);

        // checks if c is a digit
        if (Character.isDigit(c)) {
            // uses j as an index variable to scan ahead in the string
            int j;
            // for-loop has no body, it advances j until it reaches the first non-digit character
            for (j=i; j < str.length() && Character.isDigit(str.charAt(j)); j++);
            // parses digits from index i to j, casts as integer
            int value = Integer.parseInt(str.substring(i, j));
            // advances i to j so the next scan continues after this integer
            i = j;
            // returns the value and the INT token type
            return new Token(TOK.INT, value);
        }

        // increments index i
        i++;

        // switch statement to return Token type depending on character
        return switch (c) {
            case 'x','X' -> new Token(TOK.X);
            case '+' -> new Token(TOK.PLUS);
            case '-' -> new Token(TOK.MINUS);
            case '*' -> new Token(TOK.MUL);
            case '/' -> new Token(TOK.DIV);
            case '^' -> new Token(TOK.POWER);

            // maybe not do sqrts or implement logic to do by itself no other terms
            case 'R' -> new Token(TOK.ROOT);
            case '(' -> new Token(TOK.LEFT_PAREN);
            case ')' -> new Token(TOK.RIGHT_PAREN);

            default -> throw new IllegalStateException("Unexpected value: " + c);
        };
    }

//    public static void main(String[] args) {
//        Lexer lex = new Lexer("(7x + 9) + 2x^2 + 2x + 9");
//        Lexer.Token t;
//        while ((t = lex.next()).tok != Lexer.TOK.EOF) {
//            System.out.println(t);
//        }
//    }
 }

