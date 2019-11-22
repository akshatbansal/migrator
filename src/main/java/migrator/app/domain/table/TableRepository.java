package migrator.app.domain.table;

import java.util.List;

import migrator.app.domain.table.model.Table;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.TableProperty;
import migrator.lib.repository.RepositoryMapIndex;
import migrator.lib.repository.UniqueRepository;

public class TableRepository extends UniqueRepository<Table> {
    protected UniqueRepository<TableProperty> tablePropertyRepo;
    protected UniqueRepository<ChangeCommand> changeCommandRepo;

    public TableRepository(UniqueRepository<TableProperty> tablePropertyRepo, UniqueRepository<ChangeCommand> changeCommandRepo) {
        super();
        this.changeCommandRepo = changeCommandRepo;
        this.tablePropertyRepo = tablePropertyRepo;
        this.indexes.add(
            new RepositoryMapIndex<Table>((Table table) -> {
                return table.getProjectId();
            })
        );
    }

    public List<Table> findByProject(String projectId) {
        return this.indexes.get(0).filter(projectId);
    }

    public void addWith(Table item) {
        this.changeCommandRepo.add(item.getChangeCommand());
        this.tablePropertyRepo.add(item.getOriginal());
        this.tablePropertyRepo.add(item.getChange());
        this.add(item);
    }

    public void removeWith(Table item) {
        this.changeCommandRepo.remove(item.getChangeCommand());
        this.tablePropertyRepo.remove(item.getOriginal());
        this.tablePropertyRepo.remove(item.getChange());
        this.remove(item);
    }
}