package Compiler;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class JavaInterfaceEmitter {
    private static final Map<String, String> wrappers = new HashMap<String, String>();

    static {
        wrappers.put("boolean", "Boolean");
        wrappers.put("byte", "Byte");
        wrappers.put("char", "Character");
        wrappers.put("double", "Double");
        wrappers.put("float", "Float");
        wrappers.put("int", "Integer");
        wrappers.put("long", "Long");
        wrappers.put("short", "Short");
        wrappers.put("void", "Void");
    }

    private final IndentedPrintStream output;

    public JavaInterfaceEmitter(PrintStream output) {
        this.output = new IndentedPrintStream(output);
    }

    public void emit(Interface interface_) {
        output.append("public interface ");
        output.append(interface_.getName());
        output.append(" {\n");
        output.indent();

        for (Operation operation : interface_.getOperations()) {
            emit(operation);
        }

        output.unindent();
        output.append("}");
    }

    private void emit(Operation operation) {
        if (operation.isAsync()) {
            emitAsyncOperation(operation);
            return;
        }

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
        if (parameter.isOut()) {
            emitOutParameter(parameter);
            return;
        }

        output.append(parameter.getType());
        output.append(" ");
        output.append(parameter.getName());
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
        output.append("Callback<");
        output.append(makeWrapper(operation.getType()));
        output.append("> callback);\n");
    }

    private void emitOutParameter(Parameter parameter) {
        if (parameter.isIn()) {
            // in out modifier.
            output.append("InOut<");
        } else {
            // out modifier only.
            output.append("Out<");
        }

        output.append(makeWrapper(parameter.getType()));
        output.append("> ");
        output.append(parameter.getName());
    }

    private String makeWrapper(String type) {
        if (wrappers.containsKey(type)) {
            return wrappers.get(type);
        }

        return type;
    }
}
