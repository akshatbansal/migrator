package migrator.lib.encryption;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {
    private static String getKey() {
        return "w!z%C*F-JaNcRfUj";
    }

    public static String encrypt(String text) {
        try
        {
            String key = Encryption.getKey();
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
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

    public static String decrypt(String text) {
        try
        {
            String key = Encryption.getKey();
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
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