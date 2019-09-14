package migrator.migration;

public class ColumnChange {
    protected ChangeCommand command;
    protected String name;

    public ColumnChange(String name, ChangeCommand command) {
        this.name = name;
        this.command = command;
    }

    public String getName() {
        return (String) this.name;
    }

    public String getFormat() {
        return (String) this.command.getArgument("format");
    }

    public ChangeCommand getCommand() {
        return this.command;
    }
}