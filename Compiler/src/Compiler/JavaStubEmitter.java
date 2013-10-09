package Compiler;

import java.io.PrintStream;

public class JavaStubEmitter {
    private final IndentedPrintStream output;

    public JavaStubEmitter(PrintStream output) {
        this.output = new IndentedPrintStream(output);
    }

    public void emit(Interface interface_) {
        output.append("public class ");
        output.append(interface_.getName());
        output.append("_Stub {\n");
        output.indent();

        String delimiter = "";
        for (Operation o : interface_.getOperations()) {
            output.append(delimiter);
            delimiter = "\n";

            emit(o);
        }

        output.unindent();
        output.append("}");
    }

    private void emit(Operation operation) {
        output.append(operation.getType());
        output.append(" ");
        output.append(operation.getName());
        output.append("(");

        String delimiter = "";
        for (Parameter p : operation.getParameters()) {
            output.append(delimiter);
            delimiter = ", ";

            emit(p);
        }

        output.append(") {\n");
        output.indent();

        output.append("// body\n");

        output.unindent();
        output.append("}\n");
    }

    private void emit(Parameter parameter) {
        output.append(parameter.getType());
        output.append(" ");
        output.append(parameter.getName());
    }
}
