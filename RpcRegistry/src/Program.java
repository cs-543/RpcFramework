import Rpc.Registry.RegistryServer;

public class Program {
    public static void main(String[] args) throws Exception {
        Thread serverThread = new Thread(new RegistryServer());
        serverThread.start();

        System.out.println("Registry started, press any key to terminate...");
        System.in.read();

        serverThread.interrupt();
    }
}
