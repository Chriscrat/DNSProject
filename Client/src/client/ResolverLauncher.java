package client;

import request.Header;
import request.Question;
import utils.ByteConversionTool;

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
              byte[] answer =  resolver.ask("8.8.8.8", "www.google.com");

            Header header = DNSRequestParser.parseHeader(answer);
            List<Question> questions = new ArrayList<>();
            int offset = DNSRequestParser.parseAnswers(header.QDCOUNT,answer,questions);

            System.out.println(ByteConversionTool.asciiBytesToString(answer.length - offset,offset,answer));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
