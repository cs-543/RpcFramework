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

    public Stub( Socket remoteSocket ) throws IOException {
        this.remoteSocket = remoteSocket;
        obr = new ObjectStreamReader(this.remoteSocket.getInputStream());
    }

    public <T> T invoke(Call call) throws Exception {
        return invoke1(call, false);
    }

    private <T> T invoke1(Call call, boolean has_callback) throws Exception {
        byte[] payload;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectStreamWriter obw = new ObjectStreamWriter( baos );

        RunningIndex ri = new RunningIndex();
        ri.runningIndex = runningIndex;
        runningIndex++;

        obw.write(ri);
        obw.write(has_callback);
        obw.write(call);

        payload = baos.toByteArray();
        synchronized(remoteSocket) {
            remoteSocket.getOutputStream().write(payload);
            Object callback_return_value = null;

            RunningIndex returned_ri = (RunningIndex) obr.readObject();
            if ( has_callback ) {
                callback_return_value = obr.readObject();
            }

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

            if ( !has_callback ) {
                return (T) return_value;
            } else {
                return (T) callback_return_value;
            }
        }
    }

    private class AsyncThread implements Runnable {
        private Call call;
        private Callback callback;

        public AsyncThread(Call call, Callback callback)
        {
            this.call = call;
            this.callback = callback;
        }

        @Override
        public void run() {
            try {
                Object ret = invoke1(call, true);
                callback.call(ret);
            } catch ( Exception e ) {
            }
        }
    }

    public <T> void invokeAsync(Call call, Callback<T> callback) throws Exception    {
        Thread t = new Thread(new AsyncThread(call, callback));
        t.start();
    }
}
