package migrator.ext.javafx.component;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SearchComponent extends ViewComponent {
    protected StringProperty searchString;

    @FXML protected TextField searchField;

    public SearchComponent(ViewLoader viewLoader, StringProperty searchString) {
        super(viewLoader);
        this.searchString = searchString;

        this.loadView("/layout/component/search.fxml");

        this.searchField.textProperty().bindBidirectional(this.searchString);
    }

    @FXML public void clear() {
        this.searchString.set("");
    }

    public void focus() {
        this.searchField.requestFocus();
    }
}