package Compiler;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

public class Tokenizer {
    private CharBuffer buffer = CharBuffer.allocate(4096);
    private InputStreamReader reader;
    private boolean lastBuffer;

    private int column;
    private int line;

    private StringBuilder wordBuilder;

    public Tokenizer(InputStream stream) {
        reader = new InputStreamReader(stream);
    }

    public Token nextToken() throws Exception {
        char c = nextNonWhitespace();

        switch (c) {
            // Open brace.
            case '{':
                return makeToken(Compiler.Token.TokenType.TT_OPENBRACE);
            case '}':
                return makeToken(Compiler.Token.TokenType.TT_CLOSEBRACE);
            case '[':
                return makeToken(Compiler.Token.TokenType.TT_OPENBRACKET);
            case ']':
                return makeToken(Compiler.Token.TokenType.TT_CLOSEBRACKET);
            case '(':
                return makeToken(Compiler.Token.TokenType.TT_OPENPARENS);
            case ')':
                return makeToken(Compiler.Token.TokenType.TT_CLOSEPARENS);
            case ',':
                return makeToken(Compiler.Token.TokenType.TT_COMMA);
            case ';':
                return makeToken(Compiler.Token.TokenType.TT_SEMICOLON);
            default:
                return parseWord(c);
        }
    }

    private Token makeToken(Compiler.Token.TokenType type) {
        return new Token(line, column, type);
    }

    private Token parseWord(char initial) throws Exception {
        wordBuilder = new StringBuilder();

        char c = initial;
        do {
            wordBuilder.append(c);
            c = nextChar();
        } while (Character.isAlphabetic(c));

        // Put the last read character back
        buffer.put(c);
        column--;

        String word = wordBuilder.toString();
        switch (word) {
            case "interface":
                return makeToken(Compiler.Token.TokenType.TT_INTERFACE);
            case "AtMostOnce":
                return makeToken(Compiler.Token.TokenType.TT_ATMOSTONCE);
            case "AtLeastOnce":
                return makeToken(Compiler.Token.TokenType.TT_ATLEASTONCE);
            case "async":
                return makeToken(Compiler.Token.TokenType.TT_ASYNC);
            case "local":
                return makeToken(Compiler.Token.TokenType.TT_LOCAL);
            case "in":
                return makeToken(Compiler.Token.TokenType.TT_IN);
            case "out":
                return makeToken(Compiler.Token.TokenType.TT_OUT);
            default:
                return new Token(line, column, Compiler.Token.TokenType.TT_IDENTIFIER, word);
        }
    }

    private char nextNonWhitespace() throws Exception {
        char c = nextChar();

        while (Character.isWhitespace(c)) {
            if (c == '\n') {
                line++;
                column = 0;
            }

            c = nextChar();
        }

        return c;
    }

    private char nextChar() throws Exception {
        column++;

        if (!buffer.hasRemaining()) {
            int read = reader.read(buffer);

            if (read < buffer.capacity()) {
                lastBuffer = true;
            }
        }

        return buffer.get();
    }

    private boolean endOfFile() {
        return lastBuffer && buffer.remaining() > 0;
    }
}
