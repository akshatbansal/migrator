package migrator.ext.sentry;

import java.util.Properties;

import io.sentry.Sentry;
import migrator.app.boot.Container;
import migrator.app.extension.Extension;

public class SentryExtension implements Extension {
    protected Properties properties;

    public SentryExtension(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void load(Container container) {
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
}