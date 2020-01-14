package migrator.app.gui.component.commit;

import javafx.scene.Node;
import javafx.scene.text.TextFlow;
import migrator.app.gui.component.Component;
import migrator.app.gui.component.SimpleComponent;
import migrator.app.gui.component.text.PrimaryText;
import migrator.app.gui.component.text.WhiteText;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.TableChange;

public class TableChangeTextComponent extends SimpleComponent implements Component {
    protected TextFlow textFlow;

    public TableChangeTextComponent() {
        super();

        this.textFlow = new TextFlow();
        this.textFlow.getStyleClass().add("list-item");
    }

    public void set(TableChange tableChange) {
        this.textFlow.getChildren().clear();
        if (tableChange.getCommand().isType(ChangeCommand.CREATE) ) {
            this.textFlow.getChildren().add(
                new WhiteText("- create table")
            );
        } else if (tableChange.getCommand().isType(ChangeCommand.DELETE)) {
            this.textFlow.getChildren().add(
                new WhiteText("- remove table")
            );
        } else if (tableChange.getCommand().isType(ChangeCommand.UPDATE)) {
            if (tableChange.hasNameChanged()) {
                this.textFlow.getChildren().add(
                    new WhiteText("- rename table to ")
                );
                this.textFlow.getChildren().add(
                    new PrimaryText(tableChange.nameProperty())
                );
            } else {
                this.textFlow.getChildren().add(
                    new WhiteText("- table properties changed")
                );
            }
        }
    }

    @Override
    public Node getNode() {
        return this.textFlow;
    }

    public boolean isEmpty() {
        return this.textFlow.getChildren().isEmpty();
    }
}