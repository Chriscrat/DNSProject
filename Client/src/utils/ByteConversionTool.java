package utils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by Julien on 23/04/2014.
 */
public class ByteConversionTool {

    public static int bytesToInt(byte byteLight, byte byteHeavy) {
        return byteLight & 0xFF |
                (byteHeavy & 0xFF) << 8;
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
