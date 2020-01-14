package migrator.app.gui.component.list;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import migrator.app.gui.component.Component;
import migrator.app.gui.component.SimpleComponent;

public class SearchComponent extends SimpleComponent implements Component {
    protected StringProperty searchString;

    @FXML protected TextField searchField;

    public SearchComponent() {
        super();

        this.loadFxml("/layout/component/search.fxml");
    }

    public void bind(StringProperty searchString) {
        this.searchString = searchString;
        this.searchField.textProperty().bindBidirectional(this.searchString);
    }

    @FXML
    public void close() {
        this.searchString.set("");
    }

    public void focus() {
        this.searchField.requestFocus();
    }
}