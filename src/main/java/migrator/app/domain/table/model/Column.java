package migrator.app.domain.table.model;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import migrator.app.gui.GuiModel;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.Modification;
import migrator.lib.repository.UniqueItem;

public class Column extends GuiModel implements Changable, ColumnChange, ChangeListener<Object>, UniqueItem, Modification<ColumnProperty> {
    protected String id;
    protected String tableId;
    protected ColumnProperty originalColumn;
    protected ColumnProperty changedColumn;
    protected ChangeCommand changeCommand;
    protected StringProperty fullFormatProperty;

    public Column(
        String id,
        String tableId,
        ColumnProperty originalColumn,
        ColumnProperty changedColumn,
        ChangeCommand changeCommand
    ) {
        super();
        this.id = id;
        this.tableId = tableId;
        this.originalColumn = originalColumn;
        this.changedColumn = changedColumn;
        this.changeCommand = changeCommand;
        
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

        this.attribute("length").addListener((obs, ol, ne) -> {
            this.refreshFullFormat();
        });
        this.attribute("precision").addListener((obs, ol ,ne) -> {
            this.refreshFullFormat();
        });
    }

    @Override
    public String getUniqueKey() {
        return this.id;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTableId() {
        return this.tableId;
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
        return this.attribute("autoIncrement").get();
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

    @Override
    public ColumnProperty getModification() {
        return this.changedColumn;
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
        if (this.getDefaultValue() == null && this.getOriginal().getDefaultValue() == null) {
            return false;
        }
        return this.getDefaultValue() == null || !this.getDefaultValue().equals(this.getOriginal().getDefaultValue());
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
        return this.attribute("length").get();
    }

    @Override
    public Boolean hasPrecisionAttribute() {
        return this.attribute("precision").get();
    }

    @Override
    public Boolean hasSignAttribute() {
        return this.attribute("sign").get();
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

    public void updateOriginal(ColumnProperty columnProperty) {
        if (!this.hasNameChanged()) {
            this.nameProperty().setValue(columnProperty.getName());    
        }
        this.getOriginal().nameProperty().set(columnProperty.getName());

        if (!this.hasFormatChanged()) {
            this.formatProperty().setValue(columnProperty.getFormat());
        }
        this.getOriginal().formatProperty().set(columnProperty.getFormat());

        if (!this.hasDefaultValueChanged()) {
            this.defaultValueProperty().setValue(columnProperty.getDefaultValue());    
        }
        this.getOriginal().defaultValueProperty().set(columnProperty.getDefaultValue());

        if (!this.hasNullEnabledChanged()) {
            this.nullProperty().setValue(columnProperty.isNullEnabled());    
        }
        this.getOriginal().nullProperty().setValue(columnProperty.isNullEnabled());

        if (!this.hasLengthChanged()) {
            this.lengthProperty().setValue(columnProperty.getLength());    
        }
        this.getOriginal().lengthProperty().setValue(columnProperty.getLength());

        if (!this.hasSignChanged()) {
            this.signProperty().setValue(columnProperty.isSigned());
        }
        this.getOriginal().signProperty().setValue(columnProperty.isSigned());

        if (!this.hasPrecisionChanged()) {
            this.precisionProperty().setValue(columnProperty.getPrecision());
        }
        this.getOriginal().precisionProperty().setValue(columnProperty.getPrecision());
    }

    @Override
    public String toString() {
        return this.getChange().getName();
    }

    private void refreshFullFormat() {
        String fullFormat = this.formatProperty().get();
        if (this.attribute("length").get()) {
            fullFormat += "(" + this.getLength();
            if (this.attribute("precision").get()) {
                fullFormat += "," + this.getPrecision();
            }
            fullFormat += ")";
        }
        this.fullFormatProperty.set(fullFormat);
    }
}