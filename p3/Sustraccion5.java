package p3;

public class Sustraccion5 {

    static long cont;

    public static void rec5(int n) {
        cont = 0;

        for (int i = 0; i < (3 * n) / 2; i++) {
            cont++; 
        }
    }

    public static void main(String arg[]) {
        long t1, t2, cont;
        int nVeces = Integer.parseInt(arg[0]);

        for (int n = 20; n <= 100; n++) {
            t1 = System.currentTimeMillis();

            for (int repeticiones = 1; repeticiones <= nVeces; repeticiones++) {
                cont = 0;
                rec5(n);
            }

            t2 = System.currentTimeMillis();

            System.out.println(" n=" + n + "**TIEMPO=" + (t2 - t1) + "**nVeces=" + nVeces);
        } // for
    } // main
} // class