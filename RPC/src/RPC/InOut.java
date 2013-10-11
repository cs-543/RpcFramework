package Rpc;

public class InOut<T> {
    private T value;

    public InOut() {
    }

    public InOut(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
