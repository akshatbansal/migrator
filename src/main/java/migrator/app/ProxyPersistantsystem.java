package migrator.app;

import migrator.lib.persistantsystem.Persistantsystem;

public class ProxyPersistantsystem implements Persistantsystem {
    private Persistantsystem proxy;

    public ProxyPersistantsystem(Persistantsystem persistantsystem) {
        this.proxy = persistantsystem;
    }

    public void setProxy(Persistantsystem persistantsystem) {
        this.proxy = persistantsystem;
    }

    @Override
    public String getString(String key, String def) {
        return this.proxy.getString(key, def);
    }

    @Override
    public boolean getBoolean(String key, boolean def) {
        return this.proxy.getBoolean(key, def);
    }

    @Override
    public double getDouble(String key, double def) {
        return this.proxy.getDouble(key, def);
    }

    @Override
    public void putString(String key, String value) {
        this.proxy.putString(key, value);
    }

    @Override
    public void putBoolean(String key, boolean value) {
        this.proxy.putBoolean(key, value);
    }

    @Override
    public void putDouble(String key, double value) {
        this.proxy.putDouble(key, value);
    }
}