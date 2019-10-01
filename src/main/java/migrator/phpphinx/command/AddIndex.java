package migrator.phpphinx.command;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.StringProperty;
import migrator.migration.IndexChange;

public class AddIndex implements PhpCommand {
    protected IndexChange indexChange;

    public AddIndex(IndexChange indexChange) {
        this.indexChange = indexChange;
    }

    public String toPhp() {
        return "\t->addIndex(" + this.getColumns() + ", " + this.getOptions() + ")\n";
    }

    private String getColumns() {
        List<StringProperty> columnsProperty = this.indexChange.columnsProperty();
        List<String> columns = new ArrayList<>();
        for (StringProperty columnProperty : columnsProperty) {
            columns.add(columnProperty.get());
        }
        String columnsString = String.join("', '", columns);
        if (!columnsString.isEmpty()) {
            columnsString = "['" + columnsString + "']";
        }
        return columnsString;
    }

    private String getOptions() {
        // name
        // unique
        return "['name' => '" + this.indexChange.getName() + "']";
    }
}