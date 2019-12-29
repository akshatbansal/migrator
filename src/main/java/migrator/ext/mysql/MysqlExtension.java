package migrator.ext.mysql;

import migrator.app.ConfigContainer;
import migrator.app.extension.Extension;

public class MysqlExtension implements Extension {
    @Override
    public void load(ConfigContainer config) {
        config.databseContainerConfig().get()
            .addStrucutreFactory("mysql", new MysqlDatabaseStructureFactory());
    }
}