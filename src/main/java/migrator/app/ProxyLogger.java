package migrator.app;

import migrator.lib.logger.Logger;

public class ProxyLogger implements Logger {
    private Logger proxyLogger;

    public ProxyLogger(Logger proxyLogger) {
        this.proxyLogger = proxyLogger;
    }

    public void setLogger(Logger logger) {
        this.proxyLogger = logger;
    }

    @Override
    public void debug(String message) {
        this.proxyLogger.debug(message);
    }

    @Override
    public void error(Exception exception) {
        this.proxyLogger.error(exception);
    }

    @Override
    public void error(String message) {
        this.proxyLogger.error(message);
    }

    @Override
    public void info(String message) {
        this.proxyLogger.info(message);
    }

    @Override
    public void warning(String message) {
        this.proxyLogger.warning(message);
    }
}