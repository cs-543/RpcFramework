package Rpc;

public class InOut<T> {
    private T value;

    public InOut(T value) {
        setValue(value);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        if (value == null) {
            throw new IllegalArgumentException("value may not be null");
        }

        this.value = value;
    }
}
