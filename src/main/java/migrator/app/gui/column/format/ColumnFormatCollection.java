package migrator.app.gui.column.format;

import java.util.Iterator;
import java.util.Map.Entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.MapChangeListener.Change;
import migrator.app.database.column.format.ApplicationColumnFormat;

public class ColumnFormatCollection {
    protected ObservableList<ColumnFormatOption> formats;

    public ColumnFormatCollection(ObservableMap<String, ApplicationColumnFormat> applicationFormats) {
        this.formats = FXCollections.observableArrayList();

        applicationFormats.addListener((Change<? extends String, ? extends ApplicationColumnFormat> change) -> {
            if (change.wasAdded()) {
                this.addFormat(change.getKey(), change.getValueAdded());
            } else if (change.wasRemoved()) {
                this.removeFormat(change.getKey());
            }
        });

        Iterator<Entry<String, ApplicationColumnFormat>> iterator = applicationFormats.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, ApplicationColumnFormat> entry = iterator.next();
            this.addFormat(entry.getKey(), entry.getValue());
        }
    }

    public ObservableList<ColumnFormatOption> getObservable() {
        return this.formats;
    }

    public ColumnFormatOption getFormatByName(String formatName) {
        for (ColumnFormatOption format : this.formats) {
            if (format.toString().equals(formatName)) {
                return format;
            }
        }
        return null;
    }

    private void addFormat(String name, ApplicationColumnFormat appFormat) {
        this.formats.add(new SimpleColumnFormatOption(name, appFormat));
    }

    private void removeFormat(String name) {
        this.formats.remove(
            this.getFormatByName(name)
        );
    }
}