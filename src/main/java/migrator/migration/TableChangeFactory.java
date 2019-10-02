package migrator.migration;

public class TableChangeFactory {
    public TableChange createNotChanged(String tableName) {
        return new SimpleTableChange(
            tableName,
            new SimpleTableProperty(null),
            new ChangeCommand(ChangeCommand.NONE)
        );
    }

    public TableChange createWithCreateChange(String tableName) {
        return new SimpleTableChange(
            tableName,
            new SimpleTableProperty(tableName),
            new ChangeCommand(ChangeCommand.CREATE)
        );
    }
}