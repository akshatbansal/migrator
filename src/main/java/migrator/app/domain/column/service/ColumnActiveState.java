package migrator.app.domain.column.service;

import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableActiveState;
import migrator.lib.modelstorage.SimpleActiveState;

public class ColumnActiveState extends SimpleActiveState<Column> {
    protected ColumnRepository repository;
    protected TableActiveState tableActiveState;

    public ColumnActiveState(ColumnRepository repository, TableActiveState tableActiveState) {
        super();
        this.repository = repository;
        this.tableActiveState = tableActiveState;
    }

    protected String getRepositoryKey() {
        Table table = this.tableActiveState.getActive().get();
        return table.getProject().getName() + "." + table.getName();
    }

    @Override
    public void add(Column item) {
        this.repository.add(this.getRepositoryKey(), item);
        super.add(item);
    }

    @Override
    public void remove(Column item) {
        this.repository.remove(this.getRepositoryKey(), item);
        super.remove(item);
    }
}