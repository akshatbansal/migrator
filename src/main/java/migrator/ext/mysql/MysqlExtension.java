package migrator.ext.mysql;

import migrator.app.boot.Container;
import migrator.app.service.Service;
import migrator.ext.mysql.database.MysqlStructureFactory;

public class MysqlExtension implements Service {
    private Container container;

    public MysqlExtension(Container container) {
        this.container = container;
    }

    @Override
    public void start() {
        container.databaseContainer().addStrucutreFactory(
            "mysql",
            new MysqlStructureFactory(
                container.logger()
            )
        );
    }

    @Override
    public void stop() {
        
    }
}