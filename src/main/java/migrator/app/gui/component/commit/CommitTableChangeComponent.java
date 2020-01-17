package migrator.app.gui.component.commit;

import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import migrator.app.gui.component.Component;
import migrator.app.gui.component.ComponentFactories;
import migrator.app.gui.component.SimpleComponent;
import migrator.app.gui.component.list.VerticalListComponent;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.IndexChange;
import migrator.app.migration.model.TableChange;

public class CommitTableChangeComponent extends SimpleComponent implements Component {
    protected TableChange tableChange;
    private ObservableList<Component> changeLines;

    @FXML protected Text title;
    @FXML protected VBox changes;

    public CommitTableChangeComponent(ComponentFactories componentFactories) {
        super();
        this.changeLines = FXCollections.observableArrayList();

        this.loadFxml("/layout/project/commit/tablechange.fxml");

        VerticalListComponent<Component> verticalListComponent = componentFactories.createVerticalList();
        verticalListComponent.clearSpacing();
        verticalListComponent.bind(changeLines);

        this.changes.getChildren().setAll(
            verticalListComponent.getNode()
        );
    }

    public void set(TableChange tableChange) {
        this.tableChange = tableChange;

        if (tableChange.getCommand().isType(ChangeCommand.CREATE)) {
            this.title.textProperty().bind(
                tableChange.nameProperty()
            );
        } else {
            this.title.textProperty().bind(
                tableChange.getOriginal().nameProperty()
            );
        }
        

        this.render();
    }

    private void render() {
        List<Component> components = new LinkedList<>();
        TableChangeTextComponent changeTextComponent = new TableChangeTextComponent();
        changeTextComponent.set(tableChange);
        if (!changeTextComponent.isEmpty()) {
            components.add(changeTextComponent);
        }

        if (!tableChange.getCommand().isType(ChangeCommand.DELETE)) {
            for (ColumnChange columnChange : tableChange.getColumnsChanges()) {
                ColumnChangeTextComponent columnChangeTextComponent = new ColumnChangeTextComponent();
                columnChangeTextComponent.set(columnChange);
                if (columnChangeTextComponent.isEmpty()) {
                    continue;
                }
                components.add(columnChangeTextComponent);
            }
            for (IndexChange indexChange : tableChange.getIndexesChanges()) {
                IndexChangeTextComponent indexChangeTextComponent = new IndexChangeTextComponent();
                indexChangeTextComponent.set(indexChange);
                if (indexChangeTextComponent.isEmpty()) {
                    continue;
                }
                components.add(indexChangeTextComponent);
            }
        }
        this.changeLines.setAll(components);
    }
}