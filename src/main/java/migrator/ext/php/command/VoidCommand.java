package migrator.ext.php.command;

import migrator.app.code.CodeCommand;

public class VoidCommand implements CodeCommand {
    @Override
    public String toCode() {
        return "";
    }
}