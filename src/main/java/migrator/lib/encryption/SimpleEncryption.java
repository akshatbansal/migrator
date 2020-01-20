package migrator.lib.encryption;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SimpleEncryption implements Encryption{
    private byte[] key;
    
    public SimpleEncryption(byte[] key) {
        this.key = key;
    }

    public String encrypt(String text) {
        try
        {
            Key aesKey = new SecretKeySpec(this.key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = Base64.getEncoder().encode(
                cipher.doFinal(text.getBytes("UTF-8"))
            );
            return new String(encrypted);
            
        }
        catch(Exception e) 
        {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(String text) {
        try
        {
            Key aesKey = new SecretKeySpec(this.key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] decrypted = cipher.doFinal(
                Base64.getDecoder().decode(text.getBytes("UTF-8"))
            );
            return new String(decrypted);
            
        }
        catch(Exception e) 
        {
            e.printStackTrace();
        }
        return null;
    }
}