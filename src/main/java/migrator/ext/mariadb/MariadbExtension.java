package migrator.ext.mariadb;

import migrator.app.boot.Container;
import migrator.app.service.Service;
import migrator.ext.mysql.database.MysqlStructureFactory;

public class MariadbExtension implements Service {
    private Container container;

    public MariadbExtension(Container container) {
        this.container = container;
    }

    @Override
    public void start() {
        container.databaseContainer().addStrucutreFactory(
            "mariadb",
                new MysqlStructureFactory(
                    container.logger()
                )
        );
    }

    @Override
    public void stop() {
        
    }
}