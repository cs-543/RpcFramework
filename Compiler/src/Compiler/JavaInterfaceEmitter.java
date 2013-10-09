package Compiler;

import java.io.PrintStream;

public class JavaInterfaceEmitter {
    private PrintStream output;

    public JavaInterfaceEmitter(PrintStream output) {
        this.output = output;
    }

    public void emit(Interface interface_) {
        output.append("public interface ");
        output.append(interface_.getName());
        output.append(" {\n");

        for (Operation operation : interface_.getOperations()) {
            output.append("\t");
            emit(operation);
            output.append(";\n");
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

        output.append(")");
    }

    private void emit(Parameter parameter) {
        output.append(parameter.getType());
        output.append(" ");
        output.append(parameter.getName());
    }
}
