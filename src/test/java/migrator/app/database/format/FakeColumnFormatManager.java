package migrator.app.database.format;

import migrator.lib.config.MapConfig;

public class FakeColumnFormatManager extends ColumnFormatManager {
    public FakeColumnFormatManager() {
        super(new MapConfig<>());
    }

    @Override
    public ColumnFormat getFormat(String formatName) {
        return new SimpleColumnFormat(formatName);
    }
}