package migrator.ext.javafx.project.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import migrator.app.breadcrumps.VoidBreadcrump;
import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.project.breadcrumps.ProjectsBreadcrump;
import migrator.app.domain.project.breadcrumps.SingleProjectBreadcrump;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableActiveState;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.IndexChange;
import migrator.app.migration.model.TableChange;
import migrator.ext.javafx.breadcrumps.JavafxBreadcrumpsComponent;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxCommitView extends ViewComponent {
    protected ObjectProperty<ProjectContainer> activeProjectContainer;
    protected ObjectProperty<Project> breadcrumpProject;
    protected TableActiveState tableActiveState;

    @FXML protected VBox breadcrumpsContainer;
    @FXML protected VBox body;

    public JavafxCommitView(ObjectProperty<ProjectContainer> activeProjectContainer) {
        super(new ViewLoader());
        // this.tableActiveState = container.getTableActiveState();
        this.activeProjectContainer = activeProjectContainer;
        this.breadcrumpProject = new SimpleObjectProperty<>();

        this.loadView("/layout/project/commit/view.fxml");

        // JavafxBreadcrumpsComponent breadcrumpComponent = new JavafxBreadcrumpsComponent(Arrays.asList(
        //     new ProjectsBreadcrump(container.getProjectService()),
        //     new SingleProjectBreadcrump(
        //             container.getProjectService(),
        //             this.breadcrumpProject,
        //             container.getTableActiveState()
        //         ),
        //     new VoidBreadcrump("commit changes")
        // ));
        // this.breadcrumpsContainer.getChildren().add(
        //     (Node) breadcrumpComponent.getContent()
        // );

        this.activeProjectContainer.addListener((observable, oldValue, newValue) -> {
            this.onProjectChange(newValue);
        });
        this.onProjectChange(this.activeProjectContainer.get());
    }

    private void onProjectChange(ProjectContainer projectContainer) {
        this.body.getChildren().clear();
        if (projectContainer == null) {
            this.breadcrumpProject.set(null);
            return;
        }
        this.breadcrumpProject.set(projectContainer.getProject());

        List<Node> tableNodes = new ArrayList<>();
        for (Table table : this.tableActiveState.getList()) {
            if (!this.tableHasAnyChange(table)) {
                continue;
            }
            JavafxCommitTableChange tableChangeComponent = new JavafxCommitTableChange(table);
            tableNodes.add(
                (Node) tableChangeComponent.getContent()
            );
        }
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