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
        HashMap<Character, Integer> cFreqMap = new HashMap<Character, Integer>();
        int totalChar = 0;
        for (char c : T) {
            int charAscii = (int) c;
            if (c != 10) {
                cFreqMap.put(c, cFreqMap.getOrDefault(c, 0) + 1);
                totalChar +=1;
            }
        }
        System.out.println("Character map size " + cFreqMap.size() + " total " + totalChar);
        float finalTotalChar = (float)totalChar;
        cFreqMap.entrySet().forEach(entry -> {
            int charAscii = (int)entry.getKey();
            if (charAscii != 10){
//                cFreqMap.put(entry.getKey(), );
                float freq = entry.getValue()/ finalTotalChar;
                System.out.println(charAscii + " " + entry.getKey() + " " + entry.getValue() + " freq " + freq);
            }
        }
        );
 int[] priority2 = new int[63];
 priority2[0] = 32; // space
 for (int i = 0; i < 26; i++) {
     priority2[i + 1] = 97 + i; // lowercase ascii 97 - 122 97 + 25 = 122 in places 1-26
     priority2[i + 27] = 65 + i; // uppercase ascii 65 - 90 in places 26 to 51
 }
// not quite there with the upper case characters
for (int i = 0; i < 10; i++) {
    priority2[53 + i] = 48 + i; // 0-9 ascii 48 - 57 and 7 left over
}
System.out.println(Arrays.toString(priority2));

// comment out the newline characters "+ \n"
        //        the total number of characters and spaces in the text

//        for each character in  text from i = 0 to text.length
//        , if frequ <=0, sum the number of occurrences and store
//        until the end of the text
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
