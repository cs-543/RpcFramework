package Rpc.Registry;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegistryServer implements Runnable {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final Map<String, InetAddress> services = new HashMap<String, InetAddress>();

    @Override
    public void run() {
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(10000);
        } catch (Exception e) {
            return;
        }


        boolean shouldStop = false;

        while (!shouldStop) {
            try {
            Socket client = serverSocket.accept();
            executor.execute(new ClientHandler(this, client));
        } catch (Exception e) {
            shouldStop = true;
            }
        }
    }

    public InetAddress getServiceAddressByName(String name) throws Exception {
        InetAddress address;

        synchronized (services) {
            if (!services.containsKey(name)) {
                throw new Exception("Lookup failed: no such service.");
            }

            address = services.get(name);
        }

        return address;
    }

    public void registerService(String name, InetAddress address) throws Exception {
        synchronized (services) {
            if (services.containsKey(name)) {
                throw new Exception("Registration failed: duplicate name.");
            }

            services.put(name, address);
        }
    }

    public void unregisterService(String name) throws Exception {
        synchronized (services) {
            if (!services.containsKey(name)) {
                throw new Exception("Unregistration failed: no such service.");
            }

            services.remove(name);
        }
    }
}
