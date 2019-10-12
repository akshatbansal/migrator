package migrator.ext.javafx.component.card;

public interface CardFactory<T> {
    public Card<T> create(T value);
}