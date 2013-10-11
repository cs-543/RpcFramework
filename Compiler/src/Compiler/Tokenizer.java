package Compiler;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static Compiler.Token.TokenType.*;

/**
 * Creates Tokens from a stream of characters.
 */
class Tokenizer {
    /**
     * Map of single letter tokens and their corresponding TokenType.
     */
    private static final Map<Character, Token.TokenType> simpleTokens = new HashMap<Character, Token.TokenType>();

    /**
     * Map of keywords and their corresponding TokenType.
     */
    private static final Map<String, Token.TokenType> keywords = new HashMap<String, Token.TokenType>();

    /**
     * Populate both maps.
     */
    static {
        simpleTokens.put('{', TT_OPENBRACE);
        simpleTokens.put('}', TT_CLOSEBRACE);
        simpleTokens.put('[', TT_OPENBRACKET);
        simpleTokens.put(']', TT_CLOSEBRACKET);
        simpleTokens.put('(', TT_OPENPARENS);
        simpleTokens.put(')', TT_CLOSEPARENS);
        simpleTokens.put(',', TT_COMMA);
        simpleTokens.put(';', TT_SEMICOLON);

        keywords.put("interface", TT_INTERFACE);
        keywords.put("AtMostOnce", TT_ATMOSTONCE);
        keywords.put("AtLeastOnce", TT_ATLEASTONCE);
        keywords.put("async", TT_ASYNC);
        keywords.put("local", TT_LOCAL);
        keywords.put("in", TT_IN);
        keywords.put("out", TT_OUT);
    }

    /**
     * Input stream reader from which this Tokenizer reads.
     */
    private final InputStreamReader reader;

    /**
     * Character buffer, for performance.
     */
    private final char[] buffer = new char[4096];

    /**
     * Current position in buffer.
     */
    private int bufferPosition;

    /**
     * Number of characters in buffer.
     */
    private int inBufferCount;

    /**
     * Current column in file.
     */
    private int column = 1;

    /**
     * Current line in file.
     */
    private int line = 1;

    /**
     * Initializes a new instance of the Tokenizer class.
     *
     * @param stream The stream to tokenize.
     */
    public Tokenizer(InputStream stream) {
        reader = new InputStreamReader(stream);
    }

    /**
     * @return The next available Token, or null if end of stream was reached.
     * @throws Exception
     */
    public Token nextToken() throws Exception {
        char c = nextNonWhitespace();

        // End of file reached.
        if (c == '\0') {
            return null;
        }

        // Simple token.
        if (simpleTokens.containsKey(c)) {
            return makeToken(simpleTokens.get(c));
        }

        // Keyword or identifier start.
        if (Character.isJavaIdentifierStart(c)) {
            return parseWord(c);
        }

        throw new Error(String.format("Parse error line %d, column %d: unexpected `%c`", line, column, c));
    }

    /**
     * Builds a Token of a given type.
     *
     * @param type The type of the Token to create.
     * @return The newly created Token.
     */
    private Token makeToken(Token.TokenType type) {
        return new Token(line, column, type);
    }

    /**
     * Parses a word (keyword or identifier)
     *
     * @param firstChar First character of the word.
     * @return The parsed word.
     * @throws Exception
     */
    private Token parseWord(char firstChar) throws Exception {
        StringBuilder wordBuilder = new StringBuilder();
        int firstCharColumn = column;

        // Read all alphanumeric characters into the word builder.
        char c = firstChar;
        do {
            wordBuilder.append(c);
            c = nextChar();
        } while (Character.isJavaIdentifierPart(c));

        // Put the last read character back because we read one too much.
        bufferPosition--;
        column--;

        String word = wordBuilder.toString();

        // Keyword.
        if (keywords.containsKey(word)) {
            return makeToken(keywords.get(word));
        }

        // Identifier.
        return new Token(line, firstCharColumn, TT_IDENTIFIER, word);
    }

    /**
     * @return The next non whitespace character in the stream.
     * @throws Exception
     */
    private char nextNonWhitespace() throws Exception {
        char c = nextChar();

        while (Character.isWhitespace(c)) {
            if (c == '\n') {
                // Increase line count and reset column caret.
                line++;
                column = 0;
            }

            c = nextChar();
        }

        return c;
    }

    /**
     * @return The next character in the stream.
     * @throws Exception
     */
    private char nextChar() throws Exception {
        if (bufferPosition == inBufferCount) {
            inBufferCount = reader.read(buffer);
        }

        column++;
        return buffer[bufferPosition++];
    }
}
