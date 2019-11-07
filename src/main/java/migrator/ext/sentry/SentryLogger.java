package migrator.ext.sentry;

import io.sentry.Sentry;
import migrator.lib.logger.Logger;

public class SentryLogger implements Logger {

    @Override
    public void debug(String message) {
        System.out.println(message);        
    }

    @Override
    public void error(String message) {
        System.err.println(message);
        Sentry.getContext().addExtra("type", "error");
        Sentry.capture(message);
        Sentry.getContext().clear();
    }

    @Override
    public void info(String message) {
        System.out.println(message);
        Sentry.getContext().addExtra("type", "info");
        Sentry.capture(message);
        Sentry.getContext().clear();
    }

    @Override
    public void warning(String message) {
        System.out.println(message);
        Sentry.getContext().addExtra("type", "warning");
        Sentry.capture(message);
        Sentry.getContext().clear();
    }

    @Override
    public void error(Exception exception) {
        exception.printStackTrace();
        Sentry.getContext().addExtra("type", "error");
        Sentry.capture(exception);
        Sentry.getContext().clear();
    }
}