package migrator.ext.javafx.table.component;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import migrator.app.Container;
import migrator.app.Gui;
import migrator.app.breadcrumps.BreadcrumpsComponent;
import migrator.app.domain.table.component.ColumnList;
import migrator.app.domain.table.component.IndexList;
import migrator.app.domain.table.component.TableView;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxTableView extends ViewComponent implements TableView {
    protected BreadcrumpsComponent breadcrumpsComponent;
    protected TableGuiKit tableGuiKit;
    protected Table table;
    protected ColumnList columnList;
    protected IndexList indexList;

    @FXML protected VBox breadcrumpsContainer;
    @FXML protected VBox body;
    @FXML protected Text title;

    public JavafxTableView(Table table, ViewLoader viewLoader, Container container, Gui gui) {
        super(viewLoader);
        this.breadcrumpsComponent = gui.getBreadcrumps().createBreadcrumps(table, container.getProjectService().getOpened().get().getProject());
        this.tableGuiKit = gui.getTableKit();
        this.table = table;

        this.loadView("/layout/table/view.fxml");
    }

    @FXML
    public void initialize() {
        this.title.textProperty().bind(this.table.nameProperty());

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