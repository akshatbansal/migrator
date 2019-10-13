package migrator.ext.javafx.project.component.commit;

import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import migrator.app.gui.GuiNode;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.TableChange;

public class TableChangeTextComponent implements GuiNode {
    protected Node node;

    public TableChangeTextComponent(TableChange tableChange) {
        TextFlow diff = new TextFlow();
        diff.getStyleClass().add("list-item");
        if (tableChange.getCommand().isType(ChangeCommand.CREATE) ) {
            Text text = new Text("- create table");
            text.getStyleClass().add("text--white");
            diff.getChildren().add(text);
        } else if (tableChange.getCommand().isType(ChangeCommand.DELETE)) {
            Text text = new Text("- remove table");
            text.getStyleClass().add("text--white");
            diff.getChildren().add(text);
        } else if (tableChange.getCommand().isType(ChangeCommand.UPDATE)) {
            if (tableChange.hasNameChanged()) {
                Text[] textParts = new Text[] {
                    new Text("rename table to "),
                    new Text(tableChange.getName())
                };
                textParts[1].getStyleClass().add("text--primary");
                textParts[0].getStyleClass().add("text--white");
                diff.getChildren().addAll(textParts);
            } else {
                Text text = new Text("- table properties changed");
                text.getStyleClass().add("text--white");
                diff.getChildren().add(text);
            }
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
}