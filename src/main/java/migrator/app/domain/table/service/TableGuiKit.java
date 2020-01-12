package migrator.app.domain.table.service;

import migrator.app.domain.table.component.ColumnForm;
import migrator.app.domain.table.component.ColumnList;
import migrator.app.domain.table.component.IndexForm;
import migrator.app.domain.table.component.IndexList;
import migrator.app.domain.table.component.TableForm;
import migrator.app.domain.table.component.TableView;

public interface TableGuiKit {
    public TableForm createForm();
    public TableView createView();
    public ColumnList createColumnList();
    public IndexList createIndexList();
    public ColumnForm createColumnForm();
    public IndexForm createIndexForm();
}