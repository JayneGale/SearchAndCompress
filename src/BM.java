import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

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

//      pseudocode found at: http://personal.kent.edu/~rmuhamma/Algorithms/MyAlgorithms/StringMatch/boyerMoore.htm
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

    public static int search(String text, String pattern) {
        char[] P = pattern.toCharArray();
        char[] T = text.toCharArray();
        HashSet<Character> charSet = new HashSet<>();
        for (char c : T) {
            charSet.add(c);
        }
        for (char c : P){
            if(!charSet.contains(c)){ return -1;} //                if the character is not in the charSet of the text, there can be no match
        }
        System.out.println(text.substring(0, 30) + " pattern " + pattern);
        int pLen = P.length;
        int tLen = T.length;
        int[] GS1 = goodSuffixTable(P);
        System.out.println("GD1 " + GS1);

        int iT = pLen-1; // index of where the pattern is moved to in the Text
        int iP = pLen-1; // index of how far through the pattern
        System.out.println("pLen " + pLen + " tLen " + tLen + " iT " + iT + " iP " + iP);
        while(true){
            if (P[iP] == T[iT]) { // working from the end of the pattern
                System.out.println("Pj = Ti: I " + iT + P[iP] + " iP " + iP + T[iT]);
                if (iP == 0) { // if get to the beginning of the pattern found a match!
//                    System.out.println("iP = 0: Match " + iT + P[iP] + " iP " + iP + T[iT]);
                    return iT;  // Start of the pattern Matches the text at iT
                } else {
                    System.out.println("iP != 0: I " + iT + " iP " + iP );
                    iT = iT - 1;
                    iP = iP - 1;
                }
            }
            else { // not a match at iT iP
                int badCShift = pattern.lastIndexOf(T[iT]);
                System.out.println("badShift "  + badCShift  + " iT " + iT  + T[iT] + " iP " + iP  + P[iP]);

//                System.out.println("not a match: I " + iT + " iP " + iP );
//                int goodSShift = GS1[P[iP]];
;//                so move the pattern by the greatest amount out of the bad character and good suffix rules
                int goodShift = GS1[iP + 1];
                System.out.println("goodShift "  + goodShift  + " iT " + iT  + T[iT] + " iP " + iP  + P[iP]);
                int badCshift = pLen - Math.min(iP, badCShift + 1); // choose the smaller of GoodSuffix or the badCHar to move most down the text
                System.out.println("badCshift "  + badCshift  + " iT " + iT  + T[iT] + " iP " + iP  + P[iP]);
                iT = iT + Math.max(badCshift, goodShift); // badCshift the pattern (pLen - badCshift) down the text greatest move comes from least badCshift
                iP = pLen - 1; // start again from the end of the pattern
                System.out.println("badCShift: " + badCShift + " iT " +  iT + " iP " + iP);
            }
            if((tLen - iT < pLen) || (iT > tLen - 1)) break; // stop once the text left is shorter than the pattern left return no match or index is about to go out of bounds
        }
        return -1;
    }

//   based on goodSuffix Python code from https://iq.opengenus.org/boyer-moore-string-search-algorithm/

    public static int[] goodSuffixTable(char[] P) {
//        Each entry s[i] contains the shift distance of the pattern if a mismatch at position i – 1 occurs, i.e. if the suffix of the pattern starting at position i has matched.
        int pLen = P.length; // Pattern length
        int[] borderPos = new int[pLen + 2]; //borderPos is the position of the bordres of the suffix
        int[] shift1 = new int[pLen + 1];
//        think initialising int arrays to 0 in not necessary in Java
        Arrays.fill(shift1, 0, pLen + 1, 0);

        int i = pLen; // start at the end of the pattern
        int j = pLen + 1; // border exceeds the pattern
        System.out.println("end loop i  and j" + i + j);

        while (i > 0) {
            while (j < pLen && (P[i - 1] != P[j - 1]))
            {
                if (shift1[j] == 0) {shift1[j] = j - i; }
                j = borderPos[j];
            System.out.println("while j < pLen loop: i  and j" + i + j);
            }
        i -= 1;
        j -= 1;
        borderPos[i] = j;
        System.out.println("while i.0 loop: i  and j" + i + j);
        }
        j = borderPos[0] = 1;
        for (i = 0; i < pLen; i++){
            if(shift1[i] == 0){shift1[i] = j;}
            if(i == j) {j = borderPos[j];}
            }
        return shift1;
    }
}
