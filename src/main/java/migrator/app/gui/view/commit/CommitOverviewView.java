package migrator.app.gui.view.commit;

import java.util.LinkedList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.table.model.Table;
import migrator.app.gui.component.ComponentFactories;
import migrator.app.gui.component.breadcrump.Breadcrump;
import migrator.app.gui.component.breadcrump.BreadcrumpsComponent;
import migrator.app.gui.component.breadcrump.EventBreadcrump;
import migrator.app.gui.component.breadcrump.RouteBreadcrump;
import migrator.app.gui.component.breadcrump.VoidBreadcrump;
import migrator.app.gui.component.commit.CommitTableChangeComponent;
import migrator.app.gui.component.list.VerticalListComponent;
import migrator.app.gui.view.SimpleView;
import migrator.app.gui.view.View;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.IndexChange;
import migrator.app.migration.model.TableChange;
import migrator.lib.dispatcher.EventDispatcher;

public class CommitOverviewView extends SimpleView implements View {
    protected ObjectProperty<ProjectContainer> activeProjectContainer;
    protected StringProperty projectBreadcrumpName;
    private ComponentFactories componentFactories;
    private EventDispatcher dispatcher;
    private ObservableList<CommitTableChangeComponent> changeComponents;
    private ObservableList<Table> tables;

    @FXML protected VBox breadcrumpsContainer;
    @FXML protected VBox body;

    public CommitOverviewView(
        ComponentFactories componentFactories,
        EventDispatcher dispatcher
    ) {
        super();
        this.changeComponents = FXCollections.observableArrayList();
        this.projectBreadcrumpName = new SimpleStringProperty("");
        this.componentFactories = componentFactories;
        this.dispatcher = dispatcher;

        this.loadFxml("/layout/project/commit/view.fxml");

        BreadcrumpsComponent breadcrumpsComponent = this.createBreadcrumpsComponent();

        this.breadcrumpsContainer.getChildren().add(
            breadcrumpsComponent.getNode()
        );

        VerticalListComponent<CommitTableChangeComponent> verticalListComponent = componentFactories.createVerticalList();
        verticalListComponent.bind(this.changeComponents);

        this.body.getChildren().add(
            verticalListComponent.getNode()
        );
    }

    public void bind(ObjectProperty<ProjectContainer> activeProjectContainer) {
        this.activeProjectContainer = activeProjectContainer;
        this.activeProjectContainer.addListener((observable, oldValue, newValue) -> {
            this.onProjectChange(newValue);
        });
        this.onProjectChange(this.activeProjectContainer.get());
    }

    public void bindTables(ObservableList<Table> tables) {
        this.tables = tables;
    }

    private BreadcrumpsComponent createBreadcrumpsComponent() {
        ObservableList<Breadcrump> breadcrumps = FXCollections.observableArrayList();
        breadcrumps.add(
            new EventBreadcrump("Projects", this.dispatcher, "project.close")
        );
        breadcrumps.add(
            new RouteBreadcrump(this.projectBreadcrumpName, this.dispatcher, "project")
        );
        breadcrumps.add(
            new VoidBreadcrump("commit changes")
        );

        BreadcrumpsComponent breadcrumpsComponent = this.componentFactories.createBreadcrumps();
        breadcrumpsComponent.bind(breadcrumps); 

        return breadcrumpsComponent;
    }

    private void onProjectChange(ProjectContainer projectContainer) {
        if (projectContainer == null) {
            this.projectBreadcrumpName.unbind();
            this.projectBreadcrumpName.set("");
            return;
        }
        this.projectBreadcrumpName.bind(
            projectContainer.getProject().nameProperty()
        );
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

    public void render() {
        List<CommitTableChangeComponent> components = new LinkedList<>();
        for (Table table : this.tables) {
            if (!this.tableHasAnyChange(table)) {
                continue;
            }
            CommitTableChangeComponent tableChangeComponent = new CommitTableChangeComponent(this.componentFactories);
            tableChangeComponent.set(table);
            components.add(tableChangeComponent);
        }
        this.changeComponents.setAll(components);
    }
}