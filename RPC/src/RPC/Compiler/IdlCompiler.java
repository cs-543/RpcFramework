package Rpc.Compiler;

import java.io.InputStream;
import java.util.Scanner;

public class IdlCompiler {
    private static final String INTERFACE_TOKEN = "interface";
    private static final String IDENTIFIER_PATTERN = "[a-zA-Z_][0-9a-zA-Z_]*";
    private Scanner scanner;
    private Interface anInterface;

    public IdlCompiler(InputStream stream) throws Exception {
        this.scanner = new Scanner(stream);


        this.nextInterfaceDeclaration();
        this.nextInterfaceBody();
    }

    public Interface getAnInterface() {
        return this.anInterface;
    }

    private void nextInterfaceDeclaration() throws Exception {
        Interface anInterface = new Interface();

        this.expect(INTERFACE_TOKEN);
        anInterface.setName(this.expectIdentifier());

        this.anInterface = anInterface;
    }

    private void nextInterfaceBody() throws Exception {
        this.expect("\\{");

        while (this.hasNextOperation()) {
            this.nextOperation();
        }

        this.expect("\\}");
    }

    private boolean hasNextOperation() {
        return this.scanner.hasNext("local|remote");
    }

    private void nextOperation() throws Exception {
        Operation operation = new Operation();

        operation.setLocality(this.expect("local|remote"));
        operation.setType(this.expectIdentifier());
        operation.setName(this.expectIdentifier());
        this.expect("();");

        this.anInterface.addOperation(operation);
    }

    private String expect(String pattern) throws Exception {
        if (!this.scanner.hasNext(pattern)) {
            throw new Exception("Expected " + pattern);
        }

        return this.scanner.next();
    }

    private String expectIdentifier() throws Exception {
        String identifier = null;

        try {
            identifier = this.expect(IDENTIFIER_PATTERN);
        } catch (Exception e) {
            throw new Exception("Expected identifier");
        }

        return identifier;
    }
}
