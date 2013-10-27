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
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectStreamWriter obw2 = new ObjectStreamWriter( baos );

        ObjectStreamWriter obw = new ObjectStreamWriter( remoteSocket.getOutputStream() );

        RunningIndex ri = new RunningIndex();
        ri.runningIndex = runningIndex;

        obw.write(ri);
        obw.write(call);
        obw2.write(ri);
        obw2.write(call);
        // uncomment if you want to see what actually is getting sent
        //System.out.println(baos.toString());
        remoteSocket.getOutputStream().flush();

        runningIndex++; // wraps around after 2^31-1
                        // let's pretend as if nothing bad happens then

        throw new Exception("Not implemented");
    }

    public <T> void invokeAsync(Call call, Callback<T> callback) throws Exception {
        throw new Exception("Not implemented");
    }
}
