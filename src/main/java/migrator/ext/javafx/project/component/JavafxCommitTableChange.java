package migrator.ext.javafx.project.component;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import migrator.app.migration.model.TableChange;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;
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

        TableChangeTextComponent changeTextComponent = new TableChangeTextComponent(tableChange);
        changes.getChildren().setAll(
            (Node) changeTextComponent.getContent()
        );
    }


}