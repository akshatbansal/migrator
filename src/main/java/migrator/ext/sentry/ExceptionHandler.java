package migrator.ext.sentry;

import java.lang.Thread.UncaughtExceptionHandler;

import io.sentry.Sentry;

public class ExceptionHandler implements UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Sentry.capture(e);
        e.printStackTrace();
    }
}