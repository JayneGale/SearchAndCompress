import java.util.Comparator;

public class FreqComparator implements Comparator<NodeHuff>{
        public int compare(NodeHuff h1, NodeHuff h2) {
            if (h1.frequency < h2.frequency) return -1; // prefer lower frequencies
            else if (h1.frequency > h2.frequency) return 1;
            else if (h1.orderPriority < h2.orderPriority) return -1; // h1 and h2 frequency are equal, prefer earlier in Alex's alphabet order
            else return 1;
        }
    }