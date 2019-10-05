package migrator.app.domain.table.service;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import migrator.app.domain.table.model.Index;
import migrator.migration.ChangeCommand;
import migrator.migration.IndexProperty;
import migrator.migration.SimpleIndexChange;
import migrator.migration.SimpleIndexProperty;

public class IndexFactory {
    protected IndexProperty simpleIndexProperty(String name, List<StringProperty> columns) {
        return new SimpleIndexProperty(name, columns);
    }

    public Index createNotChanged(String indexName, List<String> columns) {
        List<StringProperty> columnsProperty = new ArrayList<>();
        for (String column : columns) {
            columnsProperty.add(new SimpleStringProperty(column));
        }
        return new Index(
            this.simpleIndexProperty(indexName, columnsProperty),
            this.simpleIndexProperty(indexName, columnsProperty),
            new SimpleIndexChange(this.simpleIndexProperty(null, new ArrayList<>()), new ChangeCommand(ChangeCommand.NONE))
        );
    }

    public Index createWithCreateChange(String indexName) {
        return new Index(
            this.simpleIndexProperty(indexName, new ArrayList<>()),
            this.simpleIndexProperty(indexName, new ArrayList<>()),
            new SimpleIndexChange(this.simpleIndexProperty(null, new ArrayList<>()), new ChangeCommand(ChangeCommand.CREATE))
        );
    }
}