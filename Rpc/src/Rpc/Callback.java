package Rpc;

public abstract class Callback<T> {
    abstract void call(T arg);
}
