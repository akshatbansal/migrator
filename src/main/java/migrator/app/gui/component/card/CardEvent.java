package migrator.app.gui.component.card;

public class CardEvent<T> {
    protected T value;
    protected String name;

    public CardEvent(String name, T item) {
        this.name = name;
        this.value = item;
    }

    public T getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }
}