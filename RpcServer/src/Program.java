import Rpc.Registry.Registry;

public class Program {
    public static void main(String[] args) throws Exception {
        TestInterface testInterface = new TestInterfaceImpl();

        Registry.registerService("rpc://localhost/TestInterface", testInterface);

        System.out.println("Service registered, press any key to terminate...");
        System.in.read();

        Registry.unregisterService("rpc://localhost/TestInterface");
    }
}
