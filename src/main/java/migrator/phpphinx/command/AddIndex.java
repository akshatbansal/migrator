package migrator.phpphinx.command;

import java.util.List;

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
        List<String> columns = (List<String>) this.indexChange.getCommand().getArgument("columns");
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