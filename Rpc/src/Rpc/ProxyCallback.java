package Rpc;

public class ProxyCallback extends Callback {
    public Object value;

    @Override
    public void call(Object arg) {
        value = arg;
    }
}

