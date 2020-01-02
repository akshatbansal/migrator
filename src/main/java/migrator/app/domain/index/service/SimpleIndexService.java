package migrator.app.domain.index.service;

import java.util.LinkedList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import migrator.app.domain.column.ColumnRepository;
import migrator.app.domain.index.IndexRepository;
import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.project.service.ProjectService;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableActiveState;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.IndexProperty;
import migrator.lib.modelstorage.ActiveState;
import migrator.lib.diff.CompareListDiff;
import migrator.lib.diff.ListDiff;

public class SimpleIndexService implements IndexService {
    protected IndexFactory indexFactory;
    protected IndexRepository indexRepository;
    protected ColumnRepository columnRepository;
    protected ActiveState<Index> activeState;
    protected TableActiveState tableActiveState;
    protected ProjectService projectService;

    protected ChangeListener<Table> changeTableListener;

    public SimpleIndexService(
        IndexFactory indexFactory,
        IndexRepository indexRepository,
        ActiveState<Index> activeState,
        TableActiveState tableActiveState,
        ProjectService projectService,
        ColumnRepository columnRepository
    ) {
        this.activeState = activeState;
        this.columnRepository = columnRepository;
        this.indexFactory = indexFactory;
        this.indexRepository = indexRepository;
        this.tableActiveState = tableActiveState;
        this.projectService = projectService;

        this.changeTableListener = (ObservableValue<? extends Table> observable, Table oldValue, Table newValue) -> {
            this.onTableChange(newValue);
        };
    }

    @Override
    public void start() {
        this.tableActiveState.getActive()
            .addListener(this.changeTableListener);
    }

    @Override
    public void stop() {
        this.tableActiveState.getActive()
            .removeListener(this.changeTableListener);
    }

    protected void onTableChange(Table activeTable) {
        if (activeTable == null) {
            return;
        }
        ProjectContainer projectContainer = this.projectService.getOpened().get();

        List<IndexProperty> indexes = projectContainer.getDatabaseStructure().getIndexes(activeTable.getOriginal().getName());

        List<Column> columns = this.columnRepository.byTableProperty(activeTable.getUniqueKey());
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
                this.indexFactory.createNotChanged(
                    activeTable.getUniqueKey(),
                    index.getName(),
                    indexColumns
                )
            );
        }
 
        for (Index i : dbList) {
            i.setTableId(activeTable.getUniqueKey());
        }
        this.merge(
            dbList,
            this.indexRepository.findByTable(activeTable.getUniqueKey())
        );

        this.activeState.setListAll(
            this.indexRepository.findByTable(activeTable.getUniqueKey())
        );
    }

    @Override
    public void add(Index index) {
        this.activeState.add(index);
    }

    @Override
    public ObservableList<Index> getAll() {
        return this.activeState.getList();
    }

    @Override
    public void remove(Index index) {
        this.activeState.remove(index);
    }

    @Override
    public void activate(Index index) {
        this.activeState.activate(index);
    }

    @Override
    public void addAndActivate(Index index) {
        this.add(index);
        this.activate(index);
    }

    protected void merge(List<Index> dbList, List<Index> repoList) {
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
        for (List<Index> indexPair : diff.getCommon()) {
            if (indexPair.get(1).getChangeCommand().isType(ChangeCommand.CREATE)) {
                this.indexRepository.removeWith(indexPair.get(1));
                this.indexRepository.addWith(indexPair.get(0));
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
            this.indexRepository.removeWith(index);
        }
        for (Index index : diff.getRightMissing()) {
            this.indexRepository.addWith(index);
        }
    }
}