package migrator.app.domain.column;

import java.util.List;

import javafx.collections.ObservableList;
import migrator.app.domain.table.model.Column;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnProperty;
import migrator.lib.repository.RepositoryMapIndex;
import migrator.lib.repository.UniqueRepository;

public class ColumnRepository extends UniqueRepository<Column> {
    protected UniqueRepository<ColumnProperty> columnPropertyRepo;
    protected UniqueRepository<ChangeCommand> changeCommandRepo;

    public ColumnRepository(UniqueRepository<ColumnProperty> columnPropertyRepo, UniqueRepository<ChangeCommand> changeCommandRepo) {
        super();
        this.columnPropertyRepo = columnPropertyRepo;
        this.changeCommandRepo = changeCommandRepo;
        this.indexes.add(
            new RepositoryMapIndex<>((Column column) -> {
                return column.getTableId();
            })
        );
    }

    public List<Column> findByTable(String tableId) {
        return this.byTableProperty(tableId);
    }

    public ObservableList<Column> byTableProperty(String tableId) {
        return this.indexes.get(0).filter(tableId);
    }

    public void addWith(Column item) {
        this.columnPropertyRepo.add(item.getOriginal());
        this.columnPropertyRepo.add(item.getChange());
        this.changeCommandRepo.add(item.getChangeCommand());
        this.add(item);
    }

    public void removeWith(Column item) {
        this.columnPropertyRepo.remove(item.getOriginal());
        this.columnPropertyRepo.remove(item.getChange());
        this.changeCommandRepo.remove(item.getChangeCommand());
        this.remove(item);
    }
}