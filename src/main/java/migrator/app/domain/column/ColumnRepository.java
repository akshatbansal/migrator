package migrator.app.domain.column;

import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.domain.table.model.Column;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnProperty;
import migrator.lib.repository.UniqueRepository;

public class ColumnRepository extends UniqueRepository<Column> {
    protected UniqueRepository<ColumnProperty> columnPropertyRepo;
    protected UniqueRepository<ChangeCommand> changeCommandRepo;
    protected Map<String, ObservableList<Column>> byTable;

    public ColumnRepository(UniqueRepository<ColumnProperty> columnPropertyRepo, UniqueRepository<ChangeCommand> changeCommandRepo) {
        super();
        this.columnPropertyRepo = columnPropertyRepo;
        this.changeCommandRepo = changeCommandRepo;
        this.byTable = new Hashtable<>();
    }

    public List<Column> findByTable(String tableId) {
        return this.byTableProperty(tableId);
    }

    public ObservableList<Column> byTableProperty(String tableId) {
        if (!this.byTable.containsKey(tableId)) {
            this.byTable.put(tableId, FXCollections.observableArrayList());
        }
        return this.byTable.get(tableId);
    }

    @Override
    public void add(Column item) {
        super.add(item);
        this.byTableProperty(item.getTableId()).add(item);
    }

    @Override
    public void addAll(Collection<Column> items) {
        super.addAll(items);
        if (items == null) {
            return;
        }
        for (Column item : items) {
            this.byTableProperty(item.getTableId()).add(item);
        }
    }

    @Override
    public void remove(Column item) {
        super.remove(item);
        this.byTableProperty(item.getTableId()).remove(item);
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