package migrator.table.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.database.service.ServerConnection;
import migrator.database.service.ServerKit;
import migrator.table.model.Index;
import migrator.table.model.Table;

public class IndexService {
    protected ObservableList<Index> list;
    protected ObjectProperty<Index> selected;
    protected ServerKit serverKit;

    public IndexService(TableService tableService, ServerKit serverKit) {
        this.serverKit = serverKit;
        this.list = FXCollections.observableArrayList();
        this.selected = new SimpleObjectProperty<>();

        tableService.getSelected().addListener((ObservableValue<? extends Table> observable, Table oldValue, Table newValue) -> {
            this.onTableSelect(newValue);
        });
    }

    public ObservableList<Index> getList() {
        return this.list;
    }

    public ObjectProperty<Index> getSelected() {
        return this.selected;
    }

    protected void onTableSelect(Table table) {
        if (table == null) {
            this.list.clear();
            return;
        }

        ServerConnection serverConnection = this.serverKit.createConnection(table.getDatabase());
        Map<String, Index> indexes = new LinkedHashMap<>();
        for (List<String> indexValues : serverConnection.getIndexes(table.getName())) {
            String indexName = indexValues.get(0);
            if (!indexes.containsKey(indexName)) {
                indexes.put(indexName, new Index(indexName));
            }
            Index index = indexes.get(indexName);
            index.columnsProperty().add(indexValues.get(1));
        }
        this.list.setAll(indexes.values());
    }
}