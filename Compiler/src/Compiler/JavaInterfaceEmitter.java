package Compiler;

import java.io.OutputStream;

/**
 * Class capable of printing an interface in Java.
 */
public class JavaInterfaceEmitter {
    /**
     * Output stream.
     */
    private final IndentedPrintStream output;

    /**
     * Initializes a new instance of the JavaInterfaceEmitter class.
     *
     * @param output The output stream.
     */
    public JavaInterfaceEmitter(OutputStream output) {
        this.output = new IndentedPrintStream(output);
    }

    /**
     * Emits an interface into the output stream.
     *
     * @param interface_ The interface to emit.
     */
    public void emit(Interface interface_) {
        output.append("public interface ");
        output.append(interface_.getName());
        output.append(" {\n");
        output.indent();

        String delimiter = "";
        for (Operation operation : interface_.getOperations()) {
            output.append(delimiter);
            delimiter = "\n";

            emit(operation);
            output.append(";\n");
        }

        output.unindent();
        output.append("}");
    }

    private void emit(Operation operation) {
        if (operation.isAsync()) {
            emitAsyncOperation(operation);
        } else {
            emitSyncOperation(operation);
        }
    }

    private void emit(Parameter parameter) {
        if (parameter.isOut() && parameter.isIn()) {
            // InOut parameter.
            output.append(TypeBuilder.genericInOutType(parameter.getType()));
        } else if (parameter.isOut()) {
            // Out parameter.
            output.append(TypeBuilder.genericOutType(parameter.getType()));
        } else {
            // In parameter.
            output.append(parameter.getType());
        }

        output.append(" ");
        output.append(parameter.getName());
    }

    private void emitSyncOperation(Operation operation) {
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

    private void emitAsyncOperation(Operation operation) {
        // async operations return void.
        output.append("void ");
        output.append(operation.getName());
        output.append("(");

        for (Parameter p : operation.getParameters()) {
            emit(p);
            output.append(", ");
        }

        // Append callback parameter.
        output.append(TypeBuilder.genericCallbackType(operation.getType()));
        output.append(" callback)");
    }
}
