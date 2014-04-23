package client;

import request.Header;
import request.Question;
import request.Request;

import java.util.List;

/**
 * Created by Julien on 23/04/2014.
 */
public class DNSRequestParser {

    public static Request parseRequest(byte[] unparsedRequest){
        return null;
    }

    public static Header parseHeader(byte[] unparsedRequest){
        return null;
    }

    /**
     *
     * @param output : the initialised list to fill with parsed questions
     * @return the number of byte read
     */
    public int  parseAnswers(int number,byte[] unparsedRequest ,List<Question> output){
        assert output != null;

        return -1;
    }
}
