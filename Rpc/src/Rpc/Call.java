package Rpc;

public class Call {
    String method;
    Object[] arguments;

    public Call() {
    }

    public Call(String method, Object... arguments) {
        this.method = method;
        this.arguments = arguments;
    }

    public String getMethod() {
        return method;
    }

    public Object[] getArguments() {
        return arguments;
    }
}
