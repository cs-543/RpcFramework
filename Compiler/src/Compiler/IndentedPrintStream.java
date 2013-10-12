package Compiler;

import java.io.OutputStream;
import java.io.PrintStream;

class IndentedPrintStream {
    private final PrintStream output;
    private int indentLevel;
    private boolean shouldIndent;

    public IndentedPrintStream(OutputStream output) {
        this.output = new PrintStream(output);
    }

    public void close() {
        output.close();
    }

    public void append(String str) {
        if (shouldIndent) {
            for (int i = 0; i < indentLevel; ++i) {
                output.append("    ");
            }
        }

        shouldIndent = str.endsWith("\n");
        output.append(str);
    }

    public void indent() {
        indentLevel++;
    }

    public void unindent() {
        indentLevel--;
    }
}
