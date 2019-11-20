package migrator.app.domain.index;

import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.domain.table.model.Index;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.IndexProperty;
import migrator.lib.repository.UniqueRepository;

public class IndexRepository extends UniqueRepository<Index> {
    protected UniqueRepository<IndexProperty> indexPropertyRepo;
    protected UniqueRepository<ChangeCommand> changeCommandRepo;
    protected Map<String, ObservableList<Index>> byTable;

    public IndexRepository(UniqueRepository<IndexProperty> indexPropertyRepo, UniqueRepository<ChangeCommand> changeCommandRepo) {
        super();
        this.changeCommandRepo = changeCommandRepo;
        this.indexPropertyRepo = indexPropertyRepo;
        this.byTable = new Hashtable<>();
    }

    public List<Index> findByTable(String tableId) {
        return this.byTableProperty(tableId);
    }

    public ObservableList<Index> byTableProperty(String tableId) {
        if (!this.byTable.containsKey(tableId)) {
            this.byTable.put(tableId, FXCollections.observableArrayList());
        }
        return this.byTable.get(tableId);
    }

    @Override
    public void add(Index item) {
        super.add(item);
        this.byTableProperty(item.getTableId()).add(item);
    }

    @Override
    public void addAll(Collection<Index> items) {
        super.addAll(items);
        if (items == null) {
            return;
        }
        for (Index item : items) {
            this.byTableProperty(item.getTableId()).add(item);
        }
    }

    @Override
    public void remove(Index item) {
        super.remove(item);
        this.byTableProperty(item.getTableId()).remove(item);
    }

    public void addWith(Index item) {
        this.changeCommandRepo.add(item.getChangeCommand());
        this.indexPropertyRepo.add(item.getOriginal());
        this.indexPropertyRepo.add(item.getChange());
        this.add(item);
    }

    public void removeWith(Index item) {
        this.changeCommandRepo.remove(item.getChangeCommand());
        this.indexPropertyRepo.remove(item.getOriginal());
        this.indexPropertyRepo.remove(item.getChange());
        this.remove(item);
    }
}