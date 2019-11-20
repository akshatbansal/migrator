package migrator.app.domain.index.service;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import migrator.app.domain.table.model.Index;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.IndexProperty;
import migrator.app.migration.model.SimpleIndexProperty;
import migrator.lib.uid.Generator;

public class IndexFactory {
    protected Generator idGenerator;

    public IndexFactory(Generator idGenerator) {
        this.idGenerator = idGenerator;
    }

    protected IndexProperty simpleIndexProperty(String name, List<StringProperty> columns) {
        return new SimpleIndexProperty(
            this.idGenerator.next(),
            name,
            columns
        );
    }

    public Index createNotChanged(String tableId, String indexName, List<String> columns) {
        List<StringProperty> columnsProperty = new ArrayList<>();
        for (String column : columns) {
            columnsProperty.add(new SimpleStringProperty(column));
        }
        return new Index(
            this.idGenerator.next(),
            tableId,
            this.simpleIndexProperty(indexName, columnsProperty),
            this.simpleIndexProperty(indexName, columnsProperty),
            new ChangeCommand(this.idGenerator.next(), ChangeCommand.NONE)
        );
    }

    public Index createWithCreateChange(String tableId, String indexName) {
        return this.createWithCreateChange(tableId, indexName, new ArrayList<>());
    }

    public Index createWithCreateChange(String tableId, String indexName, List<StringProperty> columns) {
        return new Index(
            this.idGenerator.next(),
            tableId,
            this.simpleIndexProperty(indexName, new ArrayList<>(columns)),
            this.simpleIndexProperty(indexName, new ArrayList<>(columns)),
            new ChangeCommand(this.idGenerator.next(), ChangeCommand.CREATE)
        );
    }
}