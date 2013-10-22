package Rpc.Compiler;

import java.io.OutputStream;

/**
 * Base class from which all emitters must derive.
 */
public abstract class Emitter {
    /**
     * Output stream where emitted text is written to.
     */
    protected IndentedPrintStream output;

    /**
     * Initializes a new instance of the Emitter class.
     *
     * @param output The output stream where emitted text is written to.
     */
    protected Emitter(OutputStream output) {
        this.output = new IndentedPrintStream(output);
    }

    /**
     * Closes the underlying output stream.
     */
    public void close() {
        output.close();
    }

    /**
     * Emits an interface and returns itself for method chaining.
     *
     * @param input The interface to emit.
     * @return This emitter.
     */
    public abstract Emitter emit(Interface input);
}
