package client;

import request.Header;
import request.Question;
import request.ResourceRecord;
import utils.ByteConversionTool;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: FuglyLionKing
 * Date: 23/04/14
 * Time: 11:21
 * To change this template use File | Settings | File Templates.
 */
public class ResolverLauncher {

    public static void main(String[] a){
        Resolver resolver = new Resolver();

        try {
              byte[] answer =  resolver.ask("8.8.8.8", "www.nofrag.com");

            String ansStr = ByteConversionTool.asciiBytesToString(answer.length,0,answer);


            Header header = DNSRequestParser.parseHeader(answer);
            List<Question> questions = new ArrayList<>(header.QDCOUNT);
            int offset = DNSRequestParser.parseAnswers(header.QDCOUNT,answer,questions);

            List<ResourceRecord> answers = new ArrayList<>(header.ANCOUNT);
            List<ResourceRecord> authority = new ArrayList<>(header.NSCOUNT);
            List<ResourceRecord> additional = new ArrayList<>(header.ARCOUNT);

            offset = DNSRequestParser.parseResource(header.ANCOUNT,offset,answer,answers);
            offset = DNSRequestParser.parseResource(header.NSCOUNT,offset,answer,authority);
            offset = DNSRequestParser.parseResource(header.ARCOUNT,offset,answer,additional);

            System.out.println(offset);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
