package migrator.ext.mariadb;

import migrator.app.boot.Container;
import migrator.app.extension.Extension;
import migrator.ext.mysql.database.MysqlStructureFactory;

public class MariadbExtension implements Extension {
    @Override
    public void load(Container container) {
        container.databaseContainer().addStrucutreFactory(
            "mariadb",
                new MysqlStructureFactory(
                    container.logger()
                )
        );
    }
}