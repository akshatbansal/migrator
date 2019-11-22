package migrator.ext.php.command;

import java.util.ArrayList;
import java.util.List;

import migrator.app.code.CodeCommand;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.IndexChange;

public class AddIndex implements CodeCommand {
    protected IndexChange indexChange;

    public AddIndex(IndexChange indexChange) {
        this.indexChange = indexChange;
    }

    @Override
    public String toCode() {
        return "\t->addIndex(" + this.getColumns() + ", " + this.getOptions() + ")\n";
    }

    private String getColumns() {
        List<ColumnProperty> columnsProperty = this.indexChange.columnsProperty();
        List<String> columns = new ArrayList<>();
        for (ColumnProperty columnProperty : columnsProperty) {
            if (columnProperty == null) {
                continue;
            }
            columns.add(columnProperty.getName());
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