package migrator.app.security;

import java.security.SecureRandom;
import java.util.prefs.Preferences;

import migrator.lib.adapter.Adapter;
import migrator.lib.encryption.SimpleEncryption;

public class SecurityContainer {
    private ProxyEncryption encryptionValue;

    public SecurityContainer() {
        this.encryptionValue = new ProxyEncryption();
        Adapter<String, byte[]> encryptionKeyAdapter = new EncryptionKeyAdapter();
        
        String key = Preferences.userRoot().get("encryptionKey", "");
        if (key.isEmpty()) {
            SecureRandom random = new SecureRandom();
            byte[] bytes = new byte[16];
            random.nextBytes(bytes);
            key = encryptionKeyAdapter.concretize(bytes);
            Preferences.userRoot().put("encryptionKey", key);
        }

        this.encryptionValue.setEncryption(
            new SimpleEncryption(encryptionKeyAdapter.generalize(key))
        );
    }

    public ProxyEncryption encryption() {
        return this.encryptionValue;
    }
}