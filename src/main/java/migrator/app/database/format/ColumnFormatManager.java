package migrator.app.database.format;

import java.util.Collection;
import java.util.Map;

import migrator.lib.config.MapConfig;
import migrator.lib.config.ValueConfig;
import migrator.lib.logger.Logger;

public class ColumnFormatManager {
    protected Map<String, ColumnFormat> config;
    protected ValueConfig<Logger> loggerConfig;

    public ColumnFormatManager(MapConfig<ColumnFormat> config, ValueConfig<Logger> logger) {
        this.config = config.getValues();
        this.loggerConfig = logger;
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
            this.loggerConfig.get().error("Column format not suported " + columnBaseName);
            return null;
        }
        return format.getFormatter();
    }
}