package Rpc;

import Rpc.*;
import java.net.Socket;
import java.net.SocketException;
import java.lang.Thread;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public abstract class Stub {
    private Socket remoteSocket;
    private String uri;

    private ObjectStreamReader obr;
    private int runningIndex = 0;

    public Stub( Socket remoteSocket, String uri ) throws IOException {
        this.remoteSocket = remoteSocket;
        this.uri = uri;
        obr = new ObjectStreamReader(this.remoteSocket.getInputStream());
    }

    private void reinitiateConnection()
    {
        try
        {
            remoteSocket = Rpc.Registry.Registry.getSocketByURI( uri );
            synchronized(obr) {
                obr = new ObjectStreamReader(this.remoteSocket.getInputStream());
            }
        }
        catch (Exception se)
        {
        }
    }

    public <T> T invoke(Call call) throws Exception {
        return invoke1(call, false);
    }

    public <T> T invokeAtLeastOnce(Call call) throws Exception {
        try
        {
            return invoke1(call, false);
        }
        catch ( SocketException se ) {
            reinitiateConnection();
            Thread.sleep(1000);
            return invokeAtLeastOnce(call); // I think Java does not do
                                            // tail-call optimization but maybe
                                            // no one will notice.
        }
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
            Object return_value;
            RunningIndex returned_ri;

            synchronized(obr) {
                returned_ri = (RunningIndex) obr.readObject();
                if ( has_callback ) {
                    callback_return_value = obr.readObject();
                }

                String got_return = (String) obr.readObject();
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
        private boolean atLeastOnce;

        public AsyncThread(Call call, Callback callback, boolean at_least_once)
        {
            this.call = call;
            this.callback = callback;
            this.atLeastOnce = at_least_once;
        }

        @Override
        public void run() {
            Object ret;
            try {
                ret = invoke1(call, true);
            } catch (SocketException se) {
                if ( atLeastOnce ) {
                    run();
                    return;
                } else {
                    return;
                }
            } catch ( Exception e ) {
                return;
            }
            try { callback.call(ret); } catch ( Exception e ) { };
        }
    }

    public <T> void invokeAsync(Call call, Callback<T> callback) throws Exception
    {
        Thread t = new Thread(new AsyncThread(call, callback, false));
        t.start();
    }

    public <T> void invokeAsyncAtLeastOnce(Call call, Callback<T> callback) throws
        Exception
    {
        Thread t = new Thread(new AsyncThread(call, callback, true));
        t.start();
    }
}

