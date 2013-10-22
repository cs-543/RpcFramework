package Rpc.Compiler;

/**
 * Represents a token.
 */
class Token {
    public enum TokenType {
        // Simple tokens.
        TT_OPENBRACE,
        TT_CLOSEBRACE,
        TT_OPENBRACKET,
        TT_CLOSEBRACKET,
        TT_OPENPARENS,
        TT_CLOSEPARENS,
        TT_COMMA,
        TT_SEMICOLON,

        // Keywords.
        TT_INTERFACE,
        TT_ATMOSTONCE,
        TT_ATLEASTONCE,
        TT_ASYNC,
        TT_LOCAL,
        TT_IN,
        TT_OUT,

        // Identifier.
        TT_IDENTIFIER
    }

    /**
     * Line where this token was parsed.
     */
    private final int line;

    /**
     * Column where this token was parsed.
     */
    private final int column;

    /**
     * Type of token.
     */
    private final TokenType type;

    /**
     * Value of token. Meaningful only for identifiers.
     */
    private final String value;

    /**
     * Initializes a new instance of the Token class.
     *
     * @param line   The line where this Token was parsed.
     * @param column The column where this Token was parsed.
     * @param type   The type of this Token.
     */
    public Token(int line, int column, TokenType type) {
        this(line, column, type, null);
    }

    /**
     * Initializes a new instance of the Token class.
     *
     * @param line   The line where this Token was parsed.
     * @param column The column where this Token was parsed.
     * @param type   The type of this Token.
     * @param value  The value of this Token.
     */
    public Token(int line, int column, TokenType type, String value) {
        this.line = line;
        this.column = column;
        this.type = type;
        this.value = value;
    }

    /**
     * @return The line where this Token was parsed.
     */
    public int getLine() {
        return line;
    }

    /**
     * @return The column where this Token was parsed.
     */
    public int getColumn() {
        return column;
    }

    /**
     * @return The type of this Token.
     */
    public TokenType getType() {
        return type;
    }

    /**
     * @return The value of this Token.
     */
    public String getValue() {
        return value;
    }
}
