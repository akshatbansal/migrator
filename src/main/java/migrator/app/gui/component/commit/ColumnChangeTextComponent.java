package migrator.app.gui.component.commit;

import javafx.scene.Node;
import javafx.scene.text.TextFlow;
import migrator.app.gui.component.Component;
import migrator.app.gui.component.SimpleComponent;
import migrator.app.gui.component.text.PrimaryText;
import migrator.app.gui.component.text.WhiteText;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;

public class ColumnChangeTextComponent extends SimpleComponent implements Component {
    protected TextFlow textFlow;

    public ColumnChangeTextComponent() {
        this.textFlow = new TextFlow();
        this.textFlow.getStyleClass().add("list-item");
    }

    public void set(ColumnChange columnChange) {
        this.textFlow.getChildren().clear();
        if (columnChange.getCommand().isType(ChangeCommand.CREATE) ) {
            this.textFlow.getChildren().add(
                new WhiteText("- create column ")
            );
            this.textFlow.getChildren().add(
                new PrimaryText(columnChange.nameProperty())
            );
        } else if (columnChange.getCommand().isType(ChangeCommand.DELETE)) {
            this.textFlow.getChildren().add(
                new WhiteText("- remove column ")
            );
            this.textFlow.getChildren().add(
                new PrimaryText(columnChange.getOriginal().nameProperty())
            );
        } else if (columnChange.getCommand().isType(ChangeCommand.UPDATE)) {
            this.textFlow.getChildren().add(
                new WhiteText("- change column ")
            );
            this.textFlow.getChildren().add(
                new PrimaryText(columnChange.getOriginal().nameProperty())
            );
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