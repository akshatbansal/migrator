package migrator.migration;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class TableChange {
    protected String name;
    protected ChangeCommand command;
    protected Map<String, Object> arguments;
    protected List<ColumnChange> columns;
    protected List<IndexChange> indexes;

    public TableChange(String name) {
        this(name, new ChangeCommand(ChangeCommand.UPDATE));
    }

    public TableChange(String name, ChangeCommand command) {
        this(name, command, new ArrayList<>());
    }

    public TableChange(String name, ChangeCommand command, List<ColumnChange> columns) {
        this(name, command, columns, new ArrayList<>());
    }

    public TableChange(String name, ChangeCommand command, List<ColumnChange> columns, List<IndexChange> indexes) {
        this.name = name;
        this.command = command;
        this.columns = columns;
        this.indexes = indexes;
    }

    public String getName() {
        return this.name;
    }

    public List<ColumnChange> getColumnsChanges() {
        return this.columns;
    }

    public List<IndexChange> getIndexesChanges() {
        return this.indexes;
    }

    public ChangeCommand getCommand() {
        return this.command;
    }

    public Boolean hasCommand() {
        return this.command != null && !this.command.isType(null);
    }
}