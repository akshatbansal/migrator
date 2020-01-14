package migrator.ext.php;

import migrator.app.boot.Container;
import migrator.app.service.Service;

public class PhpExtension implements Service {
    private Container container;

    public PhpExtension(Container container) {
        this.container = container;
    }

    @Override
    public void start() {
        container.codeContainer().addCommandFactory(
            "php",
            new PhpCommandFactory(
                container.databaseContainer().getApplicationColumnFormatCollection()
            )
        );
    }

    @Override
    public void stop() {
        
    }
}