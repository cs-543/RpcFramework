import Rpc.*;

import java.io.ByteArrayInputStream;

public class Program {

    public static void main(String[] args) throws Exception {
        /*String serialized = "{\"method\":\"methodName\",\"arguments\":[\"hi i'm yannick\"]}";
        Call c = new ObjectStreamReader(new ByteArrayInputStream(serialized.getBytes())).readObject(Call.class);

        System.out.println(c.getMethod());
        System.out.println(c.getArguments());*/

        Object[] arr = new Object[10];
        arr[0] = new Integer(10);

        test((Integer)arr[0]);


        //Marshal.marshalObject(new StringBuilder());

        //Call call = Marshal.unmarshalObject("{\"method\":\"hi\",\"array\":[\"29832893\",\"hello there howdy\"]}");


        /*
        TestInterface remoteInterface = Rpc.Registry.Registry.getServiceByURI("rpc://localhost/TestInterface");

        System.out.println("Calling: __test_voidCall");
        remoteInterface.__test_voidCall();

        System.out.println("Calling: __test_inArg arg=42");
        remoteInterface.__test_inArg(42);
        */
    }

    public static void test(int a) {
        System.out.println(a);
    }
}