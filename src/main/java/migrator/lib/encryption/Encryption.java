package migrator.lib.encryption;

public interface Encryption {
    public String encrypt(String text);
    public String decrypt(String text);
}