package migrator.app.gui;

import migrator.app.gui.column.format.ColumnFormatCollection;

public class GuiContainer {
    protected ColumnFormatCollection columnFormatCollection;

    public GuiContainer() {
        this.columnFormatCollection = new ColumnFormatCollection();
    }

    public ColumnFormatCollection getColumnFormatCollection() {
        return this.columnFormatCollection;
    }
}