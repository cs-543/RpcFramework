package Compiler;

import java.io.PrintStream;

public class JavaSkeletonEmitter {
    private PrintStream output;

    public JavaSkeletonEmitter(PrintStream output) {
        this.output = output;
    }

    public void emit(Interface interface_) {
        output.append("public ");
    }
}
