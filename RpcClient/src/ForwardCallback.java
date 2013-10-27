import Rpc.*;

public class ForwardCallback extends Callback {
    @Override
    public void call(Object arg)
    {
        System.out.println("callback!");
        System.out.println(arg);
    }
}

