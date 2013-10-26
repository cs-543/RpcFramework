package Rpc;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
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
        private Socket s;

        public ClientHandler(Socket s)
        {
            this.s = s;
        }

        @Override
        public void run() {
            try {
                // OutputStreamReader is incomplete which makes it difficult to
                // continue from here.  -- Mikko
                obr = new ObjectStreamReader(s.getInputStream());
                Integer running_index = (Integer) obr.readPrimitive(Integer.class);
                System.out.println(running_index);
            } catch (Exception ie) {
            }
            try { s.close(); } catch (Exception e) { };
        }
    }

    @Override
    public void run() {
        while (!shouldClose) {
            try {
                Socket s = serverSocket.accept();
                ClientHandler ch = new ClientHandler(s);
                ch.run();
            } catch (IOException ie) {
            }
        }
    }
}
