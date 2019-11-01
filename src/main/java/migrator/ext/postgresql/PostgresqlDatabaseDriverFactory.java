package migrator.ext.postgresql;

import migrator.app.database.driver.DatabaseDriver;
import migrator.app.database.driver.DatabaseDriverFactory;
import migrator.app.domain.column.service.ColumnFactory;
import migrator.app.domain.index.service.IndexFactory;
import migrator.app.domain.table.service.TableFactory;

public class PostgresqlDatabaseDriverFactory implements DatabaseDriverFactory {
    protected TableFactory tableFactory;
    protected ColumnFactory columnFactory;
    protected IndexFactory indexFactory;

    public PostgresqlDatabaseDriverFactory(TableFactory tableFactory, ColumnFactory columnFactory, IndexFactory indexFactory) {
        this.tableFactory = tableFactory;
        this.columnFactory = columnFactory;
        this.indexFactory = indexFactory;
    }

    @Override
    public DatabaseDriver create(String url, String user, String password) {
        return new PostgresqlDatabaseDriver(
            this.tableFactory,
            this.columnFactory,
            this.indexFactory,
            url,
            user,
            password
        );
    }
}