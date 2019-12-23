package migrator.app.gui.component.card;

public interface CardListener<T> {
    public void onCardEvent(CardEvent<T> event);
}