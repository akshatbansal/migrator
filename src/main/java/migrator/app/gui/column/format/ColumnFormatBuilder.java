package migrator.app.gui.column.format;

import java.util.LinkedList;
import java.util.List;

public class ColumnFormatBuilder {
    protected String name;
    protected List<String> enabled;
    protected List<String> disabled;

    public ColumnFormatBuilder(String name) {
        this.name = name;
        this.enabled = new LinkedList<>();
        this.disabled = new LinkedList<>();
        this.disabled.add("length");
        this.disabled.add("precision");
        this.disabled.add("sign");
        this.disabled.add("autoIncrement");
    }

    public ColumnFormatBuilder enable(String attributeName) {
        if (this.enabled.contains(attributeName)) {
            return this;
        }
        this.enabled.add(attributeName);
        this.disabled.remove(attributeName);
        return this;
    }

    public ColumnFormat build() {
        return new SimpleColumnFormat(this.name, this.enabled, this.disabled);
    }
}