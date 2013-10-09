package Compiler;

import java.io.PrintStream;

class JavaSkeletonEmitter {
    private final PrintStream output;

    public JavaSkeletonEmitter(PrintStream output) {
        this.output = output;
    }

    public void emit(Interface interface_) {
        output.append("public ");
    }
}
