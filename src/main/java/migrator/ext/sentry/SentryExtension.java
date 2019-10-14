package migrator.ext.sentry;

import io.sentry.Sentry;
import migrator.app.ConfigContainer;
import migrator.app.extension.Extension;

public class SentryExtension implements Extension {
    protected String dns;

    public SentryExtension(String dns) {
        this.dns = dns;
    }

    @Override
    public void load(ConfigContainer config) {
        Sentry.init(this.dns);
        Thread.setDefaultUncaughtExceptionHandler(
            new ExceptionHandler()
        );
    }
}