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
        // System.out.println(baos.toString());
        remoteSocket.getOutputStream().flush();

        RunningIndex returned_ri = (RunningIndex) obr.readObject();

        String got_return = (String) obr.readObject();
        Object return_value;
        if ( got_return.equals("return") ) {
            return_value = obr.readObject();
        } else {
            return_value = new Object();
        }

        for ( Object arg : call.arguments ) {
            if ( arg.getClass() == Out.class ) {
                Out new_out = (Out) obr.readObject();
                ((Out) arg).setValue(new_out.getValue());
            } else if ( arg.getClass() == InOut.class ) {
                InOut new_out = (InOut) obr.readObject();
                ((InOut) arg).setValue(new_out.getValue());
            }
        }

        runningIndex++; // wraps around after 2^31-1
                        // let's pretend as if nothing bad happens then

        return (T) return_value;
    }

    public <T> void invokeAsync(Call call, Callback<T> callback) throws Exception {
        throw new Exception("Not implemented");
    }
}
