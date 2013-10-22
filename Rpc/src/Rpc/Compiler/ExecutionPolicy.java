package Rpc.Compiler;

/**
 * Different execution policies for remote procedures.
 */
enum ExecutionPolicy {
    /**
     * A call to the procedure may execute zero or once.
     */
    AtMostOnce,

    /**
     * A call to the procedure may execute once or more.
     */
    AtLeastOnce
}
