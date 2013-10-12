package Compiler;

import java.io.OutputStream;

public abstract class Emitter {
    protected IndentedPrintStream output;

    protected Emitter(OutputStream output) {
        this.output = new IndentedPrintStream(output);
    }

    public void close() {
        output.close();
    }
}
