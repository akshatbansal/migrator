package migrator.ext.mysql.database;

import javafx.collections.ObservableList;
import migrator.app.database.DatabaseStructure;
import migrator.app.database.DatabaseStructureFactory;
import migrator.app.database.JdbcConnectionDriver;
import migrator.app.database.SimpleDatabaseStructure;
import migrator.app.database.column.ColumnStructure;
import migrator.app.database.index.IndexStructure;
import migrator.app.database.table.TableStructure;
import migrator.app.migration.model.ColumnProperty;
import migrator.ext.mysql.database.column.MysqlColumnDriver;
import migrator.ext.mysql.database.index.MysqlIndexDriver;
import migrator.ext.mysql.database.table.MysqlTableDriver;
import migrator.ext.sql.database.column.SqlColumnAdapter;
import migrator.ext.sql.database.index.SqlIndexAdapter;
import migrator.ext.sql.database.table.SqlTableAdapter;
import migrator.lib.logger.Logger;

public class MysqlStructureFactory implements DatabaseStructureFactory {
    protected Logger logger;

    public MysqlStructureFactory(Logger logger) {
        this.logger = logger;
    }

    @Override
    public DatabaseStructure create(String url, String user, String password) {
        JdbcConnectionDriver mysqlConnection = new JdbcConnectionDriver("com.mysql.cj.jdbc.Driver", url, user, password, this.logger);

        return new SimpleDatabaseStructure(
            mysqlConnection,
            new TableStructure(
                new MysqlTableDriver(mysqlConnection),
                new SqlTableAdapter()
            ),
            new ColumnStructure(
                new MysqlColumnDriver(mysqlConnection),
                new SqlColumnAdapter()
            ),
            (ObservableList<ColumnProperty> columns) -> {
                return new IndexStructure(
                    new MysqlIndexDriver(mysqlConnection),
                    new SqlIndexAdapter(columns)
                );
            }
        );
    }
} 