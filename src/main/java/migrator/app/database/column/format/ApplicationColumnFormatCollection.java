package migrator.app.database.column.format;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class ApplicationColumnFormatCollection {
    protected ObservableMap<String, ApplicationColumnFormat> formats;

    public ApplicationColumnFormatCollection() {
        this.formats = FXCollections.observableHashMap();
    }

    public ObservableMap<String, ApplicationColumnFormat> getObservable() {
        return this.formats;
    }

    public ApplicationColumnFormat getFormatByName(String formatName) {
        return this.formats.get(formatName);
    }

    public void addFormat(String name, ApplicationColumnFormat format) {
        this.formats.put(name, format);
    }
}