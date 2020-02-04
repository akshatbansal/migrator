package migrator.app.domain;

import migrator.lib.encryption.Encryption;

public class MockEncryption implements Encryption {
    @Override
    public String decrypt(String text) {
        return text;
    }

    @Override
    public String encrypt(String text) {
        return text;
    }
}