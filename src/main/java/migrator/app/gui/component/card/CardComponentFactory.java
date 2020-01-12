package migrator.app.gui.component.card;

public interface CardComponentFactory<T> {
    public CardComponent<T> create(T item);
}