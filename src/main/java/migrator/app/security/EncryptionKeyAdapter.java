package migrator.app.security;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import migrator.lib.adapter.Adapter;

public class EncryptionKeyAdapter implements Adapter<String, byte[]> {
    @Override
    public String concretize(byte[] item) {
        return new String(Base64.getEncoder().encode(item));
    }

    @Override
    public byte[] generalize(String item) {
        try {
            return Base64.getDecoder().decode(item.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new byte[16];
    }
}