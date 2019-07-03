package migrator.table.service;

import migrator.table.component.ColumnList;
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
    public TableView createView(TableService tableService, ColumnService columnService, IndexService indexService);
    public ColumnList createColumnList(ColumnService columnService);
    public IndexList createIndexList(IndexService indexService);
}