package migrator.ext.javafx.project.component;

import java.util.LinkedList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.IndexChange;
import migrator.app.migration.model.TableChange;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;
import migrator.ext.javafx.project.component.commit.ColumnChangeTextComponent;
import migrator.ext.javafx.project.component.commit.IndexChangeTextComponent;
import migrator.ext.javafx.project.component.commit.TableChangeTextComponent;

public class JavafxCommitTableChange extends ViewComponent {
    protected TableChange tableChange;

    @FXML protected Text title;
    @FXML protected VBox changes;

    public JavafxCommitTableChange(TableChange tableChange, ViewLoader viewLoader) {
        super(viewLoader);

        this.loadView("/layout/project/commit/tablechange.fxml");

        this.title.textProperty().set(
            tableChange.getName()
        );

        List<Node> nodes = new LinkedList<>();
        TableChangeTextComponent changeTextComponent = new TableChangeTextComponent(tableChange);
        if (changeTextComponent.getContent() != null) {
            nodes.add((Node) changeTextComponent.getContent());
        }
        if (!tableChange.getCommand().isType(ChangeCommand.DELETE)) {
            for (ColumnChange columnChange : tableChange.getColumnsChanges()) {
                ColumnChangeTextComponent columnChangeTextComponent = new ColumnChangeTextComponent(columnChange);
                if (columnChangeTextComponent.getContent() == null) {
                    continue;
                }
                nodes.add(
                    (Node) columnChangeTextComponent.getContent()
                );
            }
            for (IndexChange indexChange : tableChange.getIndexesChanges()) {
                IndexChangeTextComponent indexChangeTextComponent = new IndexChangeTextComponent(indexChange);
                if (indexChangeTextComponent.getContent() == null) {
                    continue;
                }
                nodes.add(
                    (Node) indexChangeTextComponent.getContent()
                );
            }
        }
        changes.getChildren().setAll(nodes);
    }


}