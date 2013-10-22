package Rpc.Compiler;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Simple wrapper over PrintStream that adds indentation capabilities.
 */
class IndentedPrintStream {
    /**
     * Wrapped PrintStream.
     */
    private final PrintStream output;

    /**
     * Current indentation level.
     */
    private int indentLevel;

    /**
     * Indicates whether indentation should be printed at the next append.
     */
    private boolean shouldIndent;

    /**
     * Initializes a new instance of the IndentedPrintStream class.
     *
     * @param output The PrintStream to wrap.
     */
    public IndentedPrintStream(OutputStream output) {
        this.output = new PrintStream(output);
    }

    /**
     * Closes the underlying PrintStream.
     */
    public void close() {
        output.close();
    }

    /**
     * Append a string to the output.
     * If the strings ends with \n, the next call to append will automatically print the indentation.
     *
     * @param str The string to append.
     */
    public void append(String str) {
        if (shouldIndent) {
            for (int i = 0; i < indentLevel; ++i) {
                output.append("    ");
            }
        }

        shouldIndent = str.endsWith("\n");
        output.append(str);
    }

    /**
     * Increases indentation level.
     */
    public void indent() {
        indentLevel++;
    }

    /**
     * Decreases indentation level.
     */
    public void unindent() {
        indentLevel--;
    }
}
