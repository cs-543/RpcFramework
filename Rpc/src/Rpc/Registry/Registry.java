package Rpc.Registry;

import Rpc.Skeleton;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registry {
    private static final Pattern URI_PATTERN = Pattern.compile("rpc://([_a-zA-Z][_a-zA-Z0-9]+)(:[0-9]+)?/([_a-zA-Z][_a-zA-Z0-9]+)");
    public static final int REGISTRY_PORT = 10000;

    @SuppressWarnings("unchecked")
    public static <T> T getServiceByURI(String uri, Class<T> type) throws Exception {
        String[] hostInfo = request(getSocketFromUri(uri), "lookup " + getServiceFromUri(uri)).split(":");
        Socket remoteSocket = new Socket(hostInfo[0], Integer.parseInt(hostInfo[1]));

        return (T) Class.forName(type.getName() + "_Stub").getConstructor(Socket.class).newInstance(remoteSocket);
    }

    public static <T> Skeleton<T> registerService(String uri, T service) throws Exception {
        Skeleton<T> skel = new Skeleton(service);
        int port = skel.getPort();

        request(getSocketFromUri(uri), "register " + getServiceFromUri(uri) +
                ":" + Integer.toString(port) );

        skel.run();
        return skel;
    }

    public static void unregisterService(String uri) throws Exception {
        Socket socket = getSocketFromUri(uri);
        request(socket, "unregister " + getServiceFromUri(uri));
    }

    private static Socket getSocketFromUri(String uri) throws Exception {
        Matcher matcher = matchUri(uri);

        String host = matcher.group(1);
        int port = REGISTRY_PORT;

        if (matcher.group(2) != null) {
            port = Integer.parseInt(matcher.group(1));
        }

        return new Socket(host, port);
    }

    private static String getServiceFromUri(String uri) throws Exception {
        return matchUri(uri).group(3);
    }

    private static String request(Socket socket, String request) throws Exception {
        PrintStream output = new PrintStream(socket.getOutputStream());
        output.append(request);
        output.append("\n");

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response = input.readLine();

        if (response.startsWith("!")) {
            throw new Exception(response.substring(1));
        }

        return response;
    }

    private static Matcher matchUri(String uri) throws Exception {
        Matcher matcher = URI_PATTERN.matcher(uri);

        if (!matcher.matches()) {
            throw new Exception("Malformed Uri.");
        }

        return matcher;
    }
}
