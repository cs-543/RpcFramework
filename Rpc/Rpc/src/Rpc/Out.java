package Rpc;

public class Out<T> {
    private T value;

    public Out() {
    }

    public Out(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
