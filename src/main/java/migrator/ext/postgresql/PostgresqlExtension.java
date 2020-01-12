package migrator.ext.postgresql;

import migrator.app.boot.Container;
import migrator.app.extension.Extension;
import migrator.ext.postgresql.database.PostgresqlStructureFactory;

public class PostgresqlExtension implements Extension {
    @Override
    public void load(Container container) {
        container.databaseContainer().addStrucutreFactory(
            "postgresql",
                new PostgresqlStructureFactory(
                    container.logger()
                )
        );
    }
}