import java.util.Scanner;

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
            Scanner s = new Scanner(zString);
            s.useDelimiter("[\\[]|[\\|]|[\\]]");

//            while(s.hasNext()){
//                System.out.println("33 zTriple " + s.next());
//            }
            int aDec = s.nextInt();
            int bDec = s.nextInt();
            String cDec = s.next();
            cDec = cDec.replace("]", "");
            System.out.println("decompressZ " + " a " + aDec+ " b " + bDec + " c " + cDec);

//            int aDec = Integer.parseInt(zString.substring(1));
//            int bDec = Integer.parseInt(zString.substring(3,4));
//            System.out.println("decompressZ " + aDec + bDec + zString.substring(5,6));
            return new ZivTriple(aDec,bDec,cDec);
        }
    }

