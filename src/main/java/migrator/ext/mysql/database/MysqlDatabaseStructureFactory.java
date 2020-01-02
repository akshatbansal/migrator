package migrator.ext.mysql.database;

import migrator.app.database.DatabaseStructure;
import migrator.app.database.DatabaseStructureFactory;
import migrator.lib.logger.Logger;

public class MysqlDatabaseStructureFactory implements DatabaseStructureFactory {
    protected Logger logger;

    @Override
    public DatabaseStructure create(String url, String user, String password) {
        MysqlConnection mysqlConnection = new MysqlConnection(url, user, password, this.logger);

        return new MysqlStructure(mysqlConnection);
    }
} 