package migrator.app.domain.table.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import migrator.app.database.format.ColumnFormatManager;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.ColumnProperty;

public class Column implements Changable, ColumnChange, ChangeListener<Object>, Serializable {
    private static final long serialVersionUID = 7548712804607681520L;
    protected transient ColumnFormatManager columnFormatManager;
    protected ColumnProperty originalColumn;
    protected ColumnProperty changedColumn;
    protected ChangeCommand changeCommand;
    protected transient StringProperty fullFormatProperty;

    public Column(
        ColumnFormatManager columnFormatManager,
        ColumnProperty originalColumn,
        ColumnProperty changedColumn,
        ChangeCommand changeCommand
    ) {
        this.columnFormatManager = columnFormatManager;
        this.originalColumn = originalColumn;
        this.changedColumn = changedColumn;
        this.changeCommand = changeCommand;
        
        this.initialize();
    }

    protected void initialize() {
        this.fullFormatProperty = new SimpleStringProperty();
        this.refreshFullFormat();

        this.changedColumn.nameProperty().addListener(this);
        this.changedColumn.formatProperty().addListener(this);
        this.changedColumn.defaultValueProperty().addListener(this);
        this.changedColumn.nullProperty().addListener(this);
        this.changedColumn.lengthProperty().addListener(this);
        this.changedColumn.precisionProperty().addListener(this);
        this.changedColumn.signProperty().addListener(this);
        this.changedColumn.autoIncrementProperty().addListener(this);
    }

    public void setColumnFormatManager(ColumnFormatManager columnFormatManager) {
        this.columnFormatManager = columnFormatManager;
        this.refreshFullFormat();
    }

    public StringProperty nameProperty() {
        return this.changedColumn.nameProperty();
    }

    public String getName() {
        return this.nameProperty().get();
    }

    public String getOriginalName() {
        return this.originalColumn.getName();
    }

    public StringProperty formatProperty() {
        return this.changedColumn.formatProperty();
    }

    public String getFormat() {
        return this.formatProperty().get();
    }

    public String getOriginalFormat() {
        return this.originalColumn.getFormat();
    }

    public StringProperty defaultValueProperty() {
        return this.changedColumn.defaultValueProperty();
    }

    public String getDefaultValue() {
        return this.defaultValueProperty().get();
    }

    public String getOriginalDefaultValue() {
        return this.originalColumn.getDefaultValue();
    }

    public Property<Boolean> enableNullProperty() {
        return this.changedColumn.nullProperty();
    }

    public Boolean isNullEnabled() {
        return this.enableNullProperty().getValue();
    }

    public Boolean isNullEnabledOriginal() {
        return this.originalColumn.isNullEnabled();
    }

    public StringProperty fullFormatProperty() {
        return this.fullFormatProperty;
    }

    @Override
    public Property<Boolean> nullProperty() {
        return this.enableNullProperty();
    }

    @Override
    public String getLength() {
        return this.lengthProperty().getValue();
    }

    @Override
    public StringProperty lengthProperty() {
        return this.changedColumn.lengthProperty();
    }

    @Override
    public Boolean hasLengthChanged() {
        return this.hasLengthAttribute() && !this.getLength().equals(this.getOriginal().getLength());
    }

    @Override
    public Boolean isSigned() {
        return this.signProperty().getValue();
    }

    @Override
    public Property<Boolean> signProperty() {
        return this.changedColumn.signProperty();
    }

    @Override
    public Boolean hasSignChanged() {
        return this.hasSignAttribute() && this.isSigned() != this.getOriginal().isSigned();
    }

    @Override
    public Property<Boolean> autoIncrementProperty() {
        return this.changedColumn.autoIncrementProperty();
    }

    @Override
    public Boolean isAutoIncrement() {
        return this.autoIncrementProperty().getValue();
    }

    @Override
    public Boolean hasAutoIncrementAttribute() {
        return this.columnFormatManager.getFormat(this.getFormat()).hasAutoIncrement();
    }

