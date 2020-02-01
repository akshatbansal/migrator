package migrator.app.security;

import migrator.lib.encryption.Encryption;

public class ProxyEncryption implements Encryption {
    private Encryption encryption;

    public void setProxy(Encryption encryption) {
        this.encryption = encryption;
    }

    @Override
    public String decrypt(String text) {
        return this.encryption.decrypt(text);
    }

    @Override
    public String encrypt(String text) {
        return this.encryption.encrypt(text);
    }
}