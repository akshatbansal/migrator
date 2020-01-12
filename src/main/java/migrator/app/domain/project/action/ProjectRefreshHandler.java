package migrator.app.domain.project.action;

import java.util.LinkedList;
import java.util.List;

import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.table.TableContainer;
import migrator.app.domain.table.TableRepository;
import migrator.app.domain.table.model.Table;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.TableProperty;
import migrator.lib.diff.CompareListDiff;
import migrator.lib.diff.ListDiff;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventHandler;

public class ProjectRefreshHandler implements EventHandler {
    private TableContainer tableContainer;

    public ProjectRefreshHandler(TableContainer tableContainer) {
        this.tableContainer = tableContainer;
    }

    @Override
    public void handle(Event<?> event) {
        ProjectContainer projectContainer = (ProjectContainer) event.getValue();
        if (projectContainer == null) {
            return;
        }

        Project project = projectContainer.getProject();

        List<TableProperty> tables = projectContainer.getDatabaseStructure().getTables();
        List<Table> dbList = new LinkedList<>();
        for (TableProperty t : tables) {
            dbList.add(
                this.tableContainer.tableFactory().createNotChanged(
                    project.getId(),
                    t.getName()
                )
            );
        }

        this.merge(
            dbList,
            this.tableContainer.tableRepository().findByProject(project.getId())
        );

        this.tableContainer.tableStore().getList().setAll(
            this.tableContainer.tableRepository().findByProject(project.getId())
        );
    }

    protected void merge(List<Table> dbList, List<Table> repoList) {
        TableRepository tableRepo = this.tableContainer.tableRepository();
        ListDiff<Table> diff = new CompareListDiff<>(dbList, repoList, (Table a, Table b) -> {
            if (a.getChangeCommand().isType(ChangeCommand.CREATE)) {
                return a.getChange().getName().equals(
                    b.getChange().getName()
                );
            }
            return a.getOriginal().getName().equals(
                b.getOriginal().getName()
            );
        });
        for (List<Table> tablePair : diff.getCommon()) {
            if (tablePair.get(1).getChangeCommand().isType(ChangeCommand.CREATE)) {
                tableRepo.removeWith(tablePair.get(1));
                tableRepo.addWith(tablePair.get(0));
            } else {
                tablePair.get(1).updateOriginal(
                    tablePair.get(0).getOriginal()
                );
            }
        }
        for (Table table : diff.getLeftMissing()) {
            if (table.getChangeCommand().isType(ChangeCommand.CREATE)) {
                continue;
            }
            tableRepo.removeWith(table);
        }
        for (Table table : diff.getRightMissing()) {
            tableRepo.addWith(table);
        }
    }
}