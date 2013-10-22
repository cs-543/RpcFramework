package Rpc.Compiler;

import java.io.OutputStream;

/**
 * Class capable of printing an interface in IDL.
 */
public class IdlEmitter extends Emitter {
    /**
     * Initializes a new instance of the IdlEmitter class.
     *
     * @param output The output stream.
     */
    public IdlEmitter(OutputStream output) {
        super(output);
    }

    /**
     * Emits an interface into the output stream.
     *
     * @param interface_ The interface to emit.
     * @return Returns itself for method chaining.
     */
    public IdlEmitter emit(Interface interface_) {
        // Begin of interface.
        output.append("interface ");
        output.append(interface_.getName());
        output.append(" {\n");

        // Emit all operations.
        String delimiter = "";
        for (Operation o : interface_.getOperations()) {
            output.append(delimiter);
            delimiter = "\n";
            emit(o);
        }

        // End of interface.
        output.append("}");

        return this;
    }

    /**
     * Emits an operation into the output stream.
     *
     * @param operation The operation to emit.
     */
    private void emit(Operation operation) {
        // Emit execution policy.
        output.append("\t[");
        output.append(operation.getPolicy().toString());
        output.append("]\n\t");

        // Emit async modifier, if any.
        output.append(operation.isAsync() ? "async " : "");

        // Emit local or remote modifier.
        output.append(operation.isLocal() ? "local " : "remote ");

        // Emit operation return type and name.
        output.append(operation.getType());
        output.append(" ");
        output.append(operation.getName());
        output.append("(");

        // Emit all parameters.
        String delimiter = "";
        for (Parameter p : operation.getParameters()) {
            output.append(delimiter);
            delimiter = ", ";

            emit(p);
        }

        output.append(");\n");
    }

    /**
     * Emits a parameter into the output stream.
     *
     * @param parameter The parameter to emit.
     */
    private void emit(Parameter parameter) {
        // Emit ref modifier, if any.
        output.append(parameter.isRef() ? "ref " : "");

        // Emit in modifier, if any.
        output.append(parameter.isIn() ? "in " : "");

        // Emit out modifier, if any.
        output.append(parameter.isOut() ? "out " : "");

        // Emit parameter type and name.
        output.append(parameter.getType());
        output.append(" ");
        output.append(parameter.getName());
    }
}
