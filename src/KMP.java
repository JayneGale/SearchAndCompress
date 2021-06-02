import java.util.*;
import java.io.*;

public class KMP {

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
     * Perform KMP substring search on the given text with the given pattern.
     * 
     * This should return the starting index of the first substring match if it
     * exists, or -1 if it doesn't.
     */
    public static int search(String text, String pattern) {
        // TODO find first occurrence of pattern S in text T
//        pattern = "4447";
//        The starting index of the first match in the text, or -1 if no match

//        – T.indexOf(S);  indexOf() returns the position of the first occurrence of specified character(s) in a string.
//        – T.lastIndexOf(S);
//        – T.contains(S);
//        KMP algorithm - first form a jump table for the pattern
        char[] S = pattern.toCharArray(); //patterm as char
        int sLength = S.length; // S
        char[] T = text.toCharArray(); //text as char
        int tLength = T.length; // T
        System.out.println("T length " + tLength + " S length " + sLength);

//        char[] partT = new char[128];
//        System.arraycopy(T, 0, partT, 0,128);
//        System.out.println("text " + Arrays.toString(partT));

        //   build the jumpTable
        int[] M = createJumpTable(S);
        System.out.println("jump table M " + Arrays.toString(M) + " for String " + Arrays.toString(S));
//        System.out.println("T length " + tLength + " S length " + sLength);

        // KMP algorithm
        int k = 0; // start of current match in T; initially at the first character T[0]
        int i = 0; // position of current character in S, initially at S[0]

        while (k + i < tLength) { // can stop before text length - no need to search if the remaining string is longer than the text left to search
//            System.out.println("T[ " + k + "] " + T[k] );
            if (S[i] == T[k + i]) {
//                System.out.println("match at k= " + k + " T[k+1] " + T[k + 1] + " i= " + i + " S[i] " + S[i]);
                i++;
                if (i == sLength) { // if whole string matches! Yay
                    return k;
                }
            }
            else if (M[i] == -1) { // the only time M[i] = -1 is when it fails on the first letter?? i = 0
                k = k + i + 1;
                i = 0; // mismatch with no self overlap = jumo forward the whole length of S
//                System.out.println("mismatch at k " + k);
            }
            else {
//                System.out.println("mismatch at k: " + k + " i " + i + " M[i] " + M[i]);
                k = k + i - M[i]; // here is a jump
                i = M[i];
            }
            }
        return -1;
    }
    public static int[] createJumpTable(char[] S) {
        int sLength = S.length; // S
        int[] M = new int[sLength]; //M
        M[0] = -1;
        M[1] = 0;
        int j = 0; //position in prefix
        int pos = 2;  // the index of M table .. already have pos 0 and 1
        while(pos < sLength){
            if (S[pos - 1] == S[j]) { // substrings pos -1 and 0-j match
                M[pos] = j+1;
                pos++;
                j++;
            }
            else if(j > 0){
                j = M[j]; // mismatch, restart the prefix
            }
            else{ //ie j= 0, the matching prefix is 0 long
                M[pos] = 0;
                pos++;
            }
        }
        return M;
    }
    }
