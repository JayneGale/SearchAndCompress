import java.util.*;
import java.io.*;
import java.util.regex.Pattern;

public class BM {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please call this program with " +
                               "two arguments which is the input file name " +
                               "and the string to search.");
        } else {
            try {
                Scanner s = new Scanner(new File(args[0]));

                // Read the entire file into one String.
                StringBuilder fileText = new StringBuilder();
                while (s.hasNextLine()) {
                    fileText.append(s.nextLine() + "\n");
                }

                System.out.println(search(fileText.toString(), args[1]));
            } catch (FileNotFoundException e) {
                System.out.println("Unable to find file called " + args[0]);
            }
        }
    }

    /**
     * Perform BM substring search on the given text with the given pattern.
     * 
     * This should return the starting index of the first substring match if it
     * exists, or -1 if it doesn't.
     */
//    pseudocode found at: http://personal.kent.edu/~rmuhamma/Algorithms/MyAlgorithms/StringMatch/boyerMoore.htm

    public static int search(String text, String pattern) {
        char[] P = pattern.toCharArray();
        char[] T = text.toCharArray();
//        System.out.println(text.substring(0, 30));

        int m = P.length;
        int n = T.length;
        int i = m-1;
        int j = m-1;
//        System.out.println("m " + m + " n " + n + " i " + i + " j " + j);
        while(true){
            if (P[j] == T[i]) {
//                System.out.println("Pj = Ti: I " + i + P[j] + " j " + j + T[i]);
                if (j == 0) {
//                    System.out.println("j = 0: Match " + i + P[j] + " j " + j + T[i]);
                    return i;  //Match at i
                } else {
//                    System.out.println("j != 0: I " + i + " j " + j );
                    i = i - 1;
                    j = j - 1;
                }
            }
            else {
                int lastIndex = pattern.lastIndexOf(T[i]);
                int x = (j < lastIndex + 1) ? j : lastIndex + 1;
                i += m - x;
                j = m - 1;
//                System.out.println("lastIndex: " + lastIndex + " i " +  i + " j " + j);
            }
            if(i > n - 1) break;
        }
        return -1;
    }
//    public static int last(char c, String P){
//        return P.lastIndexOf(c);
//    }

//    Compute function last
//    i ← m-1
//    j ← m-1
//    Repeat
//    If P[j] = T[i] then
//        if j=0 then
//            return i        // we have a match
//        else
//    i ← i -1
//    j ← j -1
//            else
//    i ← i + m - Min(j, 1 + last[T[i]])
//    j ← m -1
//    until i > n -1
//    Return "no match"
}
