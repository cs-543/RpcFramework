import Rpc.Callback;
import Rpc.InOut;
import Rpc.Out;

/**
 * Created with IntelliJ IDEA.
 * User: Naav
 * Date: 19/10/13
 * Time: 21:00
 * To change this template use File | Settings | File Templates.
 */
public class TestInterfaceImpl implements TestInterface {
    @Override
    public void __test_voidCall() {
        System.out.println("Call: __test_voidCall");
    }

    @Override
    public void __test_inArg(int arg) {
        System.out.println(String.format("Call: __test_inArg arg=%d", arg));
    }

    @Override
    public void __test_outArg(Out<Integer> arg) throws Exception {
        throw new Exception("Not implemented");
    }

    @Override
    public void __test_inOutArg(InOut<Integer> arg) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void __test_allPrimitiveArgs(byte b, char c, short s, int i, long l, float f, double d, String str) {
        System.out.println("Call: __test_allPrimitiveArgs");
        System.out.println(b + ", " + c + ", " + i + ", " + l + ", " + f + ", " + d + ", " + str);
    }

    @Override
    public int __test_forwardArg(int arg) {
        System.out.println("Call: __test_voidCall");

        return arg;
    }

    @Override
    public void __test_forwardArgAsync(int arg, Callback<Integer> callback) {
        System.out.println("Call: __test_voidCall");
    }

    @Override
    public void __test_policy_atLeastOnce() {
        System.out.println("Call: __test_policy_atLeastOnce");
    }
}
