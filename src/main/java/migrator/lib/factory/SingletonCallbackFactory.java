package migrator.lib.factory;

public class SingletonCallbackFactory<T> implements Factory<T> {
    protected T instance;
    protected Factory<T> factory;

    public SingletonCallbackFactory(Factory<T> factory) {
        this.factory = factory;
    }

    @Override
    public T create() {
        if (this.instance == null) {
            this.instance = this.factory.create();
        }
        return this.instance;
    }
}