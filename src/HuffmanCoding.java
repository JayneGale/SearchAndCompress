import java.time.Duration;
import java.time.Instant;
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
//                    Instant start = Instant.now();
                    constructTree(fileText.toString()); // initialises the tree field.
                    String codedText = encode(fileText.toString());
                    // DO NOT just change this code to simply print fileText.toString() back. ;-)
                    System.out.println(decode(codedText));
//                    Instant end = Instant.now();
//                    Duration timeElapsed = Duration.between(start, end);
//                    double deltaT = (double) timeElapsed.toMillis() / 1000;
//                    System.out.println("Time taken: " + deltaT + " seconds");

                } else {
                    System.out.println("Unknown second argument: should be 0 or 1 or 2");
                }
            } catch (FileNotFoundException e) {
                System.out.println("Unable to find file called " + args[0]);
            }
        }

    }

    /**
     * This would be a good place to compute and store the tree.
     */
    private static HuffTree tree = new HuffTree(); // Change type from Object to HuffmanTree or appropriate type you design.
    private static Map<Character, String> encoding = new HashMap<>();
//    private static final Map<String, Character> decoding = new HashMap<>();

    public static HuffTree constructTree(String text) {
//        Step 1: determine the frequency of each character in the text.
//        Set up the Node Class with int frequency initialised to -1
//        convert text to char array
        char[] T = text.toCharArray();

        //        create method for finding frequency within the text and number of unique characters
        HashMap<Character, Integer> cFreqMap = new HashMap<Character, Integer>();

//        for each character in  text
//        if freq = default start at 0, else add to count; store in character frequency Map
        for (char c : T) {
            cFreqMap.put(c, cFreqMap.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<NodeHuff> forest = new PriorityQueue<>(cFreqMap.size(), new FreqComparator());
        for (Map.Entry<Character, Integer> entry : cFreqMap.entrySet()) {
//      Step 2         create the second (alphabet) priority based on ascii codes
            int charAscii = (int) entry.getKey();
//            if (charAscii < 31 || charAscii == 127) break; // ignore control characters except newline
            int alphPriority = setAlphabetPriority(charAscii);
            //        create the Leaf nodes that contain a character and put each as a separate item in the forest of nodes
            NodeHuff c = new NodeHuff(entry.getKey(), entry.getValue(), alphPriority, null, null, null, true);
            forest.add(c);
        }

//        Step 1:
//        Create new "join" NodeHuff with
//        - the lowest priority node is childLeft
//        - the second lowest priority node is childRight
//        - freq = sum of the two child frequencies
//        - isLeaf set to false
//        - alphPriority set to the child char that is chosen first in alphabet priority (lowest index)
//        - remove the children from the forest (poll) and add the joined node back into the priority queue forest
//        - continue until reach one (root) node left in the forest - all nodes are now in the tree
        while(forest.size()>1){
            NodeHuff lowestNode = forest.poll();
            NodeHuff secondNode = forest.poll();
            NodeHuff joinedNode = new NodeHuff(lowestNode.n, (lowestNode.frequency + secondNode.frequency), lowestNode.orderPriority, null, lowestNode, secondNode, false);
            lowestNode.parent = joinedNode;
            secondNode.parent = joinedNode;
            forest.add(joinedNode);
//            System.out.println("Joined left " + joinedNode.childLeft.n + " right " + joinedNode.childRight.n + " joined freq "  + joinedNode.frequency + " 2nd order " + joinedNode.orderPriority + "least alph " + joinedNode.n  );
        }
//        the last element left is the root of the new tree with no code, no parent, and already has its 2 children and frequency
        NodeHuff root = forest.poll();
        root.addBiCode("");
        tree.root = root;

//      now set the binary code from the root and store the leaf values and code in a HashMap encoding
//      - each child node, add 0 for a left child and a 1 for a right child
        encoding = iterTree(tree.root);
        return tree;  }

    private static Map<Character, String> iterTree(NodeHuff n) {
        NodeHuff left = n.childLeft;
        NodeHuff right = n.childRight;
        left.biCode = n.biCode + "0";
        right.biCode = n.biCode + "1";
        if(!left.isLeaf){
            encoding = iterTree(left); }
        else { encoding.put(left.n, left.biCode);
        }
        if(!right.isLeaf){
            encoding  = iterTree(right); }
        else { encoding.put(right.n, right.biCode);
//            System.out.println("tree " + right.n + " right value " + encoding.get(right.n) + " isLeaf " + right.isLeaf);
        }
        return encoding;
    }

    private static int setAlphabetPriority(int charAscii) {
        int alphPriority;
        if (charAscii == 32) alphPriority = 0;
        else if (charAscii >= 97 && charAscii <= 122) alphPriority = charAscii - 96; //  charAscii + 1 - 97; // lowercase characters first
        else if (charAscii >= 65 && charAscii <= 90) alphPriority = charAscii - 38; // charAscii + 26 + 1 - 65; uppercase characters next
        else if (charAscii >= 48 && charAscii <= 58) alphPriority = charAscii - 5; // charAscii 53 (10 + 26 + 26 + 1) - 48 digits next +
        else alphPriority = charAscii + 63 ; // 63 (10 + 26 + 26 + 1) - 30 punctuation after that
        return alphPriority;
    }

    /**
     * Take an input string, text, and encode it with the tree computed from the text. Should
     * return the encoded text as a binary string, that is, a string containing
     * only 1 and 0.
     */
    public static String encode(String text) {
        System.out.println(encoding.size() + " codeSet "  + encoding.entrySet());
        char[] T = text.toCharArray();
        StringBuilder code = new StringBuilder();
        for(char c : T){code.append(encoding.get(c)); }
//        System.out.println("Encoded " + code);
        return code.toString();
    }
    
    /**
     * Take encoded input as a binary string, decode it using the stored tree,
     * and return the decoded text as a text string.
     */
    public static String decode(String encoded) {
        StringBuilder decodedText = new StringBuilder();
        if (tree.root != null) {
            char[] E = encoded.toCharArray();
            decodedText = iterTreeInverse(E);
        }
        return decodedText.toString();
    }

    private static StringBuilder iterTreeInverse(char[] E) {
        StringBuilder decodedText = new StringBuilder();
        String binary = "";
        NodeHuff node = tree.root;

        for (int i = 0; i < E.length; i++){
            binary = Character.toString(E[i]);
            if (binary.equals("0")) {
                node = node.childLeft;
            } else { // binary.equals("1")
                node = node.childRight;
            }
            if (node.isLeaf) {
                decodedText.append(node.n);
                node = tree.root;}
        }
//        System.out.println("Decoded " + decodedText.toString());
        return decodedText;
    }
    //        Reversing the encoding Map to a decoding Map is O(N squared) -  takes way too long for the longer texts
//        for (Map.Entry<Character, String> entry : encoding.entrySet()) {
//            Character ch = (char)entry.getKey();
//            String val = (String)entry.getValue();
//
//            //        create a second Map the reverse of the first
//            decoding.put(val,ch);
//        }
//        System.out.println("Decoding " + decoding.entrySet());

}
