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
    public static String compress(String input) {

        int inputLen = input.length();
        char[] text = input.toCharArray();
        System.out.println(input);
        int windowSize = 100; // make final static?
        int cursor = 0; // position of char to be encoded ie text[0]
        int length = 1;
        int prevMatch = 0;
        int match = 0;
        ArrayList<String> output = new ArrayList<>();

//        look for longest prefix of text[cursor .. text.length-1]
//        in text[max(cursor-windowSize,0) .. cursor]
//        if found, add [offset,length,text[cursor+length]] to output
//        else add [0|0|text[cursor]] to output
//        advance cursor by length+1

        while (cursor < inputLen - 1) {
            System.out.println("cursor " + cursor + text[cursor]);

//            if (cursor - windowSize < 0)
//                start = 0;// should never point before 0 - so this is not quite right because cursor is moving along
//            if (cursor + length > inputLen)
//                break; // should not go past end of text - this is not quite right because cursor is moving along

            length = 1; // longest possible lookahead length match
            int index = 0;
            prevMatch = 0;
//            loop through to find longest prefix of text from the cursor to the end of the text
            for (int i = cursor; i <= cursor + length; i++) {
                index = (cursor < windowSize) ? 0 : cursor - windowSize;
                text[i] = text[index];
                text[length] = text[i];
                System.out.println("cursor " + cursor + text[cursor] + length);

//                Todo match = stringMatch(text[cursor..cursor + length],
//                Todo text[((cursor < windowSize) ? 0 : cursor - windowSize)..cursor-1])
                if (match != 0) // need more in the loop; if match found, look for longer match
                {
                    ZivTriple next = new ZivTriple(prevMatch, length - 1, text[cursor + length - 1]);
                    output.add(next.toString(next));
                    prevMatch = match;
                    length += 1;
                } else {
                    ZivTriple next = new ZivTriple(0, 0, text[cursor]);
                    output.add(next.toString(next));
                    cursor += length + 1; // move to the character in the input text that is after the match length
                }
                break;
            }

        }
//            This looks for an occurrence of text[cursor..cursor+length] in text[start..cursor-1], for
//            increasing values of length, until none is found, then outputs a triple.
//• This is pretty wasteful – we know there is no match before prevMatch, so there’s no
//            point looking there again! Probably better starting from prevMatch?
//• Or (maybe) find longest match starting at each position in window and record lo
            String encoded = output.toString();
            // TODO (currently just returns the argument).
            return encoded;
        }
//   Output a string of tuples:
//– [offset, length, nextCharacter] or [0|0|char]
    //        text.indexOf(pattern)

    //    public class LZivTriple<int offset, int len, char C> {
//        public final int offset;
//        public final int len;
//
//        public LZivTriple(int Offset, int len, char C) {
//            this.x = x;
//            this.y = y;
//        }
//    }
}
