package migrator.ext.sentry;

import java.util.Properties;

import io.sentry.Sentry;
import migrator.app.boot.Container;
import migrator.app.service.Service;

public class SentryExtension implements Service {
    protected Properties properties;
    private Container container;

    public SentryExtension(Container container, Properties properties) {
        this.properties = properties;
        this.container = container;
    }

    @Override
    public void start() {
        container.logger().setLogger(
            new SentryLogger()
        );

        Sentry.init(
            properties.getProperty("dsn", "")
        );
        Thread.setDefaultUncaughtExceptionHandler(
            new ExceptionHandler()
        );
    }

    @Override
    public void stop() {
        
    }
}