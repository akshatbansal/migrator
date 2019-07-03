package migrator;

import migrator.connection.service.ConnectionService;
import migrator.database.service.DatabaseService;
import migrator.database.service.MysqlServerKit;
import migrator.javafx.breadcrumps.BreadcrumpsService;
import migrator.table.service.ColumnService;
import migrator.table.service.IndexService;
import migrator.table.service.TableService;

public class BusinessLogic {
    protected ConnectionService connectionService;
    protected DatabaseService databaseService;
    protected TableService tableService;
    protected BreadcrumpsService breadcrumpsService;
    protected ColumnService columnService;
    protected IndexService indexService;
 
    public BusinessLogic() {
        this.connectionService = new ConnectionService();
        this.databaseService = new DatabaseService(this.connectionService, new MysqlServerKit());
        this.tableService = new TableService(this.databaseService, new MysqlServerKit());
        this.columnService = new ColumnService(this.tableService, new MysqlServerKit());
        this.indexService = new IndexService(this.tableService, new MysqlServerKit());
        this.breadcrumpsService = new BreadcrumpsService();
    }

    public ConnectionService getConnection() {
        return this.connectionService;
    }

    public DatabaseService getDatabase() {
        return this.databaseService;
    }

    public TableService getTable() {
        return this.tableService;
    }

    public ColumnService getColumn() {
        return this.columnService;
    }

    public IndexService getIndex() {
        return this.indexService;
    }

    public BreadcrumpsService getBreadcrumps() {
        return this.breadcrumpsService;
    }
}