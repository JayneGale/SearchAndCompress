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
        char[] text = input.toCharArray();
        System.out.println("input string " + input + text.length);
        StringBuilder output = new StringBuilder();

//      LOOP 1: move through the whole text
//      set start parameters
        int cursor = 0; // position of char to be encoded ie text[0] to text [inputLen - 1]
        int length = 1;  // the length of the chars to be copied ie if length remains 1, no match. copy 0 chars
        int maxLen = 1;
        int prevMatch = 0; // starting index of the last match
        int match = 0; // starting index of the match we are testing
        while (cursor < 10) { // stay within the index bounds of text ? why -1  why not < inputLen?
//        while (cursor < inputLen - 1) { // stay within the index bounds of text ? why -1  why not < inputLen?
//          keep start of window index within size of text
//            if (cursor - windowSize < 0)
//                start = 0;// should never point before 0
            int windowStart = (cursor < windowSize) ? 0 : cursor - windowSize;

//          LOOP 2: start looking for the longest matches, starting from 1 = i
//          loop through to find longest prefix of text from the cursor to the end of the text ie look ahead for longest prefix of text[cursor .. text.length-1]

//          don't look for matches that are longer than what is possible, either before or after the cursor (+- start and end of text)
            // maximum limit to lookahead for this loop, maxLen = minimum of:
            //      (inputLen - 1) - cursor) OR
            //      (cursor - windowStart) because can't have a match longer than the window either
//        in text[max(cursor-windowSize,0) .. cursor]


//            initialise length and match for each new i
            length = 1; // longest lookahead length match so far
            prevMatch = 0; // longest match so far

            for (int j = windowStart; j <= cursor; j++) {
                maxLen = Math.min(inputLen - cursor, cursor + 1 - windowStart);
                System.out.println("61 cursor " + cursor + text[cursor] + maxLen);

                //          LOOP 3
                while (length <= maxLen) {
                    match = j;
                    if(cursor>= inputLen){break;}
                    System.out.println("71 cursor " + cursor + text[cursor] + " j " + j + text[match] + " length " + length);
                    System.out.println("72 (match + length) " + (match + length - 1) + " (cursor + length - 1) " + (cursor + length - 1));

                    if (text[match + length -1] == text[cursor + length -1]) { // if match succeeded, try a longer match
                        prevMatch = match;
                        System.out.println("76 match! cursor "  + cursor + text[cursor] + " j " + j + text[match + length -1] + length);
                        length += 1;
                        break;
                    } else {
                        ZivTriple next = new ZivTriple(prevMatch, length - 1, text[cursor + length - 1]);
                        output.append(next.toZString(next));
                        cursor += length;
                        System.out.println("82 No match! " + j + text[j] + cursor + text[cursor] + length);
                        length = 1;
                        break;
                    }
                }
                if(match == cursor && cursor < inputLen){
                    ZivTriple next = new ZivTriple(0, 0, text[cursor]);
                    String str = next.toZString(next);
                    System.out.println("92 next.toZString(next) " + str);
                    output.append(next.toZString(next));
                    cursor += 1;
                    length = 1;
                }
            }
        }
        String encoded = output.toString();
        // TODO (currently just returns the argument).
        return encoded;
    }
}


//        if found, add [offset,length,text[cursor+length]] to output
//        else add [0|0|text[cursor]] to output

//        advance cursor by length+1

//                Todo match = stringMatch(text[cursor..cursor + length],
//                Todo text[windowStart..cursor-1])
//                if (match != 0) // need more in the loop; if match found, look for longer match
//                {
//                    ZivTriple next = new ZivTriple(prevMatch, length - 1, text[cursor + length - 1]);
//                    output.append(next);
//                    prevMatch = match;
//                    length += 1;
//                } else {
//                    ZivTriple next = new ZivTriple(0, 0, text[cursor]);
//                    output.append(next.toString());
//                    cursor += length + 1; // move to the character in the input text that is after the match length
//                }
//                break;
//            }

//            This looks for an occurrence of text[cursor..cursor+length] in text[start..cursor-1], for
//            increasing values of length, until none is found, then outputs a triple.
//• This is pretty wasteful – we know there is no match before prevMatch, so there’s no
//            point looking there again! Probably better starting from prevMatch?
//• Or (maybe) find longest match starting at each position in window and record lo


//   Output a string of tuples:
//– [offset, length, nextCharacter] or [0|0|char]
    //        text.indexOf(pattern)

