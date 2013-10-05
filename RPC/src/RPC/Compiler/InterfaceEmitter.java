package Rpc.Compiler;

import java.io.PrintStream;

public class InterfaceEmitter {
    private PrintStream output;

    public InterfaceEmitter(PrintStream output) {
        this.output = output;
    }

    public void emit(Interface interface_) {
        output.append("interface ");
        output.append(interface_.getName());
        output.append(" {\n");

        for (Operation o : interface_.getOperations()) {
            output.append("\t");
            emit(o);
            output.append(";\n");
        }

        output.append("}");
    }

    private void emit(Operation operation) {
        output.append(operation.getLocality());
        output.append(" ");
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
        if (parameter.isRef()) {
            output.append("ref ");
        }

        if (parameter.isIn() && parameter.isOut()) {
            output.append("inout ");
        } else {
            if (parameter.isIn()) {
                output.append("in ");
            }

            if (parameter.isOut()) {
                output.append("out ");
            }
        }

        output.append(parameter.getType());
        output.append(" ");
        output.append(parameter.getName());
    }
}
