package migrator.app.domain.table.action;

import java.util.LinkedList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import migrator.app.domain.column.ColumnContainer;
import migrator.app.domain.column.ColumnRepository;
import migrator.app.domain.index.IndexContainer;
import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Table;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnProperty;
import migrator.lib.diff.CompareListDiff;
import migrator.lib.diff.ListDiff;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventHandler;

public class TableRefreshHandler implements EventHandler {
    private ObjectProperty<ProjectContainer> projectContainerProperty;
    private ColumnContainer columnContainer;
    private IndexContainer indexContainer;

    public TableRefreshHandler(
        ObjectProperty<ProjectContainer> projectContainerProperty,
        ColumnContainer columnContainer,
        IndexContainer indexContainer
    ) {
        this.projectContainerProperty = projectContainerProperty;
        this.columnContainer = columnContainer;
        this.indexContainer = indexContainer;
    }

    @Override
    public void handle(Event<?> event) {
        Table table = (Table) event.getValue();
        ProjectContainer projectContainer = this.projectContainerProperty.get();

        this.refreshColumns(projectContainer, table);
    }

    private void refreshColumns(ProjectContainer projectContainer, Table activeTable) {
        List<ColumnProperty> columns = projectContainer.getDatabaseStructure().getColumns(activeTable.getOriginal().getName());

        List<Column> dbList = new LinkedList<>();
        for (ColumnProperty column : columns) {
            dbList.add(
                this.columnContainer.columnFactory().createNotChanged(
                    activeTable.getUniqueKey(),
                    column.getName(),
                    column.getFormat(),
                    column.getDefaultValue(),
                    column.isNullEnabled(),
                    column.getLength(),
                    column.isSigned(),
                    column.getPrecision(),
                    column.isAutoIncrement()
                )
            );
        }
        
        this.merge(
            dbList,
            this.columnContainer.columnRepository().findByTable(activeTable.getUniqueKey())
        );

        this.columnContainer.columnStore().getList().setAll(
            this.columnContainer.columnRepository().findByTable(activeTable.getUniqueKey())
        );
    }

    protected void merge(List<Column> dbList, List<Column> repoList) {
        ListDiff<Column> diff = new CompareListDiff<>(dbList, repoList, (Column a, Column b) -> {
            if (a.getChangeCommand().isType(ChangeCommand.CREATE)) {
                return a.getChange().getName().equals(
                    b.getChange().getName()
                );

            }
            return a.getOriginal().getName().equals(
                b.getOriginal().getName()
            );
        });

        ColumnRepository columnRepository = this.columnContainer.columnRepository();
        for (List<Column> columnPair : diff.getCommon()) {
            if (columnPair.get(1).getChangeCommand().isType(ChangeCommand.CREATE)) {
                columnRepository.removeWith(columnPair.get(1));
                columnRepository.addWith(columnPair.get(0));
            } else {
                columnPair.get(1).updateOriginal(
                    columnPair.get(0).getOriginal()
                );
            }
        }
        for (Column column : diff.getLeftMissing()) {
            if (column.getChangeCommand().isType(ChangeCommand.CREATE)) {
                continue;
            }
            columnRepository.removeWith(column);
        }
        for (Column column : diff.getRightMissing()) {
            columnRepository.addWith(column);
        }
    }
}