package migrator.ext.php.command;

import migrator.app.code.CodeCommand;
import migrator.app.database.column.format.ApplicationColumnFormat;
import migrator.app.database.column.format.ApplicationColumnFormatCollection;
import migrator.app.domain.column.ColumnPropertyDiff;
import migrator.app.migration.model.ColumnChange;

public class ChangeColumn extends ColumnCommand implements CodeCommand {
    public ChangeColumn(ColumnChange columnChange, ApplicationColumnFormatCollection applicationColumnFormatCollection) {
        super(columnChange, applicationColumnFormatCollection);
    }

    @Override
    public String toCode() {
        ApplicationColumnFormat appFormat =  this.applicationColumnFormatCollection.getFormatByName(this.columnChange.getFormat());
        ColumnPropertyDiff columnDiff = new ColumnPropertyDiff(this.columnChange.getOriginal(), this.columnChange, appFormat);
        String php = "";
        if (columnDiff.hasChanged("name")) {
            php += "\t->renameColumn('" + this.columnChange.getOriginal().getName() + "', '" + this.columnChange.getName() + "')\n";
        }

        if (columnDiff.getChanges().size() > 1 || (columnDiff.getChanges().size() > 0 && !columnDiff.getChanges().get(0).equals("name"))) {
            String options = this.getOptions();
            php += "\t->changeColumn('" + this.columnChange.getOriginal().getName() + "', '" + this.columnChange.getFormat() + "'";
            if (!options.isEmpty()) {
                php += ", " + options;
            }
            php += ")\n";
        }

        return php;
    }
}