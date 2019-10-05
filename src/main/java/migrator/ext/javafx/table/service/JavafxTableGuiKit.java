package migrator.ext.javafx.table.service;

import migrator.app.domain.database.service.DatabaseService;
import migrator.app.domain.table.component.ColumnForm;
import migrator.app.domain.table.component.ColumnList;
import migrator.app.domain.table.component.IndexForm;
import migrator.app.domain.table.component.IndexList;
import migrator.app.domain.table.component.TableCard;
import migrator.app.domain.table.component.TableForm;
import migrator.app.domain.table.component.TableList;
import migrator.app.domain.table.component.TableView;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.ColumnService;
import migrator.app.domain.table.service.IndexService;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.domain.table.service.TableService;
import migrator.ext.javafx.table.component.JavafxColumnForm;
import migrator.ext.javafx.table.component.JavafxColumnList;
import migrator.ext.javafx.table.component.JavafxIndexForm;
import migrator.ext.javafx.table.component.JavafxIndexList;
import migrator.ext.javafx.table.component.JavafxTableCard;
import migrator.ext.javafx.table.component.JavafxTableForm;
import migrator.ext.javafx.table.component.JavafxTableList;
import migrator.ext.javafx.table.component.JavafxTableView;
import migrator.javafx.helpers.View;
import migrator.migration.ChangeService;
import migrator.router.Router;

public class JavafxTableGuiKit implements TableGuiKit {
    protected migrator.breadcrumps.GuiKit breadcrumpsGuiKit;
    protected Router router;
    protected View view;
    protected DatabaseService databaseService;
    protected TableService tableService;

    public JavafxTableGuiKit(migrator.breadcrumps.GuiKit breadcrumpsGuiKit, Router router, View view, DatabaseService databaseService, TableService tableService) {
        this.breadcrumpsGuiKit = breadcrumpsGuiKit;
        this.databaseService = databaseService;
        this.tableService = tableService;
        this.router = router;
        this.view  = view;
    }

    @Override
    public TableCard createCard(Table table) {
        return new JavafxTableCard(table);
    }

    @Override
    public TableList createList(TableService tableService) {
        return new JavafxTableList(tableService, this.databaseService, this, this.breadcrumpsGuiKit, this.router);
    }

    @Override
    public TableForm createForm(TableService tableService) {
        return new JavafxTableForm(tableService, this.router);
    }

    @Override
    public TableView createView(TableService tableService, ColumnService columnService, IndexService indexService, ChangeService changeService) {
        return new JavafxTableView(this.breadcrumpsGuiKit, this, tableService, columnService, indexService, changeService);
    }

    @Override
    public ColumnList createColumnList(ColumnService columnService, ChangeService changeService) {
        return new JavafxColumnList(columnService, changeService, this.router);
    }

    @Override
    public IndexList createIndexList(IndexService indexService) {
        return new JavafxIndexList(this.view, indexService, this.router);
    }

    @Override
    public ColumnForm createColumnForm(ColumnService columnService) {
        return new JavafxColumnForm(columnService, this.tableService, this.router);
    }

    @Override
    public IndexForm createIndexForm(IndexService indexService, ColumnService columnService) {
        return new JavafxIndexForm(indexService, columnService, this.tableService, this.router);
    }
}