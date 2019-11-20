package migrator.app.domain.table.model;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.IndexChange;
import migrator.app.migration.model.IndexProperty;
import migrator.app.migration.model.Modification;

public class Index implements Changable, IndexChange, Modification<IndexProperty> {
    protected String id;
    protected String tableId;
    protected IndexProperty originalIndex;
    protected IndexProperty changedIndex;
    protected ChangeCommand changeCommand;

    public Index(
        String id,
        String tableId,
        IndexProperty originalIndex,
        IndexProperty changedIndex,
        ChangeCommand changeCommand
    ) {
        this.id = id;
        this.tableId = tableId;
        this.originalIndex = originalIndex;
        this.changedIndex = changedIndex;
        this.changeCommand = changeCommand;
    }

    @Override
    public String getUniqueKey() {
        return this.id;
    }

    public String getTableId() {
        return this.tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public StringProperty nameProperty() {
        return this.changedIndex.nameProperty();
    }

    public String getName() {
        return this.nameProperty().get();
    }

    public String getOriginalName() {
        return this.originalIndex.getName();
    }

    public ObservableList<StringProperty> columnsProperty() {
        return this.changedIndex.columnsProperty();
    }

    public StringProperty columnsStringProperty() {
        return this.changedIndex.columnsStringProperty();
    }

    public StringProperty originalColumnsStringProperty() {
        return this.originalIndex.columnsStringProperty();
    }

    public ObservableList<StringProperty> originalColumnsProperty() {
        return this.originalIndex.columnsProperty();
    }

    public StringProperty originalColumnProperty(int index) {
        return this.originalColumnsProperty().get(index);
    }

    public StringProperty columnProperty(int index) {
        return this.columnsProperty().get(index);
    }

    public StringProperty columnPropertyOrCreate(int index) {
        this.fillColumnsTo(index);
        return this.columnProperty(index);
    }

    @Override
    public ChangeCommand getChangeCommand() {
        return this.changeCommand;
    }

    public IndexChange getChange() {
        return this;
    }

    protected void fillColumnsTo(int index) {
        while (this.columnsProperty().size() <= index) {
            this.changedIndex.addColumn("");
        }
    }

    public void delete() {
        this.getChangeCommand().setType(ChangeCommand.DELETE);
    }

    public void restore() {
        this.changedIndex.nameProperty().set(this.originalIndex.getName());
        this.changedIndex.columnsProperty().setAll(this.originalColumnsProperty());
        this.changeCommand.setType(ChangeCommand.NONE);
    }

    public StringProperty changeTypeProperty() {
        return this.getChangeCommand().typeProperty();
    }

    @Override
    public ChangeCommand getCommand() {
        return this.changeCommand;
    }

    @Override
    public void addColumn(String columnName) {
        this.changedIndex.addColumn(columnName);
    }

    @Override
    public IndexProperty getOriginal() {
        return this.originalIndex;
    }

    public void updateOriginal(IndexProperty indexProperty) {
        this.getOriginal().nameProperty().set(
            indexProperty.getName()
        );
        this.getChange().nameProperty().set(
            indexProperty.getName()
        );

        this.getOriginal().columnsProperty().setAll(
            indexProperty.columnsProperty()
        );
        this.getChange().columnsProperty().setAll(
            indexProperty.columnsProperty()
        );
    }
}