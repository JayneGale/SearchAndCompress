import org.w3c.dom.Node;

import java.util.*;
import java.io.*;

/**
 * A new instance of HuffmanCoding is created for every run. The constructor is
 * passed the full text to be encoded or decoded, so this is a good place to
 * construct the tree. You should store this tree in a field and then use it in
 * the encode and decode methods.
 */
public class HuffmanCoding {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please call this program with two arguments, which are " +
                               "the input file name and either 0 for constructing tree and printing it, or " +
                               "1 for constructing the tree and encoding the file and printing it, or " +
                               "2 for constructing the tree, encoding the file, and then decoding it and " +
                               "printing the result which should be the same as the input file.");
        } else {
            try {
                Scanner s = new Scanner(new File(args[0]));

                // Read the entire file into one String.
                StringBuilder fileText = new StringBuilder();
                while (s.hasNextLine()) {
                    fileText.append(s.nextLine() + "\n");
                }
                
                if (args[1].equals("0")) {
                    System.out.println(constructTree(fileText.toString()));
                } else if (args[1].equals("1")) {
                    constructTree(fileText.toString()); // initialises the tree field.
                    System.out.println(encode(fileText.toString()));
                } else if (args[1].equals("2")) {
                    constructTree(fileText.toString()); // initialises the tree field.
                    String codedText = encode(fileText.toString());
                     // DO NOT just change this code to simply print fileText.toString() back. ;-)
                    System.out.println(decode(codedText));
                } else {
                    System.out.println("Unknown second argument: should be 0 or 1 or 2");
                }
            } catch (FileNotFoundException e) {
                System.out.println("Unable to find file called " + args[0]);
            }
        }
    }

    // TODO add a field with your ACTUAL HuffmanTree implementation.
    private static Object tree; // Change type from Object to HuffmanTree or appropriate type you design.


    /**
     * This would be a good place to compute and store the tree.
     */
    public static Map<Character, String> constructTree(String text) {
//        Step 1: determine the frequency of each character in the text.
//        Set up the Node Class with int frequency initialised to -1
//        convert text to char array

        char[] T = text.toCharArray(); //text as char
        int tLength = T.length; // T
        int cFreq = 0;
//        create method for finding frequency within the text and number of unique characters != control characters
        HashMap<Character, Integer> cFreqMap = new HashMap<Character, Integer>();
        HashMap<Character, Integer> cPriority2Map = new HashMap<Character, Integer>();

//        for each character in  text from i = 0 to text.length
//        , if frequ <=0, sum the number of occurrences and store
//        until the end of the text
        int totalChars = 0;
        for (char c : T) {
//            ignore first 31 Ascii control characters
            if ((int)c > 32) {
                cFreqMap.put(c, cFreqMap.getOrDefault(c, 0) + 1);
                totalChars += 1;
            }
        }

        System.out.println("Character map size " + cFreqMap.size() + " total " + totalChars);
        float finalTotalChars = (float)totalChars;
        PriorityQueue<NodeHuff> forest = new PriorityQueue<NodeHuff>(cFreqMap.size());
// create the node
        for (Map.Entry<Character, Integer> entry : cFreqMap.entrySet()) {
            char ch = entry.getKey();
            int charAscii = (int) ch;
            cPriority2Map.put(ch, charAscii);
            float freq = entry.getValue() / finalTotalChars;
            int priority2;
            if (charAscii < 32) break; // ignore control characters
            if (charAscii == 32) priority2 = 0;
            else if (charAscii >= 97 && charAscii <= 122) priority2 = 1 + charAscii - 97; // lowercase characters first
            else if (charAscii >= 65 && charAscii <= 90) priority2 = 27 + charAscii - 65; // uppercase characters next
            else if (charAscii >= 48 && charAscii <= 58) priority2 = 53 + charAscii - 48; // digits next
            else priority2 = 63 + charAscii; // punctuation after that
            NodeHuff c = new NodeHuff(ch, entry.getValue(), priority2, null, null, null);
            forest.add(c);
            System.out.println(charAscii + " " + entry.getKey() + " " + entry.getValue() + " freq " + freq);
        }
        System.out.println(forest.size());
//TODO        Now give the priority queue its priorities


//        create the second priority - make a method that returns an ordered array
        char c = 'a';
        int charAscii = (int)c;
        int priority2 = 10000;
        Priority2 charPriority = null;
        switch(charPriority){
            case space:
                priority2 = 0;
                break;
            case lowercase:
                priority2 = 1 + (int)charAscii - 97;
                break;
            case uppercase:
                priority2 = 27 + (int)charAscii - 65;
                break;
            case digit:
                priority2 = 53 + (int)charAscii - 48;
                break;
            case other:
                break;
            case control:
                System.err.println("control character");
                break;
        }
 int[] priorityarr2 = new int[cFreqMap.size() + 63];
 priorityarr2[0] = 32; // space
 for (int i = 0; i < 26; i++) {
     priorityarr2[i + 1] = 97 + i; // lowercase ascii 97 - 122 97 + 25 = 122 in places 1-26
     priorityarr2[i + 27] = 65 + i; // uppercase ascii 65 - 90 in places 26 to 51
 }
for (int i = 0; i < 10; i++) {
    priorityarr2[53 + i] = 48 + i; // 0-9 ascii 48 - 57
}
System.out.println(Arrays.toString(priorityarr2));

//

        // TODO Construct the ACTUAL HuffmanTree here to use with both encode and decode below.
        // TODO fill this in.
        return new HashMap<Character, String>();
    }
    
    /**
     * Take an input string, text, and encode it with the tree computed from the text. Should
     * return the encoded text as a binary string, that is, a string containing
     * only 1 and 0.
     */
    public static String encode(String text) {
        // TODO fill this in.
        return "";
    }
    
    /**
     * Take encoded input as a binary string, decode it using the stored tree,
     * and return the decoded text as a text string.
     */
    public static String decode(String encoded) {
        // TODO fill this in.
        return "";
    }
}
