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
    static final Pattern OpenBracket = Pattern.compile("\\[");
    static final Pattern BitOrPat = Pattern.compile("\\|");
    static final Pattern CloseBracket = Pattern.compile("]");
    static final Pattern Delimiters = Pattern.compile("\\[*\\|*]*");
    /**
     * Take compressed input as a text string, decompress it, and return it as a
     * text string.
     */
    public static String decompress(String compressed) {
        StringBuilder decompressed = new StringBuilder();
        int cursor = 0;
        Scanner s = new Scanner(compressed);
        s.useDelimiter((Delimiters));
        while (s.hasNext()){
            System.out.println(decompressed.toString() + " " + s.toString());
            StringBuilder sb = new StringBuilder();
            int a = s.nextInt();
            int b = s.nextInt();
            if(a>0){
                sb.append(decompressed.substring((cursor - a), (cursor + b - a)));
            }
            sb.append(s.next());
            decompressed.append(sb);
            System.out.println(decompressed.toString());
        }
        // TODO (look up the require methods etc from Assignment 4).
        return decompressed.toString();
    }
}
