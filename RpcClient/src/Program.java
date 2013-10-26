import Rpc.*;

import java.io.ByteArrayInputStream;

public class Program {

    public static void main(String[] args) throws Exception {
        TestInterface remoteInterface = Rpc.Registry.Registry.getServiceByURI("rpc://localhost/TestInterface", TestInterface.class);

        remoteInterface.__test_allPrimitiveArgs( (byte) 50, 'x', (short) 2000, 500000, (long) 500000
                                               , 1.5f, 2.0, "hello" );

        System.out.println("Calling: __test_voidCall");
        remoteInterface.__test_voidCall();

        System.out.println("Calling: __test_inArg arg=42");
        remoteInterface.__test_inArg(42);
    }
}

