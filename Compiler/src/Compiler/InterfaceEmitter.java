package Compiler;

import java.io.PrintStream;

public class InterfaceEmitter {
    private final PrintStream output;

    public InterfaceEmitter(PrintStream output) {
        this.output = output;
    }

    public void emit(Interface interface_) {
        output.append("interface ");
        output.append(interface_.getName());
        output.append(" {\n");

        String delimiter = "";
        for (Operation o : interface_.getOperations()) {
            output.append(delimiter);
            delimiter = "\n";
            emit(o);
        }

        output.append("}");
    }

    private void emit(Operation operation) {
        output.append("\t[");
        output.append(operation.getPolicy().toString());
        output.append("]\n\t");

        output.append(operation.isAsync() ? "async " : "");
        output.append(operation.isLocal() ? "local " : "remote ");
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

        output.append(");\n");
    }

    private void emit(Parameter parameter) {
        if (parameter.isRef()) {
            output.append("ref ");
        }

        if (parameter.isIn()) {
            output.append("in ");
        }

        if (parameter.isOut()) {
            output.append("out ");
        }

        output.append(parameter.getType());
        output.append(" ");
        output.append(parameter.getName());
    }
}
