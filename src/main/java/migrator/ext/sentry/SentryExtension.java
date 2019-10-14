package migrator.ext.sentry;

import io.sentry.Sentry;
import migrator.app.ConfigContainer;
import migrator.app.extension.Extension;

public class SentryExtension implements Extension {
    public SentryExtension() {}

    @Override
    public void load(ConfigContainer config) {
        Sentry.init();
        Thread.setDefaultUncaughtExceptionHandler(
            new ExceptionHandler()
        );
    }
}