package migrator.table.javafx;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import migrator.breadcrumps.BreadcrumpsComponent;
import migrator.breadcrumps.GuiKit;
import migrator.javafx.helpers.ControllerHelper;
import migrator.table.component.TableView;
import migrator.table.model.Table;
import migrator.table.service.ColumnService;
import migrator.table.service.IndexService;
import migrator.table.service.TableService;

public class JavafxTableView implements TableView {
    protected BreadcrumpsComponent breadcrumpsComponent;
    protected migrator.table.service.GuiKit tableGuiKit;
    protected TableService tableService;
    protected ColumnService columnService;
    protected IndexService indexService;
    protected Table table;
    protected Node node;
    @FXML protected VBox breadcrumpsContainer;
    @FXML protected VBox body;
    @FXML protected Text title;

    public JavafxTableView(GuiKit breadcrumpsGuiKit, migrator.table.service.GuiKit tableGuiKit, TableService tableService, ColumnService columnService, IndexService indexService) {
        this.breadcrumpsComponent = breadcrumpsGuiKit.createBreadcrumps();
        this.tableGuiKit = tableGuiKit;
        this.tableService = tableService;
        this.columnService = columnService;
        this.indexService = indexService;
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
        ControllerHelper.addNode(this.body, this.tableGuiKit.createColumnList(this.columnService));
        ControllerHelper.addNode(this.body, this.tableGuiKit.createIndexList(this.indexService));
    }
}