    @Override
    public Boolean hasAutoIncrementChanged() {
        return this.hasAutoIncrementAttribute() && this.isAutoIncrement() != this.getOriginal().isAutoIncrement();
    }

    @Override
    public String getPrecision() {
        return this.precisionProperty().getValue();
    }

    @Override
    public StringProperty precisionProperty() {
        return this.changedColumn.precisionProperty();
    }

    @Override
    public Boolean hasPrecisionChanged() {
        return (this.hasPrecisionAttribute() && !this.getPrecision().equals(this.getOriginal().getPrecision()));
    }

    public ColumnChange getChange() {
        return this;
    }

    public StringProperty changeTypeProperty() {
        return this.changeCommand.typeProperty();
    }

    @Override
    public ChangeCommand getChangeCommand() {
        return this.changeCommand;
    }

    public void delete() {
        this.getChangeCommand().setType(ChangeCommand.DELETE);
    }

    @Override
    public void restore() {
        this.changedColumn.nameProperty().set(this.originalColumn.getName());
        this.changedColumn.formatProperty().set(this.originalColumn.getFormat());
        this.changedColumn.defaultValueProperty().set(this.originalColumn.getDefaultValue());
        this.changedColumn.nullProperty().setValue(this.originalColumn.isNullEnabled());
        this.changedColumn.lengthProperty().set(this.originalColumn.getLength());
        this.changedColumn.precisionProperty().set(this.originalColumn.getPrecision());
        this.changedColumn.signProperty().setValue(this.originalColumn.isSigned());
        this.changedColumn.autoIncrementProperty().setValue(
            this.originalColumn.isAutoIncrement()
        );

        this.changeCommand.setType(ChangeCommand.NONE);
        
    }

    @Override
    public ChangeCommand getCommand() {
        return this.getChangeCommand();
    }

    @Override
    public ColumnProperty getOriginal() {
        return this.originalColumn;
    }

    @Override
    public Boolean hasDefaultValueChanged() {
        return !this.getDefaultValue().equals(this.getOriginal().getDefaultValue());
    }

    @Override
    public Boolean hasFormatChanged() {
        return !this.getFormat().equals(this.getOriginal().getFormat());
    }

    @Override
    public Boolean hasNameChanged() {
        return !this.getName().equals(this.getOriginal().getName());
    }

    @Override
    public Boolean hasNullEnabledChanged() {
        return this.isNullEnabled() != this.getOriginal().isNullEnabled();
    }

    @Override
    public Boolean hasLengthAttribute() {
        return this.columnFormatManager.getFormat(this.getFormat())
            .hasLength();
    }

    @Override
    public Boolean hasPrecisionAttribute() {
        return this.columnFormatManager.getFormat(this.getFormat())
            .hasPrecision();
    }

    @Override
    public Boolean hasSignAttribute() {
        return this.columnFormatManager.getFormat(this.getFormat())
            .isSigned();
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        this.refreshFullFormat();
        if (this.changeCommand.isType(ChangeCommand.DELETE)) {
            return;
        }
        if (this.changeCommand.isType(ChangeCommand.CREATE)) {
            return;
        }
        
        if (this.hasNameChanged() || this.hasFormatChanged() || this.hasDefaultValueChanged() || this.hasNullEnabledChanged() || this.hasLengthChanged() || this.hasPrecisionChanged() || this.hasSignChanged() || this.hasAutoIncrementChanged()) {
            this.changeCommand.typeProperty().set(ChangeCommand.UPDATE);
            return;
        }
        this.changeCommand.typeProperty().set(ChangeCommand.NONE);
    }

    protected void refreshFullFormat() {
        if (this.columnFormatManager == null) {
            return;
        }
        this.fullFormatProperty.set(
            this.columnFormatManager.getFormatter(this.getFormat()).format(this.getLength(), this.getPrecision())
        );
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.initialize();
    }
}