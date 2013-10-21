package Rpc.Registry;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegistryServer implements Runnable {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final Map<String, ServiceInfo> services = new HashMap<String, ServiceInfo>();

    @Override
    public void run() {
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(Registry.REGISTRY_PORT);
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

    public ServiceInfo getServiceInfoByName(String name) throws Exception {
        ServiceInfo serviceInfo;

        synchronized (services) {
            if (!services.containsKey(name)) {
                throw new Exception("No such service.");
            }

            serviceInfo = services.get(name);
        }

        return serviceInfo;
    }

    public void registerService(String name, ServiceInfo serviceInfo) throws Exception {
        synchronized (services) {
            if (services.containsKey(name)) {
                throw new Exception("Duplicate name.");
            }

            services.put(name, serviceInfo);
        }
    }

    public void unregisterService(String name) throws Exception {
        synchronized (services) {
            if (!services.containsKey(name)) {
                throw new Exception("No such service.");
            }

            services.remove(name);
        }
    }
}
