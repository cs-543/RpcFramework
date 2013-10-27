package Rpc;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.lang.Class;
import java.lang.reflect.Method;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Skeleton<T> implements Runnable {
    private ServerSocket serverSocket;
    private BlockingQueue queue = new LinkedBlockingDeque();
    private T implementation;
    private boolean shouldClose = false;

    public Skeleton(T implementation) throws Exception {
        serverSocket = new ServerSocket(0);
        this.implementation = implementation;
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public void stop() {
        try {
            shouldClose = true;
            serverSocket.close(); // provokes SocketException in accept()
        } catch (IOException e) {
            // I think we should not propagate this exception up
        }
    }

    private class ClientHandler implements Runnable
    {
        private ObjectStreamReader obr;
        private ObjectStreamWriter obw;
        private Socket s;
        private Method[] methods;

        public ClientHandler(Socket s) throws Exception
        {
            obr = new ObjectStreamReader(s.getInputStream());
            obw = new ObjectStreamWriter(s.getOutputStream());
            this.s = s;

            methods = implementation.getClass().getMethods();
        }

        @Override
        public void run() {
            while(true) {
                try {
                    RunningIndex running_index_ob =
                        (RunningIndex) obr.readObject();
                    int running_index = running_index_ob.runningIndex;

                    Call c = (Call) obr.readObject();

                    // We could easily start a thread for each call; however, I
                    // think it's easier to write the server if they can assume no
                    // two calls are happening simultaneously.
                    boolean found_it = false;
                    for ( Method m : methods ) {
                        if ( m.getName().equals(c.method) ) {
                            Object ret = m.invoke(implementation, c.arguments);
                            obw.write(running_index_ob);

                            if ( ret != null &&
                                 ret.getClass() != void.class ) {
                                obw.write("return");
                                obw.write(ret);
                            } else {
                                obw.write("noreturn");
                            }

                            // Send back out variables
                            for ( Object a : c.arguments ) {
                                if ( a.getClass() == Out.class ||
                                     a.getClass() == InOut.class ) {
                                    obw.write(a);
                                }
                            }
                            found_it = true;
                            break;
                        }
                    }
                    if ( !found_it ) {
                        throw new Exception("No such method: '" + c.method + "'");
                    }
                } catch (SocketException se) {
                    return;
                } catch (Exception ie) {
                    ie.printStackTrace();
                    return;
                }
            }
        }
    }

    @Override
    public void run() {
        while (!shouldClose) {
            try {
                Socket s = serverSocket.accept();
                ClientHandler ch = new ClientHandler(s);
                Thread t = new Thread(ch);
                t.start();
            } catch (Exception ie) {
                ie.printStackTrace();
            }
        }
    }
}
