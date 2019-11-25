package migrator.app.code;

import java.util.List;

import migrator.app.code.CodeCommand;

public class CommandSequence implements CodeCommand {
    protected List<CodeCommand> commands;

    public CommandSequence(List<CodeCommand> commands) {
        this.commands = commands;
    }

    @Override
    public String toCode() {
        String code = "";
        for (CodeCommand command : this.commands) {
            code += command.toCode();
        }
        return code;
    }
}