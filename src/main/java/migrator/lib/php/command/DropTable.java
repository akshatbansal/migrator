package migrator.lib.php.command;

import migrator.migration.TableChange;

public class DropTable implements PhpCommand {
    protected TableChange tableChange;

    public DropTable(TableChange tableChange) {
        this.tableChange = tableChange;
    }
    public String toPhp() {
        return "$this->dropTable('" + this.tableChange.getOriginalName() + "');\n";
    }
}