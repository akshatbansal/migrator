package migrator.app.domain.table.service;

import migrator.app.domain.change.service.ChangeService;
import migrator.app.domain.table.component.ColumnForm;
import migrator.app.domain.table.component.ColumnList;
import migrator.app.domain.table.component.IndexForm;
import migrator.app.domain.table.component.IndexList;
import migrator.app.domain.table.component.TableCard;
import migrator.app.domain.table.component.TableForm;
import migrator.app.domain.table.component.TableList;
import migrator.app.domain.table.component.TableView;
import migrator.app.domain.table.model.Table;

public interface TableGuiKit {
    public TableList createList(TableService tableService);
    public TableCard createCard(Table table);
    public TableForm createForm(TableService tableService);
    public TableView createView(TableService tableService, ColumnService columnService, IndexService indexService, ChangeService changeService);
    public ColumnList createColumnList(ColumnService columnService, ChangeService changeService);
    public IndexList createIndexList(IndexService indexService);
    public ColumnForm createColumnForm(ColumnService columnService);
    public IndexForm createIndexForm(IndexService indexService, ColumnService columnService);
}