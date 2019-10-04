package migrator.migration;

import java.util.Arrays;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class SimpleIndexChange implements IndexChange {
    protected IndexProperty index;
    protected ChangeCommand command;

    public SimpleIndexChange(IndexProperty index, ChangeCommand command) {
        this.index = index;
        this.command = command;

        UpdateCommandListener updateCommandListener = new UpdateCommandListener(
            this.command,
            Arrays.asList(
                this.index.nameProperty(),
                this.index.columnsStringProperty()
            )
        );

        this.index.nameProperty().addListener(updateCommandListener);
        this.index.columnsStringProperty().addListener(updateCommandListener);
    }

    @Override
    public String getName() {
        return this.index.getName();
    }

    @Override
    public StringProperty nameProperty() {
        return this.index.nameProperty();
    }

    @Override
    public ObservableList<StringProperty> columnsProperty() {
        return this.index.columnsProperty();
    }

    @Override
    public StringProperty columnsStringProperty() {
        return this.index.columnsStringProperty();
    }

    @Override
    public ChangeCommand getCommand() {
        return this.command;
    }

    @Override
    public void clear() {
        this.command.setType(ChangeCommand.NONE);
    }

    @Override
    public void addColumn(String columnName) {
        this.index.addColumn(columnName);
    }
}