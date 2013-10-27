package Rpc;

public abstract class Callback<T> {
    public abstract void call(T arg);
}
