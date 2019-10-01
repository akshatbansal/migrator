package migrator.table.model;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import migrator.migration.ChangeCommand;
import migrator.migration.IndexChange;
import migrator.migration.IndexProperty;

public class Index implements Changable {
    protected IndexProperty originalIndex;
    protected IndexProperty changedIndex;
    protected IndexChange change;

    public Index(IndexProperty originalIndex, IndexProperty changedIndex, IndexChange indexChange) {
        this.originalIndex = originalIndex;
        this.changedIndex = changedIndex;
        this.change = indexChange;
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
        // this.fillColumnsTo(index);
        return this.originalColumnsProperty().get(index);
    }

    public StringProperty columnProperty(int index) {
        // this.fillColumnsTo(index);
        return this.columnsProperty().get(index);
    }

    @Override
    public ChangeCommand getChangeCommand() {
        return this.change.getCommand();
    }

    public IndexChange getChange() {
        return this.change;
    }

    // protected void fillColumnsTo(int index) {
    //     while (this.columnsProperty().size() <= index) {
    //         this.columnsProperty().add(this.addColumn(""));
    //     }
    // }

    // public StringProperty addColumn(String column) {
        // StringProperty newColumn = new SimpleStringProperty(column);
        // newColumn.addListener((ObservableValue<? extends String> obs, String oldValue, String newValue) -> {
        //     this.onColumnsChange();
        // });
        // return newColumn;
    // }
}