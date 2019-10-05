package migrator.app.code;

public class CodeManager {
    protected CodeConfig config;

    public CodeManager(CodeConfig config) {
        this.config = config;
    }

    public CodeCommandFactory getCommandFactory(String name) {
        return this.config.getCommandFactories().get(name);
    }
}