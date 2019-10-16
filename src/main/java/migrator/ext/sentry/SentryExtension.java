package migrator.ext.sentry;

import java.util.Properties;

import io.sentry.Sentry;
import migrator.app.ConfigContainer;
import migrator.app.extension.Extension;

public class SentryExtension implements Extension {
    protected Properties properties;

    public SentryExtension(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void load(ConfigContainer config) {
        System.out.println(this.properties.getProperty("dsn", ""));
        Sentry.init(properties.getProperty("dsn", ""));
        Thread.setDefaultUncaughtExceptionHandler(
            new ExceptionHandler()
        );
    }
}