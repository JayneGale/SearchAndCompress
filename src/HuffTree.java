public class HuffTree<NodeHuff> {
    NodeHuff root;
    NodeHuff parent;
    NodeHuff childLeft;
    NodeHuff childRight;
    char n;
    int frequency;
    int orderPriority;
    boolean isLeaf;
    String biCode = "";

    public HuffTree(char n, int frequency, int orderPriority, NodeHuff parent, NodeHuff childLeft, NodeHuff childRight, boolean isLeaf) {
        this.n = n;
        this.parent = parent;
        this.childLeft = childLeft;
        this.childRight = childRight;
        this.frequency = frequency;
        this.orderPriority = orderPriority;
        this.isLeaf = isLeaf;
//        this.biCode = biCode;
    }

}
