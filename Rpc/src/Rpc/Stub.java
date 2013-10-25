package Rpc;
import java.net.Socket;

public abstract class Stub {
    public Stub() {

    }

    public <T> T invoke(Socket remoteSocket, Call call) throws Exception {

        throw new Exception("Not implemented");
    }

    public <T> void invokeAsync(Socket remoteSocket, Call call, Callback<T> callback) throws Exception {
        throw new Exception("Not implemented");
    }
}
