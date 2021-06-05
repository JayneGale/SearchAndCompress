public class NodeHuff {
//prioritise by lowest frequency first
//    If the two nodes have the same frequency in the priority queue,
//    pick first the nodes with the smallest characters alphabetically.
//    space  first 32
//    then lower case letters, a-z 97 - 122
//    then upper case letters, A-Z 65 - 90
//    then digits 48- 57
//    special characters?? any after that are higher in PQ
    public int frequency = -1;
    public int otherPriority = -1;
    public char n;
    public Boolean isLeaf;
    public NodeHuff childLeft, childRight; // these should be nodes not Leafs
    public NodeHuff parent; // these should be nodes not Leafs

//    TODO Up to here setting up the Node Object class for the tree
    public NodeHuff(char n, int frequency, int otherPriority, NodeHuff parent, NodeHuff childLeft, NodeHuff childRight) {
        this.n = n;
        this.frequency = frequency;
        this.otherPriority = otherPriority;
        this.parent = parent;
        this.childLeft = childLeft;
        this.childRight = childRight;
    }
}
//import java.util.ArrayList;
//
//public class APObject {
//    public final Node n;
//    public int depth;
//    public int reachBack;
//    public APObject parent;
//    public ArrayList<APObject> children;
//
//    public APObject(Node n, int depth, int reachBack, APObject parent, ArrayList<APObject> children) {
//        this.n = n;
//        this.depth = depth;
//        this.reachBack = reachBack;
//        this.parent = parent;
//        this.children = children;
//    }
//
//}