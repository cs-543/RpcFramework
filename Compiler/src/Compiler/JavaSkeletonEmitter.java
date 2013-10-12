package Compiler;

import java.io.PrintStream;

class JavaSkeletonEmitter extends Emitter {
    public JavaSkeletonEmitter(PrintStream output) {
        super(output);
    }

    public JavaSkeletonEmitter emit(Interface interface_) {
        output.append("public ");

        return this;
    }
}
