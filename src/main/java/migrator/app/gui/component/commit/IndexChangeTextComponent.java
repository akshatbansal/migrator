package migrator.app.gui.component.commit;

import javafx.scene.Node;
import javafx.scene.text.TextFlow;
import migrator.app.gui.component.Component;
import migrator.app.gui.component.SimpleComponent;
import migrator.app.gui.component.text.PrimaryText;
import migrator.app.gui.component.text.WhiteText;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.IndexChange;

public class IndexChangeTextComponent extends SimpleComponent implements Component {
    protected TextFlow textFlow;

    public IndexChangeTextComponent() {
        super();

        this.textFlow = new TextFlow();
        this.textFlow.getStyleClass().add("list-item");
    }

    public void set(IndexChange indexChange) {
        this.textFlow.getChildren().clear();
        if (indexChange.getCommand().isType(ChangeCommand.CREATE) ) {
            this.textFlow.getChildren().add(
                new WhiteText("- create index ")
            );
            this.textFlow.getChildren().add(
                new PrimaryText(indexChange.columnsStringProperty())
            );
        } else if (indexChange.getCommand().isType(ChangeCommand.DELETE)) {
            this.textFlow.getChildren().add(
                new WhiteText("- remove index ")
            );
            this.textFlow.getChildren().add(
                new PrimaryText(indexChange.getOriginal().nameProperty())
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