package migrator.ext.mariadb;

import migrator.app.ConfigContainer;
import migrator.app.extension.Extension;
import migrator.ext.mysql.database.MysqlDatabaseStructureFactory;

public class MariadbExtension implements Extension {
    @Override
    public void load(ConfigContainer config) {
        config.databaseContainerConfig().get()
            .addStrucutreFactory(
                "mariadb",
                new MysqlDatabaseStructureFactory()
            );
    }
}