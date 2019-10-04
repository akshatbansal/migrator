package migrator.app.gui;

public interface Controller<T> extends GuiNode {
    public void update(T value);
}