package Rpc;

public class Out<T> {
    private T value;

    public Out() {
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
