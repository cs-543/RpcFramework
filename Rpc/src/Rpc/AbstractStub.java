package Rpc;

public abstract class AbstractStub {
    public <T> T invoke(String methodName, Object... arguments) throws Exception {
        throw new Exception("Not implemented");
    }

    public <T> void invokeAsync(Callback<T> callback, String methodName, Object... arguments) throws Exception {
        throw new Exception("Not implemented");
    }
}
