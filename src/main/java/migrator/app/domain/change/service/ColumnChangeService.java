package migrator.app.domain.change.service;

import migrator.app.domain.table.model.Table;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.TableChange;

public class ColumnChangeService {
    protected ChangeService changeService;
    protected ColumnChangeFactory columnChangeFactory;

    public ColumnChangeService(ChangeService changeService, ColumnChangeFactory columnChangeFactory) {
        this.changeService = changeService;
        this.columnChangeFactory = columnChangeFactory;
    }

    public ColumnChange getOrCreate(Table table, ColumnProperty columnProperty) {
        TableChange tableChange = this.changeService.getOrCreateTableChange(table.getProject().getName(), table.getOriginalName());

        for (ColumnChange columnChange : tableChange.getColumnsChanges()) {
            if (columnChange.getOriginalName().equals(columnProperty.getName())) {
                return columnChange;
            }
        }

        ColumnChange newColumnChange = this.columnChangeFactory.createNotChanged(columnProperty.getName());
        tableChange.getColumnsChanges().add(newColumnChange);

        return newColumnChange;
    }
}