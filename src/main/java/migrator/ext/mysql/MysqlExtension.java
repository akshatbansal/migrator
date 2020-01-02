package migrator.ext.mysql;

import migrator.app.ConfigContainer;
import migrator.app.extension.Extension;
import migrator.ext.mysql.database.MysqlDatabaseStructureFactory;

public class MysqlExtension implements Extension {
    @Override
    public void load(ConfigContainer config) {
        config.databaseContainerConfig().get()
            .addStrucutreFactory(
                "mysql",
                new MysqlDatabaseStructureFactory()
            );
    }
}