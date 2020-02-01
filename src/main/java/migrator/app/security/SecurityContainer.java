package migrator.app.security;

import java.security.SecureRandom;

import migrator.lib.adapter.Adapter;
import migrator.lib.encryption.SimpleEncryption;
import migrator.lib.persistantsystem.Persistantsystem;

public class SecurityContainer {
    private ProxyEncryption encryptionValue;

    public SecurityContainer(Persistantsystem persistantsystem) {
        this.encryptionValue = new ProxyEncryption();
        Adapter<String, byte[]> encryptionKeyAdapter = new EncryptionKeyAdapter();
        
        String key = persistantsystem.getString("encryptionKey", "");
        if (key.isEmpty()) {
            SecureRandom random = new SecureRandom();
            byte[] bytes = new byte[16];
            random.nextBytes(bytes);
            key = encryptionKeyAdapter.concretize(bytes);
            persistantsystem.putString("encryptionKey", key);
        }

        this.encryptionValue.setEncryption(
            new SimpleEncryption(encryptionKeyAdapter.generalize(key))
        );
    }

    public ProxyEncryption encryption() {
        return this.encryptionValue;
    }
}