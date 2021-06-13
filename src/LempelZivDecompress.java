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
    static final Pattern OpenBracket = Pattern.compile("\\[");
    static final Pattern BitOrPat = Pattern.compile("\\|");
    static final Pattern CloseBracket = Pattern.compile("]");
    static final Pattern Delimiters = Pattern.compile("\\[|\\||]");
    private static final Pattern NUMPAT = Pattern.compile("-?[1-9][0-9]*|0");

//    static ZivTriple parseFile(File compressedFile) {
//        Scanner scan = null;
//        try {
//            scan = new Scanner(compressedFile);
//            // the only time tokens can be next to each other is
//            // when one of them is one of (){},;
////            scan.useDelimiter("\\s+|(?=[{}(),;])|(?<=[{}(),;])");
//            scan.useDelimiter("\\s+|(?=[\\[]|[\\|]|[\\]])");
////            scan.useDelimiter("\\s+|(?=["OpenBracket"]|["BitOrPat"]|["CloseBracket"])");
//
//            ZivTriple z = parseCompressed(scan); // You need to implement this!!!
//            scan.close();
//            return z;
//
//        } catch (FileNotFoundException e) {
//            System.out.println("Robot program source file not found");
//        } catch (ParserFailureException e) {
//            System.out.println("Parser error:");
//            System.out.println(e.getMessage());
//            scan.close();
//        }
//        return null;
//    }
//
//    public static ZivTriple parseCompressed(Scanner comprFile) {
//        return new ZivTriple(0, 0, "");
//    }

    public static String decompress(String compressed) {
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
                System.out.println(decompressed.substring(offset,length));
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

    public static String decompressSc (String compressed) {
        Scanner s = new Scanner(compressed);
        s.useDelimiter("(?=[\\[]|[\\|]|[\\]])");

        StringBuilder decompressed = new StringBuilder();
        int cursor = 0;
//        int strLen = compressed.length();
        while (s.hasNext()) {
            System.out.println(decompressed.toString() + " " + s.toString());
            int a = requireInt(NUMPAT, "no int ", s);
            int b = requireInt(NUMPAT, "no int ", s);;
            if (a > 0) {
                decompressed.append(decompressed.substring((cursor - a), (cursor + b - a)));
            }
//            sb.append(s.next());
            decompressed.append(s.next());
            System.out.println(decompressed.toString());
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

    static int requireInt(Pattern p, String message, Scanner s) {
        if (s.hasNext(p) && s.hasNextInt()) {
            return s.nextInt();
        }
        fail(message, s);
        return -1;
    }
}



