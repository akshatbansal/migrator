package migrator.app.domain.index;

import java.util.LinkedList;
import java.util.List;

import javafx.collections.ObservableList;
import migrator.app.domain.table.model.Index;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.IndexProperty;
import migrator.lib.repository.RepositoryMapIndex;
import migrator.lib.repository.UniqueRepository;

public class IndexRepository extends UniqueRepository<Index> {
    protected UniqueRepository<IndexProperty> indexPropertyRepo;
    protected UniqueRepository<ChangeCommand> changeCommandRepo;

    public IndexRepository(UniqueRepository<IndexProperty> indexPropertyRepo, UniqueRepository<ChangeCommand> changeCommandRepo) {
        super();
        this.changeCommandRepo = changeCommandRepo;
        this.indexPropertyRepo = indexPropertyRepo;
        this.indexes.add(
            new RepositoryMapIndex<Index>((Index index) -> {
                return index.getTableId();
            })
        );
    }

    public List<Index> findByTable(String tableId) {
        return this.byTableProperty(tableId);
    }

    public ObservableList<Index> byTableProperty(String tableId) {
        return this.indexes.get(0).filter(tableId);
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

    public void addAllWith(List<Index> items) {
        List<IndexProperty> properties = new LinkedList<>();
        List<ChangeCommand> commands = new LinkedList<>();
        for (Index column : items) {
            properties.add(column.getChange());
            properties.add(column.getOriginal());
            commands.add(column.getChangeCommand());
        }
        this.indexPropertyRepo.addAll(properties);
        this.changeCommandRepo.addAll(commands);
        this.addAll(items);
    }

    public void removeAllWith(List<Index> items) {
        List<IndexProperty> properties = new LinkedList<>();
        List<ChangeCommand> commands = new LinkedList<>();
        for (Index column : items) {
            properties.add(column.getChange());
            properties.add(column.getOriginal());
            commands.add(column.getChangeCommand());
        }
        this.indexPropertyRepo.removeAll(properties);
        this.changeCommandRepo.removeAll(commands);
        this.removeAll(items);
    }
}