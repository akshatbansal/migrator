package migrator.ext.sql;

import migrator.app.ConfigContainer;
import migrator.app.extension.Extension;

public class SqlExtension implements Extension {
    @Override
    public void load(ConfigContainer config) {
        config.getCodeConfig().addCommandFactory(
            "sql",
            new SqlCommandFactory(config.databaseContainerConfig().get().getApplicationColumnFormatCollection())
        );
    }
}