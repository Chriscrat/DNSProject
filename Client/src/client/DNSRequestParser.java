package client;

import request.BitMask;
import request.Header;
import request.Question;
import request.Request;
import utils.ByteConversionTool;

import java.util.List;

/**
 * Created by Julien on 23/04/2014.
 */
public class DNSRequestParser {

    public static Request parseRequest(byte[] unparsedRequest) {
        return null;
    }

    public static Header parseHeader(byte[] unparsedRequest) {
        Header header = new Header();

        header.ID = ByteConversionTool.bytesToInt(unparsedRequest[1], unparsedRequest[0]);

        header.QRisResponse = BitMask.RESPONSE.isSetIn(unparsedRequest[2]);
        header.AA = BitMask.AA.isSetIn(unparsedRequest[2]);
        header.TC = BitMask.TC.isSetIn(unparsedRequest[2]);
        header.RD = BitMask.RD.isSetIn(unparsedRequest[2]);
        header.RA = BitMask.RA.isSetIn(unparsedRequest[2]);

        header.QDCOUNT = ByteConversionTool.bytesToInt(unparsedRequest[5], unparsedRequest[4]);
        header.ANCOUNT = ByteConversionTool.bytesToInt(unparsedRequest[7], unparsedRequest[6]);
        header.NSCOUNT = ByteConversionTool.bytesToInt(unparsedRequest[9], unparsedRequest[8]);
        header.ARCOUNT = ByteConversionTool.bytesToInt(unparsedRequest[11], unparsedRequest[10]);

        return header;
    }

    /**
     * @param output : the initialised list to fill with parsed questions
     * @return the number of byte read
     */
    public static int parseAnswers(int number, byte[] unparsedRequest, List<Question> output) {
        assert output != null;

        //questions begins at twelve bytes from the unparsedRequest start
        int readingCursor = 12;

        for (int i = 0; i < number; i++) {
            Question question = new Question();

            StringBuilder qname = new StringBuilder();
            readingCursor = readName(readingCursor,unparsedRequest,qname);
            question.QNAME = qname.toString();

            question.QTYPE = ByteConversionTool.bytesToInt(unparsedRequest[++readingCursor], unparsedRequest[++readingCursor]);
            question.QCLASS = ByteConversionTool.bytesToInt(unparsedRequest[++readingCursor], unparsedRequest[++readingCursor]);

            output.add(question);
        }

        return readingCursor;
    }

    public static int readName(int offset, byte[] bytes, StringBuilder output){
        int wordLength;
        while(0 != (wordLength = ByteConversionTool.bytesToInt(bytes[offset]))){
            output.append(ByteConversionTool.asciiBytesToString(wordLength, ++offset, bytes));
            output.append('.');
            offset +=wordLength;
        }
        return offset;
    }
}
