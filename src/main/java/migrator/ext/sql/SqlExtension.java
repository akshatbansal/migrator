package migrator.ext.sql;

import migrator.app.boot.Container;
import migrator.app.service.Service;

public class SqlExtension implements Service {
    private Container container;

    public SqlExtension(Container container) {
        this.container = container;
    }

    @Override
    public void start() {
        container.codeContainer().addCommandFactory(
            "sql",
            new SqlCommandFactory(
                container.databaseContainer().getApplicationColumnFormatCollection()
            )
        );
    }

    @Override
    public void stop() {
        
    }
}