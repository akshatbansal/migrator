package migrator.lib.config;

public class ValueConfig<T> {
    protected T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return this.value;
    }
}