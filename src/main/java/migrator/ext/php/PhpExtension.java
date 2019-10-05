package migrator.ext.php;

import migrator.app.code.CodeConfig;
import migrator.app.extension.ConfigContainer;
import migrator.app.extension.Extension;

public class PhpExtension implements Extension {
    @Override
    public void load(ConfigContainer config) {
        CodeConfig codeConfig = config.getCodeConfig();

        codeConfig.addCommandFactory("php", new PhpCommandFactory());
    }
}