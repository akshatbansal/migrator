package migrator.ext.postgresql;

import migrator.app.boot.Container;
import migrator.app.service.Service;
import migrator.ext.postgresql.database.PostgresqlStructureFactory;

public class PostgresqlExtension implements Service {
    private Container container;

    public PostgresqlExtension(Container container) {
        this.container = container;
    }

    @Override
    public void start() {
        container.databaseContainer().addStrucutreFactory(
            "postgresql",
                new PostgresqlStructureFactory(
                    container.logger()
                )
        );
    }

    @Override
    public void stop() {
        
    }
}