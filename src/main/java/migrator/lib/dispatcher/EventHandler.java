package migrator.lib.dispatcher;

public interface EventHandler {
    public void handle(Event<?> event);
}