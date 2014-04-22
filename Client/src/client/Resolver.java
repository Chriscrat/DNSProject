package client;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Arrays;
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

    void ask(String dnsAdr, String url) throws IOException {
        DatagramSocket dnsServer = new DatagramSocket(null);

        byte[] message = new byte[256];

        byte[] id = generateId();


        byte first = 0,
        second = 0;

        first |= bitMasks.QUERY.mask | bitMasks.OP_QUERY.mask | bitMasks.RD.mask;

        int QDCOUNT = 1;    //     an unsigned 16 bit integer specifying the number of entries in the question section.
        int ANCOUNT = 0;    //     an unsigned 16 bit integer specifying the number of resource records in the answer section.
        int NSCOUNT = 0;    //     an unsigned 16 bit integer specifying the number of name server resource records in the authority records section.
        int ARCOUNT = 0;    //     an unsigned 16 bit integer specifying the number of resource records in the additional records section.


        System.arraycopy(id,0,message,0,2);
        message[3] = first;
        message[4] = second;
        System.arraycopy(id,0,message,0,2);
        System.arraycopy(shorty(QDCOUNT),0,message,5,2);
        System.arraycopy(shorty(ANCOUNT),0,message,7,2);
        System.arraycopy(shorty(NSCOUNT),0,message,9,2);
        System.arraycopy(shorty(ARCOUNT),0,message,11,2);


        int offset = 12;
        //add question
        byte[] question = asQuestionName(url);
        byte[] QTYPE = new byte[2];
        byte[] QCLASS = new byte[2];
        System.arraycopy(question,0,message,offset,question.length);
        offset+=question.length;
        System.arraycopy(QTYPE,0,message,offset,QTYPE.length);
        offset+=QTYPE.length;
        System.arraycopy(QCLASS,0,message,offset,QCLASS.length);
        offset+=QCLASS.length;


        DatagramPacket packet = new DatagramPacket(message,message.length,InetAddress.getByName(dnsAdr),53);
        dnsServer.send(packet);
    }

    byte[] asQuestionName(String adr){
        return new byte[0];
    }


    byte[] shorty(int number){
        byte[] array = ByteBuffer.allocate(4).putInt(number).array();

        return Arrays.copyOfRange(array,2,4);
    }

}
