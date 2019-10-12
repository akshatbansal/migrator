package migrator.ext.javafx.project.component;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import migrator.app.Container;
import migrator.app.Gui;
import migrator.app.domain.project.component.CommitView;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableActiveState;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.IndexChange;
import migrator.app.migration.model.TableChange;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxCommitView extends ViewComponent implements CommitView {
    @FXML protected VBox breadcrumpsContainer;
    @FXML protected VBox body;

    public JavafxCommitView(Container container, Project project, ViewLoader viewLoader, Gui gui) {
        super(viewLoader);

        this.loadView("/layout/project/commit/view.fxml");

        List<Node> tableNodes = new ArrayList<>();
        for (Table table : container.getTableActiveState().getList()) {
            if (!this.tableHasAnyChange(table)) {
                continue;
            }
            JavafxCommitTableChange tableChangeComponent = new JavafxCommitTableChange(table, viewLoader);
            tableNodes.add(
                (Node) tableChangeComponent.getContent()
            );
        }

        this.breadcrumpsContainer.getChildren().add(
            (Node) gui.getBreadcrumps().createBreadcrumpsForCommit(project).getContent()
        );

        this.body.getChildren().addAll(tableNodes);
    }

    protected Boolean tableHasAnyChange(TableChange tableChange) {
        if (!tableChange.getCommand().isType(ChangeCommand.NONE)) {
            return true;
        }
        for (ColumnChange columnChange : tableChange.getColumnsChanges()) {
            if (!columnChange.getCommand().isType(ChangeCommand.NONE)) {
                return true;
            }
        }
        for (IndexChange indexChange : tableChange.getIndexesChanges()) {
            if (!indexChange.getCommand().isType(ChangeCommand.NONE)) {
                return true;
            }
        }
        return false;
    }
}