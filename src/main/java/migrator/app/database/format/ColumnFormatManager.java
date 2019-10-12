package migrator.app.database.format;

import java.util.Collection;
import java.util.Map;

import migrator.lib.config.MapConfig;

public class ColumnFormatManager {
    protected Map<String, ColumnFormat> config;

    public ColumnFormatManager(MapConfig<ColumnFormat> config) {
        this.config = config.getValues();
    }

    public Collection<ColumnFormat> getFormats() {
        return this.config.values();
    }

    public ColumnFormat getFormat(String formatName) {
        if (this.config.containsKey(formatName)) {
            return this.config.get(formatName);
        }
        return null;
    }

    public ColumnFormatter getFormatter(String columnBaseName) {
        ColumnFormat format = this.getFormat(columnBaseName);
        if (format == null) {
            return null;
        }
        return format.getFormatter();
    }
}