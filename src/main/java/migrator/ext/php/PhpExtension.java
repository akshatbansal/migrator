package migrator.ext.php;

import migrator.app.boot.Container;
import migrator.app.extension.Extension;

public class PhpExtension implements Extension {
    @Override
    public void load(Container container) {
        container.codeContainer().addCommandFactory(
            "php",
            new PhpCommandFactory(
                container.databaseContainer().getApplicationColumnFormatCollection()
            )
        );
    }
}