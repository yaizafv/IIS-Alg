package p3p;

import java.util.Random;

public class PuntosDyVTiempos {

    public static double[][] generarPuntos(int n) {
        Random rnd = new Random();
        double[][] puntos = new double[n][2];
        for (int i = 0; i < n; i++) {
            puntos[i][0] = rnd.nextDouble() * 100.0;
            puntos[i][1] = rnd.nextDouble() * 100.0;
        }
        return puntos;
    }

    public static void main(String[] arg) {
        System.out.println("PuntosDyVTiempos - Complejidad O(n*log^2 n)");
        System.out.println("n\t\tTIEMPO(ms)");

        for (int n = 1024; ; n *= 2) {
            double[][] puntos = generarPuntos(n);

            long t1 = System.currentTimeMillis();
            double[] res = PuntosDyV.resolver(puntos);
            long t2 = System.currentTimeMillis();

            long tiempo = t2 - t1;
            System.out.println("n=" + n + "\t\tTIEMPO=" + tiempo + " ms");

            if (tiempo > 60000) {
                System.out.println("FdT para n=" + (n * 2));
                break;
            }
        }
    }
}
