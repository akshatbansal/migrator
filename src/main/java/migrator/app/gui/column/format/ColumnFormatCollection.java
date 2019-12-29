package migrator.app.gui.column.format;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ColumnFormatCollection {
    protected ObservableList<ColumnFormat> formats;

    public ColumnFormatCollection() {
        this.formats = FXCollections.observableArrayList();
    }

    public ObservableList<ColumnFormat> getObservable() {
        return this.formats;
    }

    public ColumnFormat getFormatByName(String formatName) {
        for (ColumnFormat format : this.formats) {
            if (format.getName().equals(formatName)) {
                return format;
            }
        }
        return null;
    }

    public void addFormat(ColumnFormat format) {
        this.formats.add(format);
    }

    public void addFormat(String name, List<String> enabled) {
        ColumnFormatBuilder builder = new ColumnFormatBuilder(name);
        for (String attributeName : enabled) {
            builder.enable(attributeName);
        }

        this.formats.add(builder.build());
    }
}