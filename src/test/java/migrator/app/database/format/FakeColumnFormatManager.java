package migrator.app.database.format;

import migrator.lib.config.MapConfig;
import migrator.lib.config.ValueConfig;
import migrator.lib.logger.SystemLogger;

public class FakeColumnFormatManager extends ColumnFormatManager {
    protected ColumnFormat format;
    public FakeColumnFormatManager(ColumnFormat format) {
        super(new MapConfig<>(), new ValueConfig<>(new SystemLogger()));
        this.format = format;
    }

    @Override
    public ColumnFormat getFormat(String formatName) {
        return this.format;
    }
}