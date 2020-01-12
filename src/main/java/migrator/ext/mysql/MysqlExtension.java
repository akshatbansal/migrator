package migrator.ext.mysql;

import migrator.app.boot.Container;
import migrator.app.extension.Extension;
import migrator.ext.mysql.database.MysqlStructureFactory;

public class MysqlExtension implements Extension {
    @Override
    public void load(Container container) {
        container.databaseContainer().addStrucutreFactory(
            "mysql",
            new MysqlStructureFactory(
                container.logger()
            )
        );
    }
}