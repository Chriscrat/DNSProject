package client;

import com.sun.prism.impl.Disposer;
import request.*;
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

        header.ID = ByteConversionTool.bytesToInt( unparsedRequest[0], unparsedRequest[1]);

        header.QRisResponse = BitMask.RESPONSE.isSetIn(unparsedRequest[2]);
        header.AA = BitMask.AA.isSetIn(unparsedRequest[2]);
        header.TC = BitMask.TC.isSetIn(unparsedRequest[2]);
        header.RD = BitMask.RD.isSetIn(unparsedRequest[2]);
        header.RA = BitMask.RA.isSetIn(unparsedRequest[2]);

        header.QDCOUNT = ByteConversionTool.bytesToInt(unparsedRequest[4], unparsedRequest[5]);
        header.ANCOUNT = ByteConversionTool.bytesToInt( unparsedRequest[6], unparsedRequest[7]);
        header.NSCOUNT = ByteConversionTool.bytesToInt( unparsedRequest[8], unparsedRequest[9]);
        header.ARCOUNT = ByteConversionTool.bytesToInt(  unparsedRequest[10],  unparsedRequest[11]);

        return header;
    }

    /**
     * @param output : the initialised list to fill with parsed questions
     * @return the current offset in the unparsedResuest
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

        return ++readingCursor;
    }
    /**
     * @param output : the initialised list to fill with parsed resource
     * @return the current offset after the parsing
     */
    public static int parseResource(int number, int offset, byte[] unparsedRequest, List<ResourceRecord> output) {
        assert output != null;

        for (int i = 0; i < number; i++) {
            ResourceRecord record = new ResourceRecord();

            StringBuilder name = new StringBuilder();
            offset = readName(offset,unparsedRequest,name);
            record.NAME = name.toString();

            record.TYPE = ByteConversionTool.bytesToInt(unparsedRequest[++offset], unparsedRequest[++offset]);
            record.CLASS = ByteConversionTool.bytesToInt(unparsedRequest[++offset], unparsedRequest[++offset]);
            record.TTL = ByteConversionTool.bytesToLong(unparsedRequest[++offset],unparsedRequest[++offset],unparsedRequest[++offset],unparsedRequest[++offset]);
            record.RDLENGTH = ByteConversionTool.bytesToInt(unparsedRequest[++offset], unparsedRequest[++offset]);

            record.RDATA = ByteConversionTool.asciiBytesToString(record.RDLENGTH, offset, unparsedRequest);
            offset+=record.RDLENGTH;

            output.add(record);
        }

        return offset;
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
