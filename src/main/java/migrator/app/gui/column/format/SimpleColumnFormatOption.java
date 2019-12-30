package migrator.app.gui.column.format;

import migrator.app.database.column.format.ApplicationColumnFormat;
import migrator.app.gui.GuiModel;

public class SimpleColumnFormatOption implements ColumnFormatOption {
    protected String name;
    ApplicationColumnFormat appFormat;

    public SimpleColumnFormatOption(String name, ApplicationColumnFormat appFormat) {
        this.name = name;
        this.appFormat = appFormat;
    }

    @Override
    public void updateModel(GuiModel columnModel) {
        columnModel.setAttribute("length", appFormat.hasLength());
        columnModel.setAttribute("precision", appFormat.hasPrecision());
        columnModel.setAttribute("sign", appFormat.hasSign());
        columnModel.setAttribute("autoIncrement", appFormat.hasAutoIncrement());
    }

    @Override
    public String toString() {
        return this.name;
    }
}