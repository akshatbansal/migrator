package migrator.table.javafx;

import migrator.javafx.breadcrumps.BreadcrumpsService;
import migrator.migration.ChangeService;
import migrator.router.Router;
import migrator.table.component.ColumnForm;
import migrator.table.component.ColumnList;
import migrator.table.component.IndexList;
import migrator.table.component.TableCard;
import migrator.table.component.TableForm;
import migrator.table.component.TableList;
import migrator.table.component.TableView;
import migrator.table.model.Table;
import migrator.table.service.ColumnService;
import migrator.table.service.GuiKit;
import migrator.table.service.IndexService;
import migrator.table.service.TableService;

public class JavafxGuiKit implements GuiKit {
    protected migrator.breadcrumps.GuiKit breadcrumpsGuiKit;
    protected Router router;

    public JavafxGuiKit(migrator.breadcrumps.GuiKit breadcrumpsGuiKit, Router router) {
        this.breadcrumpsGuiKit = breadcrumpsGuiKit;
        this.router = router;
    }

    @Override
    public TableCard createCard(Table table) {
        return new JavafxTableCard(table);
    }

    @Override
    public TableList createList(TableService tableService) {
        return new JavafxTableList(tableService, this, this.breadcrumpsGuiKit, this.router);
    }

    @Override
    public TableForm createForm(TableService tableService) {
        return new JavafxTableForm(tableService);
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
        return new JavafxIndexList(indexService);
    }

    @Override
    public ColumnForm createColumnForm(ColumnService columnService) {
        return new JavafxColumnForm(columnService);
    }
}