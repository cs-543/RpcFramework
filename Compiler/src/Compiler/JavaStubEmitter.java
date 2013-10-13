package Compiler;

import java.io.OutputStream;

public class JavaStubEmitter extends Emitter {
    public JavaStubEmitter(OutputStream output) {
        super(output);
    }

    public JavaStubEmitter emit(Interface interface_) {
        output.append("import Rpc.Call;\n\n");

        output.append("public class ");
        output.append(interface_.getName());
        output.append("_Stub {\n");
        output.indent();

        String delimiter = "";
        for (Operation o : interface_.getOperations()) {
            output.append(delimiter);
            delimiter = "\n";

            emit(o);
            output.append("\n");
        }

        output.unindent();
        output.append("}");

        return this;
    }

    private void emit(Operation operation) {
        // Policy.
        output.append("@");
        output.append(operation.getPolicy().toString());
        output.append("\n");

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

        output.append(") {\n");
        output.indent();

        output.append("Call c = new Call(\"");
        output.append(operation.getName());
        output.append("\"");

        for (Parameter p : operation.getParameters()) {
            output.append(", ");
            output.append(p.getName());
        }

        output.append(");\n");

        if (!operation.getType().equals("void")) {
            output.append("return ");
        }

        output.append(TypeBuilder.genericType("invoke", operation.getType()));
        output.append("(call);\n");

        output.unindent();
        output.append("}");
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
        output.append(" callback) {\n");
        output.indent();

        // Body.
        output.append("Call c = new Call(\"");
        output.append(operation.getName());
        output.append("\"");

        for (Parameter p : operation.getParameters()) {
            output.append(", ");
            output.append(p.getName());
        }

        output.append(");\n");
        output.append("invokeAsync(call, callback);\n");

        output.unindent();
        output.append("}");
    }
}
