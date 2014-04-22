package client;

import java.net.*;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: FuglyLionKing
 * Date: 22/04/14
 * Time: 09:59
 * To change this template use File | Settings | File Templates.
 */
public class Resolver {

    byte[] generateId(){
        byte[] id = new byte[2];
        Random rnd = new Random();
        rnd.nextBytes(id);

        return id;
    }

    enum bitMasks {

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

        bitMasks(byte mask) {
            this.mask = mask;
        }


    }

    void ask(String dnsAdr, String url) throws SocketException, UnknownHostException {
        DatagramSocket dnsServer = new DatagramSocket(null);

        byte[] message = new byte[256];

        byte[] id = generateId();


        byte first = 0, //QR, OPCODE, AA, TC
        second = 0;
        //opcode
//        0               a standard query (QUERY)
//        1               an inverse query (IQUERY)
//        2               a server status request (STATUS)
        first |= bitMasks.QUERY.mask | bitMasks.OP_QUERY.mask | bitMasks.RD.mask;
//        second |=




        DatagramPacket packet = new DatagramPacket(message,message.length,InetAddress.getByName(dnsAdr),53);
    }



}
