package migrator.app.database.format;

import migrator.lib.config.MapConfig;

public class FakeColumnFormatManager extends ColumnFormatManager {
    protected ColumnFormat format;
    public FakeColumnFormatManager(ColumnFormat format) {
        super(new MapConfig<>());
        this.format = format;
    }

    @Override
    public ColumnFormat getFormat(String formatName) {
        return this.format;
    }
}