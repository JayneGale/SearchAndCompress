import java.util.*;
import java.io.*;
import java.util.PriorityQueue;
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
        HashMap<Character, Integer> alphOrderMap = new HashMap<Character, Integer>();

//        for each character in  text from i = 0 to text.length
//        if frequ <=0, start counting; sum the number of occurrences and store
//        until the end of the text
        int totalChars = 0;
        for (char c : T) {
//            ignore first 31 Ascii control characters
            if ((int)c > 31) {
                cFreqMap.put(c, cFreqMap.getOrDefault(c, 0) + 1);
                totalChars += 1;
            }
        }

        System.out.println("Character map size " + cFreqMap.size() + " total chars in text " + totalChars);
//        float finalTotalChars = (float)totalChars; // float to make division easier
        PriorityQueue<NodeHuff> forest = new PriorityQueue<>(cFreqMap.size(), new FreqComparator());
    //        create the second alphabetical priority based on ascii codes
    //        create the Leaf nodes that contain a character

        for (Map.Entry<Character, Integer> entry : cFreqMap.entrySet()) {
            char ch = entry.getKey();
            int charAscii = (int) ch;
            alphOrderMap.put(ch, charAscii);
            float freq = entry.getValue();
            int alphPriority;
            if (charAscii < 32 || charAscii == 127) break; // ignore control characters
            else if (charAscii == 32) alphPriority = 0;
            else if (charAscii >= 97 && charAscii <= 122) alphPriority = charAscii - 96; //  charAscii + 1 - 97; // lowercase characters first
            else if (charAscii >= 65 && charAscii <= 90) alphPriority = charAscii - 38; // charAscii + 26 + 1 - 65; uppercase characters next
            else if (charAscii >= 48 && charAscii <= 58) alphPriority = charAscii - 5; // charAscii 53 (10 + 26 + 26 + 1) - 48 digits next +
            else alphPriority = charAscii + 31 ; // 63 (10 + 26 + 26 + 1) - 32 punctuation after that
            NodeHuff c = new NodeHuff(ch, entry.getValue(), alphPriority, null, null, null, true);
            forest.add(c);
            System.out.println(charAscii + " " + entry.getKey() + " " + entry.getValue() + " freq " + freq);
        }
        for (NodeHuff n : forest){
            System.out.println(n.n + " freq " + n.frequency + " alph order " + n.orderPriority);
        }
        System.out.println(forest.size() + " peek " + forest.peek().n);
//        Step 1:
//        Create a new NodeHuff
//        - the lowest priority node is childRight
//        - the second lowest priority node is childLeft
        while(forest.size()>1){
            NodeHuff lowestNode = forest.poll();
            NodeHuff secondNode = forest.poll();
            NodeHuff joinedNode = new NodeHuff(lowestNode.n, (lowestNode.frequency + secondNode.frequency), lowestNode.orderPriority, null, secondNode, lowestNode, false);
            lowestNode.parent = joinedNode;
            secondNode.parent = joinedNode;
            forest.add(joinedNode);
            System.out.println(joinedNode.n + "freq "  + joinedNode.frequency + " order "+ joinedNode.orderPriority + " left " + joinedNode.childLeft.n + " right " + joinedNode.childRight.n);
        }




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
