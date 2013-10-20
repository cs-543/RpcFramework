package Rpc;

public abstract class Stub {
    public Stub() {

    }

    public <T> T invoke(Call call) throws Exception {

        throw new Exception("Not implemented");
    }

    public <T> void invokeAsync(Call call, Callback<T> callback) throws Exception {
        throw new Exception("Not implemented");
    }
}
