package request;

/**
 * Created by Julien on 23/04/2014.
 */
public enum BitMask {

        QUERY((byte)0b00000000)
        ,RESPONSE((byte)0b10000000)
        ,OP_QUERY((byte)0b00000000)
        ,OP_IQUERY((byte)0b00001000)
        ,OP_STATUS((byte)0b00011000)
        ,AA((byte)0b00000100)
        ,TC((byte)0b00000010)
        ,RD((byte)0b00000001)
        ,RA((byte)0b10000000)
        ,R_NO_ERROR((byte)0b00000000)
        ,R_FORMAT_ERROR((byte)0b00000001)
        ,R_SERVER_FAILURE((byte)0b00000010)
        ,R_NAME_ERROR((byte)0b00000011)
        ,R_NOT_IMPLEMENTED((byte)0b00000100)
        ,R_REFUSED((byte)0b00000101);


        public byte mask;

         BitMask (byte mask) {
            this.mask = mask;
        }

        public byte complement(){
            return (byte)~mask;
        }
    private static byte full = (byte)0b11111111;
    public boolean isSetIn(byte b){
        return full == (b | complement());
    }



}
