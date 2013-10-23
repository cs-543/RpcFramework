package Rpc;

import java.net.ServerSocket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public abstract class Skeleton implements Runnable {
    private ServerSocket serverSocket;
    private BlockingQueue queue = new LinkedBlockingDeque();

    public Skeleton() throws Exception {
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket();
        } catch (Exception e) {
            return;
        }

        boolean shouldStop = false;
        while (!shouldStop) {
            try {
                //Socket client = serverSocket.accept();

            } catch (Exception e) {
            }
        }
    }
}
