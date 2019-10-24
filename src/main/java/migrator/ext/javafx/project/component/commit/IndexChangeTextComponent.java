package migrator.ext.javafx.project.component.commit;

import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import migrator.app.gui.GuiNode;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.IndexChange;

public class IndexChangeTextComponent implements GuiNode {
    protected Node node;

    public IndexChangeTextComponent(IndexChange indexChange) {
        TextFlow diff = new TextFlow();
        diff.getStyleClass().add("list-item");
        if (indexChange.getCommand().isType(ChangeCommand.CREATE) ) {
            Text[] textParts = new Text[] {
                new Text("- create index "),
                new Text(indexChange.columnsStringProperty().get())
            };
            textParts[0].getStyleClass().add("text--white");
            textParts[1].getStyleClass().add("text--primary");
            diff.getChildren().addAll(textParts);
        } else if (indexChange.getCommand().isType(ChangeCommand.DELETE)) {
            Text[] textParts = new Text[] {
                new Text("- remove index "),
                new Text(indexChange.getName())
            };
            textParts[0].getStyleClass().add("text--white");
            textParts[1].getStyleClass().add("text--primary");
            diff.getChildren().addAll(textParts);
        }
        if (diff.getChildren().size() == 0) {
            this.node = null;
            return;
        }

        this.node = diff;
    }

    @Override
    public Object getContent() {
        return this.node;
    }

    @Override
    public void destroy() {}
}