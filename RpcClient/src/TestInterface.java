import Rpc.*;

public interface TestInterface {
    void __test_voidCall() throws Exception;
    
    void __test_inArg(int arg) throws Exception;
    
    void __test_outArg(Out<Integer> arg) throws Exception;
    
    void __test_inOutArg(InOut<Integer> arg) throws Exception;
    
    void __test_allPrimitiveArgs(byte b, char c, short s, int i, long l, float f, double d, String str) throws Exception;
    
    int __test_forwardArg(int arg) throws Exception;
    
    void __test_forwardArgAsync(int arg, Callback<Integer> callback) throws Exception;
    
    void __test_policy_atLeastOnce() throws Exception;
}
