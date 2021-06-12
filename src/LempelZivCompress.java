import java.util.*;
import java.io.*;

public class LempelZivCompress {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please call this program with one argument which is the input file name.");
        } else {
            try {
                Scanner s = new Scanner(new File(args[0]));

                // Read the entire file into one String.
                StringBuilder fileText = new StringBuilder();
                while (s.hasNextLine()) {
                    fileText.append(s.nextLine() + "\n");
                }

                System.out.println(compress(fileText.toString()));
            } catch (FileNotFoundException e) {
                System.out.println("Unable to find file called " + args[0]);
            }
        }
    }

    /**
     * Take uncompressed input as a text string, compress it, and return it as a
     * text string.
     */
    static final int windowSize = 100;
    public static String compress(String input) {
        int inputLen = input.length();
        StringBuilder output = new StringBuilder();
//      First character can have no match so just add that one;
//        input.substring(windowStart,cursor),input.substring(cursor,Math.min(cursor+length,N));
        ZivTriple first = new ZivTriple(0,0, input.substring(0,1));
        output.append(first.toZString(first));

//      LOOP 1: move through the whole text
        int cursor = 1; // cursor = position index om T of char to be encoded ie text[1] to text [inputLen - 1]
        int length = 1;  // the length of the chars to be copied ie if length remains 1, no match. copy 0 chars
        int prevMatch = -1; // starting index in the text of the last match
        int match = -1; // starting index of the match we are testing
        int maxLen;
        int windowStart;

        while (cursor < inputLen) { // stay within text:  while (cursor < inputLen - 1) { // ? why -1  why not < inputLen?
            windowStart = (cursor < windowSize) ? 0 : cursor - windowSize;
//          LOOP: start looking for the longest matches, starting from 1 = i loop through to find longest prefix of text from the cursor to the end of the text ie look ahead for longest prefix of text[cursor .. text.length-1]don't look for matches that are longer than what is possible, either before or after the cursor (+- start and end of text)
            // maximum limit to lookahead for this loop, maxLen = minimum of:(inputLen - 1) - cursor) OR (cursor - windowStart) because can't have a match longer than the window either
            maxLen = Math.min((inputLen - cursor), (cursor - windowStart));
//            if(cursor + length < inputLen){
//                System.out.println("54 While cursor " + cursor + input.substring(cursor, (cursor+length)) + " maxLen " + maxLen + " length " + length);
//            }
            match = stringMatch(input.substring(cursor, (cursor + length)), input.substring(windowStart, cursor));
            for (length = 1; length <= maxLen; length++) {
                while (match >= 0) {
                    prevMatch = match;
                    length += 1;
                    if(cursor+length>inputLen){match = -1; break;}
//                    System.out.println("60 While MATCH cursor " + cursor + " " + input.substring(cursor, (cursor + length)) + " prevMatch " + prevMatch + " " + input.substring(prevMatch, (prevMatch + length)) + " length " + length);
                    match = stringMatch(input.substring(cursor, (cursor + length)), input.substring(windowStart, cursor));
                    if (length > maxLen) {match = -1; break;}
                }
                if(match < 0){break;}
            }
            if (match < 0) { // match < 0 no match

                if(prevMatch < 0){
                    length = 1;
//                    System.out.println("68 if no match no prevMatch " + cursor + input.substring(cursor, cursor + length)  + " length " + length);
                    ZivTriple next = new ZivTriple(0, 0, input.substring(cursor, (cursor + 1)));
                    output.append(next.toZString(next));
                }
                else if (prevMatch >=0){
//                    System.out.println("74 NO MATCH prevMatch " + prevMatch + input.substring(prevMatch,(prevMatch + length)) + " cursor " + cursor + input.substring(cursor, (cursor+1)) + " length " + length + " windowStart " + windowStart);
                    if((cursor + length) > inputLen){
                        ZivTriple next = new ZivTriple((cursor - prevMatch - windowStart), (length - 2), input.substring((cursor + length - 2), (cursor + length -1)));
                        break;
                    }
                    else {
                        ZivTriple next = new ZivTriple((cursor - prevMatch - windowStart), (length - 1), input.substring((cursor + length - 1), (cursor + length)));
                        output.append(next.toZString(next));
                        prevMatch = -1; // longest match so far index of where it first matches
                    }
                }
//                if(cursor+length<inputLen) {
//                    System.out.println("78 update cursor " + cursor + " text from cursor " + input.substring(cursor, (cursor + length)) + " to length" + length);
//                }
                cursor += length;
                length = 1;
                if (cursor > inputLen) { break; }
            }
        }
        return output.toString();
    }
    public static int stringMatch(String fromCursor, String fromWindowStart){
        int match = -1;
        match = fromWindowStart.lastIndexOf(fromCursor);
//        System.out.println("115 find fromCursor " + fromCursor + " in From WindowStart " + fromWindowStart + " match " + match);
        return match;
    }
}