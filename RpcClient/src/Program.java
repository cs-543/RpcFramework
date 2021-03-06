import Rpc.*;

import java.lang.Thread;
import java.io.ByteArrayInputStream;

public class Program {


    public static void main(String[] args) throws Exception {
        TestInterface remoteInterface = Rpc.Registry.Registry.getServiceByURI("rpc://localhost/TestInterface", TestInterface.class);

        Out<Integer> oui = new Out<Integer>();
        oui.setValue(100);

        remoteInterface.__test_outArg( oui );
        System.out.println(oui.getValue());

        remoteInterface.__test_allPrimitiveArgs( (byte) 50, 'x', (short) 2000, 500000, (long) 500000
                                               , 1.5f, 2.0, "hello" );

        System.out.println("Calling: __test_voidCall");
        remoteInterface.__test_voidCall();

        System.out.println("Calling: __test_inArg arg=42");
        remoteInterface.__test_inArg(42);

        System.out.println("Calling: __test_forwardArg arg=5577");
        System.out.println(remoteInterface.__test_forwardArg(5577));

        System.out.println("Calling: __test_inOutArg arg=101");
        InOut<Integer> iou = new InOut<Integer>(101);
        remoteInterface.__test_inOutArg(iou);
        System.out.println(iou.getValue());

        ForwardCallback fc = new ForwardCallback();

        System.out.println("Calling: __testforwardArgAsync arg=150");
        remoteInterface.__test_forwardArgAsync(150, fc);
        System.out.println("Async going...");

        System.out.println("Waiting 10 seconds before calling " +
                           "__test_policy_atLeastOnce()...");
        Thread.sleep(10000);
        remoteInterface.__test_policy_atLeastOnce();
        System.out.println("Okay!");
        Thread.sleep(500);
    }
}

