package migrator.ext.javafx.table.component;

import java.util.Arrays;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import migrator.app.Container;
import migrator.app.Gui;
import migrator.app.breadcrumps.BreadcrumpsComponent;
import migrator.app.breadcrumps.VoidBreadcrump;
import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.table.component.ColumnList;
import migrator.app.domain.table.component.IndexList;
import migrator.app.domain.table.component.TableView;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.gui.breadcrumps.RouteBreadcrump;
import migrator.ext.javafx.breadcrumps.JavafxBreadcrumpsComponent;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxTableView extends ViewComponent implements TableView {
    protected StringProperty breadcrumpTableName;
    protected StringProperty breadcrumpProjectName;
    protected ObjectProperty<Table> activeTable;
    protected ObjectProperty<ProjectContainer> activeProject;

    protected BreadcrumpsComponent breadcrumpsComponent;
    protected TableGuiKit tableGuiKit;
    protected ColumnList columnList;
    protected IndexList indexList;

    @FXML protected VBox breadcrumpsContainer;
    @FXML protected VBox body;
    @FXML protected Text title;

    public JavafxTableView(Container container, Gui gui, ObjectProperty<ProjectContainer> activeProject, ObjectProperty<Table> activeTable) {
        super(new ViewLoader());
        this.activeProject = activeProject;
        this.activeTable = activeTable;
        this.breadcrumpProjectName = new SimpleStringProperty("");
        this.breadcrumpTableName = new SimpleStringProperty("");

        this.breadcrumpsComponent = new JavafxBreadcrumpsComponent(Arrays.asList(
            new RouteBreadcrump(
                new SimpleStringProperty("Projects"),
                container.getActiveRoute(),
                "project.index"
            ),
            new RouteBreadcrump(
                this.breadcrumpProjectName,
                container.getActiveRoute(),
                "table.index"
            ),
            new VoidBreadcrump(this.breadcrumpTableName)
        ));
        this.tableGuiKit = gui.getTableKit();

        this.loadView("/layout/table/view.fxml");

        activeTable.addListener((observable, oldValue, newValue) -> {
            this.onTableChange(newValue);
        });
        this.onTableChange(activeTable.get());

        activeProject.addListener((observable, oldValue, newValue) -> {
            this.onProjectChange(newValue);
        });
        this.onProjectChange(activeProject.get());
    }

    private void onProjectChange(ProjectContainer project) {
        if (project == null) {
            this.breadcrumpProjectName.set("");
            return;
        }
        this.breadcrumpProjectName.set(project.getProject().nameProperty().get());
    }

    private void onTableChange(Table table) {
        this.title.textProperty().unbind();
        if (table == null) {
            this.breadcrumpTableName.unbind();
            return;
        }
        this.breadcrumpTableName.bind(table.nameProperty());
        this.title.textProperty().bind(table.nameProperty());
    }

    @FXML
    public void initialize() {
        this.breadcrumpsContainer.getChildren().setAll((Node) this.breadcrumpsComponent.getContent());

        this.columnList = this.tableGuiKit.createColumnList();
        this.indexList = this.tableGuiKit.createIndexList();

        this.columnList.onSelect((Column selectedColumn) -> {
            this.indexList.deselect();
        });
        this.indexList.onSelect((Index selectedIndex) -> {
            this.columnList.deselect();
        });

        this.body.getChildren()
            .setAll(
                (Node) this.columnList.getContent(),
                (Node) this.indexList.getContent()
            );
    }
}