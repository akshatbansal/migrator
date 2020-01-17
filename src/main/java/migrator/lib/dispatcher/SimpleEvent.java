package migrator.lib.dispatcher;

public class SimpleEvent<T> implements Event<T> {
    protected T value;
    protected String name;

    public SimpleEvent(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public SimpleEvent(String name) {
        this(name, null);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public T getValue() {
        return this.value;
    }
}