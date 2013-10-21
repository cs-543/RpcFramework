package Rpc.Registry;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

class ClientHandler implements Runnable {
    private final RegistryServer server;
    private final Socket client;
    private BufferedReader input;
    private PrintStream output;

    public ClientHandler(RegistryServer server, Socket client) throws Exception {
        this.server = server;
        this.client = client;
        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        output = new PrintStream(client.getOutputStream());
    }

    @Override
    public void run() {
        try {
            String[] request = input.readLine().split(" ");

            if (request.length != 2) {
                throw new Exception("Malformed request.");
            }

            String response = processRequest(request[0], request[1]);
            output.append(response);
            output.append("\n");
        } catch (Exception e) {
            output.append("!");
            output.append(e.getMessage());
            output.append("\n");
        } finally {
            try {
                client.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String processRequest(String method, String argument) throws Exception {
        if (method.equals("lookup")) {
            InetAddress address = server.getServiceAddressByName(argument);
            return address.getHostAddress();
        }

        if (method.equals("register")) {
            if (!isValidServiceName(argument)) {
                throw new Exception("Invalid service name.");
            }

            server.registerService(argument, client.getInetAddress());
            return "Service registered.";
        }

        if (method.equals("unregister")) {
            if (!isValidServiceName(argument)) {
                throw new Exception("Invalid service name.");
            }

            server.unregisterService(argument);
            return "Service unregistered.";
        }

        throw new Exception("Unknown method.");
    }

    private boolean isValidServiceName(String name) {
        if (name == null || name.length() == 0) {
            return false;
        }

        char[] c = name.toCharArray();

        if (!Character.isJavaIdentifierStart(c[0])) {
            return false;
        }

        for (int i = 1; i < c.length; ++i) {
            if (!Character.isJavaIdentifierPart(c[i])) {
                return false;
            }
        }

        return true;
    }
}
