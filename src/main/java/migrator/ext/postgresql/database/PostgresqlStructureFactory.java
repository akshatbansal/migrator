package migrator.ext.postgresql.database;

import javafx.collections.ObservableList;
import migrator.app.database.DatabaseStructure;
import migrator.app.database.DatabaseStructureFactory;
import migrator.app.database.JdbcConnectionDriver;
import migrator.app.database.SimpleDatabaseStructure;
import migrator.app.database.column.ColumnStructure;
import migrator.app.database.index.IndexStructure;
import migrator.app.database.table.TableStructure;
import migrator.app.migration.model.ColumnProperty;
import migrator.ext.postgresql.database.column.PostgresqlColumnDriver;
import migrator.ext.postgresql.database.index.PostgresqlIndexDriver;
import migrator.ext.postgresql.database.table.PostgresqlTableDriver;
import migrator.ext.sql.database.column.SqlColumnAdapter;
import migrator.ext.sql.database.index.SqlIndexAdapter;
import migrator.ext.sql.database.table.SqlTableAdapter;
import migrator.lib.logger.Logger;

public class PostgresqlStructureFactory implements DatabaseStructureFactory {
    protected Logger logger;

    public PostgresqlStructureFactory(Logger logger) {
        this.logger = logger;
    }

    @Override
    public DatabaseStructure create(String url, String user, String password) {
        JdbcConnectionDriver connectionDriver = new JdbcConnectionDriver("", url, user, password, this.logger);
        return new SimpleDatabaseStructure(
            connectionDriver,
            new TableStructure(
                new PostgresqlTableDriver(connectionDriver),
                new SqlTableAdapter()
            ),
            new ColumnStructure(
                new PostgresqlColumnDriver(connectionDriver),
                new SqlColumnAdapter()    
            ),
            (ObservableList<ColumnProperty> columns)-> {
                return new IndexStructure(
                    new PostgresqlIndexDriver(connectionDriver), 
                    new SqlIndexAdapter(columns)
                );
            }    
        );
    }
}