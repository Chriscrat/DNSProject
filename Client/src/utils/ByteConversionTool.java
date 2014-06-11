package utils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by Julien on 23/04/2014.
 */
public class ByteConversionTool {

    public static int bytesToInt(byte byteHeavy, byte byteLight) {
        return byteLight & 0xFF |
                (byteHeavy & 0xFF) << 8;
    }

    public static long bytesToLong(byte byteHeavy,byte byteMedHeavy, byte byteMedLight, byte byteLight) {
        return byteLight & 0xFF |
                (byteMedLight & 0xFF) << 8 |
                (byteMedLight & 0xFF) << 16 |
                (byteMedHeavy & 0xFF) << 24 |
                (byteHeavy & 0xFF) << 32
                ;
    }

    public static long bytesToLong(int offset, int length, byte[] source){
        assert 9 > length && 0 < length;

        long res = 0;
        short combo = 0;

        while(0 < length) {
            res|= source[offset+(--length)] << (combo * 2);
            combo = combo == 0 ? 4 : combo;
        }

        return res;

    }

    public static int bytesToInt(byte byteLight) {
        return byteLight & 0xFF;
    }

    public static String asciiBytesToString(int length, int start, byte[] table){
        try {
            return new String(Arrays.copyOfRange(table,start,start+length),"ascii");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
