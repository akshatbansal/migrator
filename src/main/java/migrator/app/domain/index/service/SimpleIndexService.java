package migrator.app.domain.index.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import migrator.app.database.driver.DatabaseDriver;
import migrator.app.database.driver.DatabaseDriverManager;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableActiveState;
import migrator.app.migration.model.ChangeCommand;
import migrator.lib.modelstorage.ActiveState;

public class SimpleIndexService implements IndexService {
    protected IndexFactory indexFactory;
    protected IndexRepository indexRepository;
    protected ActiveState<Index> activeState;
    protected DatabaseDriverManager databaseDriverManager;
    protected TableActiveState tableActiveState;

    protected ChangeListener<Table> changeTableListener;

    public SimpleIndexService(
        IndexFactory indexFactory,
        IndexRepository indexRepository,
        ActiveState<Index> activeState,
        TableActiveState tableActiveState,
        DatabaseDriverManager databaseDriverManager
    ) {
        this.activeState = activeState;
        this.databaseDriverManager = databaseDriverManager;
        this.indexFactory = indexFactory;
        this.indexRepository = indexRepository;
        this.tableActiveState = tableActiveState;

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
        String repositryKey = activeTable.getProject().getName() + "." + activeTable.getOriginalName();

        DatabaseDriver databaseDriver  = this.databaseDriverManager
            .createDriver(activeTable.getProject().getDatabase());
        databaseDriver.connect();

        Map<String, List<String>> indexColumnsMap = new LinkedHashMap<>();
        for (List<String> indexValues : databaseDriver.getIndexes(activeTable.getOriginalName())) {
            String indexName = indexValues.get(0);
            if (!indexColumnsMap.containsKey(indexName)) {
                indexColumnsMap.put(indexName, new ArrayList<>());
            }
            indexColumnsMap.get(indexName).add(indexValues.get(1));
        }

        List<Index> indexes = new ArrayList<>();
        Iterator<Entry<String, List<String>>> entryIterator = indexColumnsMap.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Entry<String, List<String>> entry = entryIterator.next();
            Index dbValue = this.indexFactory.createNotChanged(
                entry.getKey(), 
                entry.getValue()
            );

            indexes.add(
                this.mergeColumn(dbValue, this.indexRepository.get(repositryKey, dbValue.getOriginal().getName()))
            );
        }

        for (Index index : this.indexRepository.getList(repositryKey)) {
            if (!index.getCommand().isType(ChangeCommand.CREATE)) {
                continue;
            }
            indexes.add(index);
        }
        this.indexRepository.setList(repositryKey, indexes);

        this.activeState.setListAll(
            this.indexRepository.getList(repositryKey)
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

    protected Index mergeColumn(Index dbValue, Index repositoryValue) {
        if (dbValue == null) {
            if (repositoryValue.getCommand().isType(ChangeCommand.CREATE)) {
                return repositoryValue;
            }
            return null;
        }

        if (repositoryValue == null) {
            return dbValue;
        }

        repositoryValue.getOriginal().nameProperty().set(dbValue.getOriginal().getName());
        repositoryValue.getOriginal().columnsProperty().setAll(
            dbValue.getOriginal().columnsProperty()
        );
        return repositoryValue;
    }
}