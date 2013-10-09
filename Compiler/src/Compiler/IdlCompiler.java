package Compiler;

import java.io.InputStream;

import static Compiler.Token.TokenType.*;

/**
 * Compiles an IDL file
 */
public class IdlCompiler {
    /**
     * Tokenizer providing tokens from the input stream.
     */
    private final Tokenizer tokenizer;

    /**
     * Token being currently analyzed.
     */
    private Token currentToken;

    /**
     * Last recorded identifier.
     */
    private String lastIdentifier;

    /**
     * Compiled interface. Available only when compilation finishes successfully.
     */
    private Interface interface_;

    /**
     * Initializes a new instance of the IdlCompiler class.
     *
     * @param stream The input stream to compile from.
     * @throws Exception
     */
    public IdlCompiler(InputStream stream) throws Exception {
        tokenizer = new Tokenizer(stream);

        // Read first token and parse interface.
        currentToken = tokenizer.nextToken();
        interface_ = nextInterface();
    }

    /**
     * @return The compiled interface. Available only when compilation finishes successfully.
     */
    public Interface getInterface() {
        return interface_;
    }

    /**
     * Parses an interface.
     *
     * @throws Exception
     */
    private Interface nextInterface() throws Exception {
        Interface interface_ = new Interface();

        expect(TT_INTERFACE);

        // Interface name.
        expect(TT_IDENTIFIER);
        interface_.setName(lastIdentifier);

        expect(TT_OPENBRACE);

        // Parse all operations.
        Operation operation;
        while ((operation = nextOperation()) != null) {
            interface_.addOperation(operation);
        }

        expect(TT_CLOSEBRACE);

        return interface_;
    }

    /**
     * Parses an operation.
     *
     * @return The parsed operation or null if no more operation is available.
     * @throws Exception
     */
    private Operation nextOperation() throws Exception {
        Operation operation = new Operation();

        // No more operations.
        if (currentToken.getType() == TT_CLOSEBRACE) {
            return null;
        }

        // Operation policy attribute.
        if (accept(TT_OPENBRACKET)) {
            if (accept(TT_ATMOSTONCE)) {
                operation.setPolicy(ExecutionPolicy.AtMostOnce);
            } else if (accept(TT_ATLEASTONCE)) {
                operation.setPolicy(ExecutionPolicy.AtLeastOnce);
            } else {
                throw new Error("Expected policy");
            }

            expect(TT_CLOSEBRACKET);
        }

        // async and local modifiers.
        operation.setAsync(accept(TT_ASYNC));
        operation.setLocal(accept(TT_LOCAL));

        // Return type.
        expect(TT_IDENTIFIER);
        operation.setType(lastIdentifier);

        // Operation name.
        expect(TT_IDENTIFIER);
        operation.setName(lastIdentifier);

        expect(TT_OPENPARENS);

        // Parse all parameters.
        Parameter parameter;
        boolean firstParam = true;
        while ((parameter = nextParameter(!firstParam)) != null) {
            firstParam = false;
            operation.addParameter(parameter);
        }

        expect(TT_CLOSEPARENS);
        expect(TT_SEMICOLON);

        return operation;
    }

    /**
     * Parses a parameter.
     *
     * @param eatComma Whether a leading comma (from a previous parameter) should be parsed.
     * @return The parsed parameter.
     * @throws Exception
     */
    private Parameter nextParameter(boolean eatComma) throws Exception {
        Parameter parameter = new Parameter();

        // No more parameters.
        if (currentToken.getType() == TT_CLOSEPARENS) {
            return null;
        }

        if (eatComma) {
            expect(TT_COMMA);
        }

        // in and out modifiers.
        parameter.setIn(accept(TT_IN));
        parameter.setOut(accept(TT_OUT));

        // Parameter type.
        expect(TT_IDENTIFIER);
        parameter.setType(lastIdentifier);

        // Parameter name.
        expect(TT_IDENTIFIER);
        parameter.setName(lastIdentifier);

        return parameter;
    }

    /**
     * Advances to the next token if the current token is of a given type.
     *
     * @param type The desired type of the current token.
     * @return True if the current token is of type type, false if not.
     * @throws Exception
     */
    private boolean accept(Token.TokenType type) throws Exception {
        if (currentToken.getType() == type) {
            // Record last identifier value if current token was an identifier.
            if (currentToken.getType() == TT_IDENTIFIER) {
                lastIdentifier = currentToken.getValue();
            }

            currentToken = tokenizer.nextToken();
            return true;
        }

        return false;
    }

    /**
     * Advances to the next token if the current token is of a given type, raises an exception if not.
     *
     * @param type The expected type of the current token.
     * @throws Exception
     */
    private void expect(Token.TokenType type) throws Exception {
        if (accept(type)) {
            return;
        }

        throw new Error(String.format("Syntax error line %d, column %d: expected %s", currentToken.getLine(), currentToken.getColumn(), type.toString()));
    }
}
