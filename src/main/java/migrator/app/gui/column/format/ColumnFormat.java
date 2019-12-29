package migrator.app.gui.column.format;

import migrator.app.gui.GuiModel;

public interface ColumnFormat {
    public void updateModel(GuiModel columnModel);
    public String getName();
}