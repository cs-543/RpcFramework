
public class Program {
    public static void main(String args[]) {
        if (args.length == 0) {
            printUsage();
            return;
        }
    }

    private static void printUsage() {
        System.out.println("Usage: rpcregistry port")
    }
}
