package migrator.migration;

public interface IndexChange extends IndexProperty {
    public ChangeCommand getCommand();
    public void clear();
}