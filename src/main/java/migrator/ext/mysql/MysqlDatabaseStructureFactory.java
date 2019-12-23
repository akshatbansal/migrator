package migrator.ext.mysql;

import migrator.app.database.DatabaseStructure;
import migrator.app.database.DatabaseStructureFactory;
import migrator.app.database.SimpleDatabaseStructure;
import migrator.ext.mysql.column.MysqlColumnDriver;
import migrator.ext.mysql.index.MysqlIndexDriver;
import migrator.ext.mysql.table.MysqlTableDriver;
import migrator.lib.logger.Logger;

public class MysqlDatabaseStructureFactory implements DatabaseStructureFactory {
    protected Logger logger;

    @Override
    public DatabaseStructure create(String url, String user, String password) {
        MysqlConnection mysqlConnection = new MysqlConnection(url, user, password, this.logger);

        return new SimpleDatabaseStructure(
            new MysqlTableDriver(mysqlConnection),
            new MysqlColumnDriver(mysqlConnection),
            new MysqlIndexDriver(mysqlConnection),
            mysqlConnection
        );
    }
}