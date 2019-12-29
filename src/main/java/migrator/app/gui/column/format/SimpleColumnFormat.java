package migrator.app.gui.column.format;

import java.util.List;

import migrator.app.gui.GuiModel;

public class SimpleColumnFormat implements ColumnFormat {
    protected String name;
    protected List<String> enabledAttributes;
    protected List<String> disabledAttributes;

    public SimpleColumnFormat(String name, List<String> enabledAttributes, List<String> disabledAttributes) {
        this.name = name;
        this.enabledAttributes = enabledAttributes;
        this.disabledAttributes = disabledAttributes;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void updateModel(GuiModel columnModel) {
        for (String attributeName : this.disabledAttributes) {
            columnModel.disable(attributeName);
        }
        for (String attributeName : this.enabledAttributes) {
            columnModel.enable(attributeName);
        }
    }

    @Override
    public String toString() {
        return this.name;
    }
}