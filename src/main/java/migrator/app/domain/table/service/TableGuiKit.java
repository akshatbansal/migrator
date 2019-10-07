package migrator.app.domain.table.service;

import migrator.app.domain.table.component.ColumnForm;
import migrator.app.domain.table.component.ColumnList;
import migrator.app.domain.table.component.IndexForm;
import migrator.app.domain.table.component.IndexList;
import migrator.app.domain.table.component.TableForm;
import migrator.app.domain.table.component.TableList;
import migrator.app.domain.table.component.TableView;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.model.Table;

public interface TableGuiKit {
    public TableList createList();
    public TableForm createForm(Table table);
    public TableView createView(Table table);
    public ColumnList createColumnList();
    public IndexList createIndexList();
    public ColumnForm createColumnForm(Column column);
    public IndexForm createIndexForm(Index index);
}