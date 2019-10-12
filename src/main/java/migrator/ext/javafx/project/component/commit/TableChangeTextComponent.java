package migrator.ext.javafx.project.component.commit;

import javafx.scene.Node;
import javafx.scene.text.Text;
import migrator.app.gui.GuiNode;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.TableChange;

public class TableChangeTextComponent implements GuiNode {
    protected Node node;

    public TableChangeTextComponent(TableChange tableChange) {
        Text diff = new Text();
        if (tableChange.hasNameChanged()) {
            diff.textProperty().set("rename table to " + tableChange.getName());
        } else if (tableChange.getCommand().isType(ChangeCommand.CREATE) ) {
            diff.textProperty().set("create table " + tableChange.getName());
        } else if (tableChange.getCommand().isType(ChangeCommand.DELETE)) {
            diff.textProperty().set("remove table " + tableChange.getName());
        }

        this.node = diff;
    }

    @Override
    public Object getContent() {
        return this.node;
    }
}