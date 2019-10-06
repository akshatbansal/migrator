package migrator.ext.javafx.table.component;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import migrator.app.Container;
import migrator.app.Gui;
import migrator.app.domain.table.component.TableView;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.breadcrumps.BreadcrumpsComponent;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxTableView extends ViewComponent implements TableView {
    protected BreadcrumpsComponent breadcrumpsComponent;
    protected TableGuiKit tableGuiKit;
    protected Table table;

    @FXML protected VBox breadcrumpsContainer;
    @FXML protected VBox body;
    @FXML protected Text title;

    public JavafxTableView(Table table, ViewLoader viewLoader, Container container, Gui gui) {
        super(viewLoader);
        this.breadcrumpsComponent = gui.getBreadcrumps().createBreadcrumps();
        this.tableGuiKit = gui.getTableKit();
        this.table = table;

        this.loadView("/layout/table/view.fxml");
    }

    @FXML
    public void initialize() {
        this.title.textProperty().bind(this.table.nameProperty());

        this.breadcrumpsContainer.getChildren().setAll((Node) this.breadcrumpsComponent.getContent());

        this.body.getChildren().setAll(
            (Node) this.tableGuiKit.createColumnList().getContent(),
            (Node) this.tableGuiKit.createIndexList().getContent()
        );
    }
}