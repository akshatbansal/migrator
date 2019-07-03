package migrator.table.javafx;

import migrator.javafx.breadcrumps.BreadcrumpsService;
import migrator.router.Router;
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
    public TableView createView(TableService tableService, ColumnService columnService, IndexService indexService) {
        return new JavafxTableView(this.breadcrumpsGuiKit, this, tableService, columnService, indexService);
    }

    @Override
    public ColumnList createColumnList(ColumnService columnService) {
        return new JavafxColumnList(columnService);
    }

    @Override
    public IndexList createIndexList(IndexService indexService) {
        return new JavafxIndexList(indexService);
    }
}