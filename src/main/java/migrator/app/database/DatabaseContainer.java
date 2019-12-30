package migrator.app.database;

import migrator.app.database.column.format.ApplicationColumnFormatCollection;

public class DatabaseContainer {
    protected ApplicationColumnFormatCollection applicationColumnFormatCollection;

    public DatabaseContainer() {
        this.applicationColumnFormatCollection = new ApplicationColumnFormatCollection();
    }

    public ApplicationColumnFormatCollection getApplicationColumnFormatCollection() {
        return this.applicationColumnFormatCollection;
    }
}