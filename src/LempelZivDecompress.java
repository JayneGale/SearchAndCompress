import java.util.*;
import java.io.*;
import java.util.regex.Pattern;

public class LempelZivDecompress {

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

                System.out.println(decompress(fileText.toString()));
            } catch (FileNotFoundException e) {
                System.out.println("Unable to find file called " + args[0]);
            }
        }
    }


    /**
     * Take compressed input as a text string, decompress it, and return it as a
     * text string.
     */
    static final int maxLookahead = 30;
    static final Pattern OpenBracket = Pattern.compile("\\[?");
    static final Pattern BitOrPat = Pattern.compile("\\|");
    static final Pattern CloseBracket = Pattern.compile("]");
//    static final Pattern Delimiters = Pattern.compile("\\[|\\||]");
    private static final Pattern NUMPAT = Pattern.compile("-?[1-9][0-9]*|0");

    public static String decompress1(String compressed) {
        StringBuilder decompressed = new StringBuilder();
        int strLen = compressed.length();
        int cursor = 0;
        int cursorAdd = 7;
        ZivTriple z = new ZivTriple(0,0,"");
        while((cursor + cursorAdd) <= strLen){
            StringBuilder sb = new StringBuilder();
            String substr = compressed.substring(cursor, (cursor + cursorAdd));
            z = z.decompressZ(substr);
            int cursorDec = decompressed.length();
            if (z.a > 0) {
                int offset = cursorDec - z.a;
                int length = offset + z.b;
//                System.out.println(decompressed.substring(offset,length));
                sb.append(decompressed.substring(offset,length));
            }
            sb.append(z.c);
            decompressed.append(sb);
            System.out.println(decompressed.toString() + " added " + sb.toString());
            cursorAdd = 5 + String.valueOf(z.a).length() + String.valueOf(z.b).length();
            cursor += cursorAdd;
            System.out.println("cursor "+ cursor + " cursorAdd " + cursorAdd);
        }
        return decompressed.toString();
    }

    public static String decompress (String compressed) {
        Scanner s = new Scanner(compressed);
        s.useDelimiter(OpenBracket);
//        s.useDelimiter("\\[?\\]?");
//        s.useDelimiter("(?=[\\[]|[\\|]|[\\]])");
//        ("\\[[0-9]+\\|[0-9]+\\|.?\n?\\]");
        StringBuilder decompressed = new StringBuilder();
        int cursor = 0;
        int a = -1;
        int b = -1;
        int strLen = compressed.length();
        while (s.hasNext()) {
            StringBuilder as = new StringBuilder();
            StringBuilder bs = new StringBuilder();
            while(s.hasNext(NUMPAT)){as.append(s.next());}
            a = Integer.parseInt(as.toString());
            require(BitOrPat, "no | after a ", s);
            while(s.hasNext(NUMPAT)){bs.append(s.next());}
            b = Integer.parseInt(bs.toString());
            require(BitOrPat, "no | after b ", s);
            String sub = "";
            String c = s.next();
            cursor = decompressed.length();
            if (a == 0 && b == 0) decompressed.append(c);
            else if (a > 0) {
//                System.out.println("cursor " + cursor + " offset " + a + " length " + b);
                sub = decompressed.substring((cursor - a), (cursor - a + b));
//                System.out.println("sub " + sub + " char " + c);
                decompressed.append(sub);
                decompressed.append(c);
            }
            require(CloseBracket, "no ] after c ", s);
            if(!s.hasNext(NUMPAT)){break;}
        }
        return decompressed.toString();
    }

    static String require(Pattern p, String message, Scanner s) {
        if (s.hasNext(p)) {
            return s.next();
        }
        fail(message, s);
        return null;
    }

    static void fail(String message, Scanner s) {
        String msg = message + "\n   @ ...";
        for (int i = 0; i < 5 && s.hasNext(); i++) {
            msg += " " + s.next();
        }
        throw new ParserFailureException(msg + "...");
    }
}



