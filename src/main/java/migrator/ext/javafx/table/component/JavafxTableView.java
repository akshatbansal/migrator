package migrator.ext.javafx.table.component;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import migrator.app.domain.table.component.TableView;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.ColumnService;
import migrator.app.domain.table.service.IndexService;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.domain.table.service.TableService;
import migrator.breadcrumps.BreadcrumpsComponent;
import migrator.breadcrumps.GuiKit;
import migrator.javafx.helpers.ControllerHelper;
import migrator.migration.ChangeService;

public class JavafxTableView implements TableView {
    protected BreadcrumpsComponent breadcrumpsComponent;
    protected TableGuiKit tableGuiKit;
    protected TableService tableService;
    protected ColumnService columnService;
    protected IndexService indexService;
    protected ChangeService changeService;
    protected Table table;
    protected Node node;
    @FXML protected VBox breadcrumpsContainer;
    @FXML protected VBox body;
    @FXML protected Text title;

    public JavafxTableView(GuiKit breadcrumpsGuiKit, TableGuiKit tableGuiKit, TableService tableService, ColumnService columnService, IndexService indexService, ChangeService changeService) {
        this.breadcrumpsComponent = breadcrumpsGuiKit.createBreadcrumps();
        this.tableGuiKit = tableGuiKit;
        this.tableService = tableService;
        this.columnService = columnService;
        this.indexService = indexService;
        this.changeService = changeService;
        this.table = this.tableService.getSelected().get();
        this.node = ControllerHelper.createViewNode(this, "/layout/table/view.fxml");
    }

    @Override
    public Object getContent() {
        return this.node;
    }

    @FXML
    public void initialize() {
        this.title.textProperty().bind(this.table.nameProperty());
        ControllerHelper.replaceNode(this.breadcrumpsContainer, this.breadcrumpsComponent);
        ControllerHelper.addNode(this.body, this.tableGuiKit.createColumnList(this.columnService, this.changeService));
        ControllerHelper.addNode(this.body, this.tableGuiKit.createIndexList(this.indexService));
    }
}