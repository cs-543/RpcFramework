package Compiler;

import java.io.PrintStream;

class JavaSkeletonEmitter {
    private final IndentedPrintStream output;

    public JavaSkeletonEmitter(PrintStream output) {
        this.output = new IndentedPrintStream(output);
    }

    public void emit(Interface interface_) {
        output.append("public ");
    }
}
