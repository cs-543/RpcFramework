package Rpc;
import java.net.Socket;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public abstract class Stub {
    private Socket remoteSocket;
    private ObjectStreamReader obr;
    private int runningIndex = 0;

    public Stub(Socket remoteSocket) throws IOException {
        this.remoteSocket = remoteSocket;
        obr = new ObjectStreamReader(this.remoteSocket.getInputStream());
    }

    private class InputReader implements Runnable {
        @Override
        public void run() {

        }
    }

    public <T> T invoke(Call call) throws Exception {
        ObjectStreamWriter obw = new ObjectStreamWriter( remoteSocket.getOutputStream() );

        obw.write(runningIndex);
        obw.write(call);
        remoteSocket.getOutputStream().flush();

        runningIndex++; // wraps around after 2^31-1
                        // let's pretend as if nothing bad happens then

        throw new Exception("Not implemented");
    }

    public <T> void invokeAsync(Call call, Callback<T> callback) throws Exception {
        throw new Exception("Not implemented");
    }
}
