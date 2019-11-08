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
        config.loggerConfig().set(
            new SentryLogger()
        );
        
        String sentryInit = properties.getProperty("dsn", "");
        if (!sentryInit.isEmpty()) {
            sentryInit += "?environment=" + properties.getProperty("environment", "");
        }
        Sentry.init(sentryInit);
        Thread.setDefaultUncaughtExceptionHandler(
            new ExceptionHandler()
        );
    }
}