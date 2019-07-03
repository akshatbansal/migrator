package migrator.component;

public interface Renderer<T> {
    public void show(T object);
    public void hide();
}