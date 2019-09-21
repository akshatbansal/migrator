package migrator.table.service;

import migrator.migration.ChangeService;
import migrator.table.component.ColumnForm;
import migrator.table.component.ColumnList;
import migrator.table.component.IndexForm;
import migrator.table.component.IndexList;
import migrator.table.component.TableCard;
import migrator.table.component.TableForm;
import migrator.table.component.TableList;
import migrator.table.component.TableView;
import migrator.table.model.Table;

public interface GuiKit {
    public TableList createList(TableService tableService);
    public TableCard createCard(Table table);
    public TableForm createForm(TableService tableService);
    public TableView createView(TableService tableService, ColumnService columnService, IndexService indexService, ChangeService changeService);
    public ColumnList createColumnList(ColumnService columnService, ChangeService changeService);
    public IndexList createIndexList(IndexService indexService);
    public ColumnForm createColumnForm(ColumnService columnService);
    public IndexForm createIndexForm(IndexService indexService);
}