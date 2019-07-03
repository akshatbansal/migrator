package migrator.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serialize {
    public static byte[] serialize(Object object) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream stream = new ObjectOutputStream(bos);
            stream.writeObject(object);
            stream.flush();
            return bos.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public static Object deserialize(byte[] byteArray) {
        ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
        try {
            ObjectInputStream stream = new ObjectInputStream(bis);
            return stream.readObject();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        } finally {
            try {
                bis.close();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        return null;
    }
}