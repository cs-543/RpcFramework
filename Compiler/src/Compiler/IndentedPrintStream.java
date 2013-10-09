package Compiler;

import java.io.PrintStream;

public class IndentedPrintStream {
    private final PrintStream output;
    private int indentLevel;
    private boolean shouldIndent;

    public IndentedPrintStream(PrintStream output) {
        this.output = output;
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
