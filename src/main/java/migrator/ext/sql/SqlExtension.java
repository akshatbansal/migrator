package migrator.ext.sql;

import migrator.app.boot.Container;
import migrator.app.extension.Extension;

public class SqlExtension implements Extension {
    @Override
    public void load(Container container) {
        container.codeContainer().addCommandFactory(
            "sql",
            new SqlCommandFactory(
                container.databaseContainer().getApplicationColumnFormatCollection()
            )
        );
    }
}