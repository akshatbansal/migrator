package migrator.app.domain.table.action;

import java.util.LinkedList;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import migrator.app.domain.column.ColumnContainer;
import migrator.app.domain.column.ColumnRepository;
import migrator.app.domain.index.IndexContainer;
import migrator.app.domain.index.IndexRepository;
import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.model.Table;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.IndexProperty;
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
        this.refreshIndexes(projectContainer, table);
    }

    private void refreshIndexes(ProjectContainer projectContainer, Table activeTable) {
        List<IndexProperty> indexes = projectContainer.getDatabaseStructure().getIndexes(activeTable.getOriginal().getName());

        List<Column> columns = this.columnContainer.columnRepository().byTableProperty(activeTable.getUniqueKey());
        List<Index> dbList = new LinkedList<>();
        for (IndexProperty index : indexes) {
            List<ColumnProperty> indexColumns = new LinkedList<>();
            for (ColumnProperty columnProperty : index.columnsProperty()) {
                for (Column c : columns) {
                    if (c.getOriginal().getName().equals(columnProperty.getName())) {
                        indexColumns.add(c);
                    }
                }
            }
            dbList.add(
                this.indexContainer.indexFactory().createNotChanged(
                    activeTable.getUniqueKey(),
                    index.getName(),
                    indexColumns
                )
            );
        }
 
        for (Index i : dbList) {
            i.setTableId(activeTable.getUniqueKey());
        }
        this.mergeIndex(
            dbList,
            this.indexContainer.indexRepository().findByTable(activeTable.getUniqueKey())
        );

        Platform.runLater(() -> {
            this.indexContainer.indexStore().getList().setAll(
                this.indexContainer.indexRepository().findByTable(activeTable.getUniqueKey())
            );
        });
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
        
        this.mergeColumn(
            dbList,
            this.columnContainer.columnRepository().findByTable(activeTable.getUniqueKey())
        );

        Platform.runLater(() -> {
            this.columnContainer.columnStore().getList().setAll(
                this.columnContainer.columnRepository().findByTable(activeTable.getUniqueKey())
            );
        });
    }

    protected void mergeColumn(List<Column> dbList, List<Column> repoList) {
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

    protected void mergeIndex(List<Index> dbList, List<Index> repoList) {
        ListDiff<Index> diff = new CompareListDiff<>(dbList, repoList, (Index a, Index b) -> {
            if (a.getChangeCommand().isType(ChangeCommand.CREATE)) {
                return a.getChange().getName().equals(
                    b.getChange().getName()
                );
            }
            return a.getOriginal().getName().equals(
                b.getOriginal().getName()
            );
        });

        IndexRepository indexRepository = this.indexContainer.indexRepository();
        for (List<Index> indexPair : diff.getCommon()) {
            if (indexPair.get(1).getChangeCommand().isType(ChangeCommand.CREATE)) {
                indexRepository.removeWith(indexPair.get(1));
                indexRepository.addWith(indexPair.get(0));
            } else {
                indexPair.get(1).updateOriginal(
                    indexPair.get(0).getOriginal()
                );
            }
        }
        for (Index index : diff.getLeftMissing()) {
            if (index.getChangeCommand().isType(ChangeCommand.CREATE)) {
                continue;
            }
            indexRepository.removeWith(index);
        }
        for (Index index : diff.getRightMissing()) {
            indexRepository.addWith(index);
        }
    }
}