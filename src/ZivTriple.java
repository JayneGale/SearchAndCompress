public class ZivTriple {

    int a;
    int b;
    char c;
        public ZivTriple( int a, int b, char c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
        public ZivTriple getTriple( int a, int b, char c){
            ZivTriple z = new ZivTriple(a,b,c);
            return z;
        }
        public String toZString(ZivTriple z){
            StringBuilder str = new StringBuilder("[");
//            str.append("[");
            str.append(z.a);
            str.append("|");
            str.append(z.b);
            str.append("|");
            if((int)c==10)str.append("newL");
            else str.append(z.c);
            str.append("]");
        return str.toString();
        }
    }

