package utils;

/**
 * Created by Julien on 23/04/2014.
 */
public class ByteArrayConversionTool {

    public static int byteArrayToInt(byte byteLight, byte byteHeavy ){
        {
            return   byteLight & 0xFF |
                    (byteHeavy & 0xFF) << 8;
        }
    }
}
