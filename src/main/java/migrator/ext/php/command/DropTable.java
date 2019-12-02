package migrator.ext.php.command;

import migrator.app.code.CodeCommand;
import migrator.app.migration.model.TableChange;

public class DropTable implements CodeCommand {
    protected TableChange tableChange;

    public DropTable(TableChange tableChange) {
        this.tableChange = tableChange;
    }

    @Override
    public String toCode() {
        return "$this->table('" + this.tableChange.getOriginalName() + "')->drop()->save();\n";
    }
}