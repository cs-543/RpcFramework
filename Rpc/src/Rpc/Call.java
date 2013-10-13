package Rpc;

public class Call {
    String methodName;
    Object[] arguments;

    public Call(String methodName, Object... arguments) {
        this.methodName = methodName;
        this.arguments = arguments;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getArguments() {
        return arguments;
    }
}