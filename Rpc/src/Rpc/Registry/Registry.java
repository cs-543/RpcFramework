package Rpc.Registry;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registry {
    private static final Pattern URI_PATTERN = Pattern.compile("rpc://([_a-zA-Z][_a-zA-Z0-9]+)(:[0-9]+)?/([_a-zA-Z][_a-zA-Z0-9]+)");
    public static final int REGISTRY_PORT = 10000;

    public static <T> T getServiceByURI(String uri) throws Exception {
        Matcher matcher = URI_PATTERN.matcher(uri);

        if (!matcher.matches()) {
            throw new Exception("Malformed URI.");
        }

        String host = matcher.group(1);
        int port = REGISTRY_PORT;
        String service = matcher.group(3);

        if (matcher.group(2) != null) {
            port = Integer.parseInt(matcher.group(1));
        }

        //Socket socket = new Socket(host, port);

        System.out.printf("rpc://%s:%d/%s\n", host, port, service);

        return (T) null;
    }

    public static <T> void registerService(String name, T service) {

    }

    public static void unregisterService(String name) {

    }
}
