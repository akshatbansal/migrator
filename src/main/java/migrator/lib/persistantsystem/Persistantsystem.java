package migrator.lib.persistantsystem;

public interface Persistantsystem {
    public void putString(String key, String value);
    public void putDouble(String key, double value);
    public void putBoolean(String key, boolean value);
    public String getString(String key, String def);
    public double getDouble(String key, double def);
    public boolean getBoolean(String key, boolean def);
}