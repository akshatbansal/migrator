package migrator.ext.sql.command;

import migrator.app.code.CodeCommand;

public class VoidCommand implements CodeCommand {
    @Override
    public String toCode() {
        return "";
    }
}