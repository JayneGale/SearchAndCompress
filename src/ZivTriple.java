public class ZivTriple {

    int a;
    int b;
    String c;
        public ZivTriple( int a, int b, String c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
        public ZivTriple getTriple( int a, int b, String c){
            ZivTriple z = new ZivTriple(a,b,c);
            return z;
        }
        public String toZString(ZivTriple z){
//            char ch = c.charAt(0);
            StringBuilder str = new StringBuilder("[");
            str.append(z.a);
            str.append("|");
            str.append(z.b);
            str.append("|");
//            if((int)c.charAt(0) ==10)str.append("nL");
//            else
            str.append(z.c);
            str.append("]");
        return str.toString();
        }
        public ZivTriple decompressZ(String zString){


            return new ZivTriple(zString.charAt(1),zString.charAt(3),Character.toString(zString.charAt(5)));
        }
    }

