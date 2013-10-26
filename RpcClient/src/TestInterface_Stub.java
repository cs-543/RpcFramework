import Rpc.*;
import java.net.Socket;
import java.io.IOException;

public class TestInterface_Stub extends Stub implements TestInterface {
    public TestInterface_Stub(Socket s) throws IOException {
        super(s);
    }

    @Override
    public void __test_voidCall() throws Exception {
        Call call = new Call("__test_voidCall");
        invoke(call);
    }
    
    @Override
    public void __test_inArg(int arg) throws Exception {
        Call call = new Call("__test_inArg", arg);
        invoke(call);
    }
    
    @Override
    public void __test_outArg(Out<Integer> arg) throws Exception {
        Call call = new Call("__test_outArg", arg);
        invoke(call);
    }
    
    @Override
    public void __test_inOutArg(InOut<Integer> arg) throws Exception {
        Call call = new Call("__test_inOutArg", arg);
        invoke(call);
    }
    
    @Override
    public void __test_allPrimitiveArgs(byte b, char c, short s, int i, long l, float f, double d, String str) throws Exception {
        Call call = new Call("__test_allPrimitiveArgs", b, c, s, i, l, f, d, str);
        invoke(call);
    }
    
    @Override
    public int __test_forwardArg(int arg) throws Exception {
        Call call = new Call("__test_forwardArg", arg);
        return invoke(call);
    }
    
    @Override
    public void __test_forwardArgAsync(int arg, Callback<Integer> callback) throws Exception {
        Call call = new Call("__test_forwardArgAsync", arg);
        invokeAsync(call, callback);
    }
    
    @Override
    public void __test_policy_atLeastOnce() throws Exception {
        Call call = new Call("__test_policy_atLeastOnce");
        invoke(call);
    }
}
