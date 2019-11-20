package migrator.app.domain.table.service;

import migrator.app.domain.column.ColumnRepository;
import migrator.app.domain.index.IndexRepository;
import migrator.app.domain.table.model.Table;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.SimpleTableProperty;
import migrator.lib.uid.Generator;

public class TableFactory {
    protected ColumnRepository columnRepository;
    protected IndexRepository indexRepository;
    protected Generator idGenerator;

    public TableFactory(ColumnRepository columnRepository, IndexRepository indexRepository, Generator idGenerator) {
        this.columnRepository = columnRepository;
        this.indexRepository = indexRepository;
        this.idGenerator = idGenerator;
    }

    public Table createNotChanged(String projectId, String tableName) {
        String tableId = this.idGenerator.next();
        return new Table(
            tableId,
            projectId,
            new SimpleTableProperty(this.idGenerator.next(), tableName), // original
            new SimpleTableProperty(this.idGenerator.next(), tableName), // changed
            new ChangeCommand(this.idGenerator.next(), ChangeCommand.NONE),
            this.columnRepository.byTableProperty(tableId),
            this.indexRepository.byTableProperty(tableId)
        );
    }

    public Table createWithCreateChange(String projectId, String tableName) {
        String tableId = this.idGenerator.next();
        return new Table(
            tableId,
            projectId,
            new SimpleTableProperty(this.idGenerator.next(), ""), // original
            new SimpleTableProperty(this.idGenerator.next(), tableName), // changed
            new ChangeCommand(this.idGenerator.next(), ChangeCommand.CREATE),
            this.columnRepository.byTableProperty(tableId),
            this.indexRepository.byTableProperty(tableId)
        );
    }
}