package migrator.ext.javafx.component;

public interface CardFactory<T> {
    public Card<T> create(T value);
}