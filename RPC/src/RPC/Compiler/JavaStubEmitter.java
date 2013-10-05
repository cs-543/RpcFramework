package Rpc.Compiler;

import java.io.PrintStream;

public class JavaStubEmitter {
    private PrintStream output;

    public JavaStubEmitter(PrintStream output) {
        this.output = output;
    }

    public void emit(Interface interface_) {
        output.append("public class ");
        output.append(interface_.getName());
        output.append("_Stub {\n");

        String delimiter = "";
        for (Operation o: interface_.getOperations()) {
            output.append(delimiter);

            emit(o);
        }

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

        output.append("}");
    }

    private void emit(Parameter parameter) {
        output.append(parameter.getType());
        output.append(" ");
        output.append(parameter.getName());
    }
}
