package migrator.lib.persistance.helpers;

import java.util.Arrays;

public class ArraysUtil {
    public static byte[] join(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    public static byte[][] chunk(byte[] a, int chunkSize) {
        int count = (int) Math.ceil((double) a.length / chunkSize);
        byte[][] result = new byte[count][];
        int index = 0;
        while (index < count) {
            int from = index * chunkSize;
            int to = from + Math.min(a.length - from, chunkSize);
            result[index] = Arrays.copyOfRange(a, from, to);
            index++;
        }
        return result;
    }
}