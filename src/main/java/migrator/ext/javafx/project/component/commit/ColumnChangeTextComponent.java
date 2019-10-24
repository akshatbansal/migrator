package migrator.ext.javafx.project.component.commit;

import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import migrator.app.gui.GuiNode;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;

public class ColumnChangeTextComponent implements GuiNode {
    protected Node node;

    public ColumnChangeTextComponent(ColumnChange columnChange) {
        TextFlow diff = new TextFlow();
        diff.getStyleClass().add("list-item");
        if (columnChange.getCommand().isType(ChangeCommand.CREATE) ) {
            Text[] textParts = new Text[] {
                new Text("- create column "),
                new Text(columnChange.getName())
            };
            textParts[0].getStyleClass().add("text--white");
            textParts[1].getStyleClass().add("text--primary");
            diff.getChildren().addAll(textParts);
        } else if (columnChange.getCommand().isType(ChangeCommand.DELETE)) {
            Text[] textParts = new Text[] {
                new Text("- remove column "),
                new Text(columnChange.getName())
            };
            textParts[0].getStyleClass().add("text--white");
            textParts[1].getStyleClass().add("text--primary");
            diff.getChildren().addAll(textParts);
        } else if (columnChange.getCommand().isType(ChangeCommand.UPDATE)) {
            Text[] textParts = new Text[] {
                new Text("- change column "),
                new Text(columnChange.getName())
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