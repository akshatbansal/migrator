package migrator.app.domain.table.service;

import migrator.app.domain.column.service.ColumnRepository;
import migrator.app.domain.index.service.IndexRepository;
import migrator.app.domain.project.model.Project;
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

    public Table createNotChanged(Project project, String tableName) {
        String tableId = this.idGenerator.next();
        String repositoryKey = project.getId() + "." + tableId;
        return new Table(
            tableId,
            new SimpleTableProperty(tableName), // original
            new SimpleTableProperty(tableName), // changed
            new ChangeCommand(ChangeCommand.NONE),
            this.columnRepository.getList(repositoryKey),
            this.indexRepository.getList(repositoryKey)
        );
    }

    public Table createWithCreateChange(Project project, String tableName) {
        String tableId = this.idGenerator.next();
        String repositoryKey = project.getId() + "." + tableId;
        return new Table(
            tableId,
            new SimpleTableProperty(""), // original
            new SimpleTableProperty(tableName), // changed
            new ChangeCommand(ChangeCommand.CREATE),
            this.columnRepository.getList(repositoryKey),
            this.indexRepository.getList(repositoryKey)
        );
    }
}