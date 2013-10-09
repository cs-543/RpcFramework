package Compiler;

import java.io.*;

public class IdlCompiler {
    private static final String INTERFACE_TOKEN = "interface";
    private static final String IDENTIFIER_PATTERN = "[a-zA-Z_][0-9a-zA-Z_]*";
    private static final String POLICY_PATTERN = "\\[(AtLeastOnce)|(AtMostOnce))\\]";

    private Tokenizer tokenizer;
    private Interface interface_;

    public IdlCompiler(InputStream stream) throws Exception {
        tokenizer = new Tokenizer(stream);

        nextInterface();
    }

    public Interface getInterface() {
        return interface_;
    }

    private void nextInterface() throws Exception {
        this.interface_ = new Interface();

        expect(TT_INTERFACE);
        interface_.setName(this.expectIdentifier());

        expect("\\{");

        while (nextOperation());

        expect("\\}");
    }

    private boolean nextOperation() throws Exception {
        Operation operation = new Operation();

        String policy = expect(POLICY_PATTERN);

        if (policy.equals("[AtLeastOnce]")) {
            operation.setPolicy(ExecutionPolicy.AtLeastOnce);
        }



        //operation.setLocal(this.expect("local|remote"));
        operation.setType(expectIdentifier());
        operation.setName(expectIdentifier());
        expect("();");

        interface_.addOperation(operation);
    }

    private void expect(Token.TokenType type) throws Exception {
        Token token = tokenizer.nextToken();

        if (token.getType() != type) {
            syntaxError(token);
        }
    }
}
