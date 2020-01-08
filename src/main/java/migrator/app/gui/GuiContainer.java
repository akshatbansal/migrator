package migrator.app.gui;

import migrator.app.database.DatabaseContainer;
import migrator.app.gui.column.format.ColumnFormatCollection;

public class GuiContainer {
    protected ColumnFormatCollection columnFormatCollection;

    public GuiContainer(DatabaseContainer databaseContainer) {
        this.columnFormatCollection = new ColumnFormatCollection(
            databaseContainer.getApplicationColumnFormatCollection().getObservable()
        );
    }

    public ColumnFormatCollection getColumnFormatCollection() {
        return this.columnFormatCollection;
    }
}