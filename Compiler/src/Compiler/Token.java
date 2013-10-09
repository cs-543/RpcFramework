package Compiler;

public class Token {
    public enum TokenType {
        TT_OPENBRACE,
        TT_CLOSEBRACE,
        TT_OPENBRACKET,
        TT_CLOSEBRACKET,
        TT_OPENPARENS,
        TT_CLOSEPARENS,
        TT_COMMA,
        TT_SEMICOLON,

        // Keywords
        TT_INTERFACE,
        TT_ATMOSTONCE,
        TT_ATLEASTONCE,
        TT_ASYNC,
        TT_LOCAL,
        TT_IN,
        TT_OUT,

        // Identifier
        TT_IDENTIFIER
    }

    private int line;
    private int column;
    private TokenType type;
    private String value;

    public Token(int line, int column, TokenType type) {
        this(line, column, type, null);
    }

    public Token(int line, int column, TokenType type, String value) {
        this.line = line;
        this.column = column;
        this.type = type;
        this.value = value;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
