package migrator.migration;

public class IndexChange {
    protected String name;
    protected ChangeCommand command;

    public IndexChange(String name) {
        this(name, new ChangeCommand(ChangeCommand.CREATE));
    }

    public IndexChange(String name, ChangeCommand command) {
        this.name = name;
        this.command = command;
    }

    public String getName() {
        return this.name;
    }

    public ChangeCommand getCommand() {
        return this.command;
    }
}