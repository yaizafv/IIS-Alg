package p3p;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PuntosDyV {

    static double minDist;
    static double[] puntoA = new double[2];
    static double[] puntoB = new double[2];

    public static double[][] leerPuntos(String fichero) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fichero));
        int n = Integer.parseInt(br.readLine().trim());
        double[][] puntos = new double[n][2];
        for (int i = 0; i < n; i++) {
            String[] partes = br.readLine().trim().split(",");
            puntos[i][0] = Double.parseDouble(partes[0]);
            puntos[i][1] = Double.parseDouble(partes[1]);
        }
        br.close();
        return puntos;
    }

    public static double dist(double[] p1, double[] p2) {
        double dx = p1[0] - p2[0];
        double dy = p1[1] - p2[1];
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static double dyv(double[][] puntos, int iz, int de) {
        if (de - iz <= 2) {
            return fuerzaBruta(puntos, iz, de);
        }

        int mid = (iz + de) / 2;
        double xMid = puntos[mid][0];

        double d1 = dyv(puntos, iz, mid);
        double d2 = dyv(puntos, mid + 1, de);
        double delta = Math.min(d1, d2);

        List<double[]> franja = new ArrayList<>();
        for (int i = iz; i <= de; i++) {
            if (Math.abs(puntos[i][0] - xMid) < delta) {
                franja.add(puntos[i]);
            }
        }

        franja.sort((a, b) -> Double.compare(a[1], b[1]));

        for (int i = 0; i < franja.size(); i++) {
            for (int j = i + 1; j < franja.size()
                    && (franja.get(j)[1] - franja.get(i)[1]) < delta; j++) {
                double d = dist(franja.get(i), franja.get(j));
                if (d < delta) {
                    delta = d;
                    if (d < minDist) {
                        minDist = d;
                        puntoA[0] = franja.get(i)[0];
                        puntoA[1] = franja.get(i)[1];
                        puntoB[0] = franja.get(j)[0];
                        puntoB[1] = franja.get(j)[1];
                    }
                }
            }
        }
        return delta;
    }

    public static double fuerzaBruta(double[][] puntos, int iz, int de) {
        double minLocal = Double.MAX_VALUE;
        for (int i = iz; i <= de; i++) {
            for (int j = i + 1; j <= de; j++) {
                double d = dist(puntos[i], puntos[j]);
                if (d < minLocal) {
                    minLocal = d;
                    if (d < minDist) {
                        minDist = d;
                        puntoA[0] = puntos[i][0];
                        puntoA[1] = puntos[i][1];
                        puntoB[0] = puntos[j][0];
                        puntoB[1] = puntos[j][1];
                    }
                }
            }
        }
        return minLocal;
    }

    public static double[] resolver(double[][] puntos) {
        Arrays.sort(puntos, (a, b) -> Double.compare(a[0], b[0]));

        minDist = Double.MAX_VALUE;
        puntoA = new double[2];
        puntoB = new double[2];

        dyv(puntos, 0, puntos.length - 1);

        return new double[] { puntoA[0], puntoA[1], puntoB[0], puntoB[1], minDist };
    }

    public static void main(String[] arg) throws IOException {
        if (arg.length < 1) {
            System.out.println("fnf");
            return;
        }
        double[][] puntos = leerPuntos(arg[0]);
        double[] res = resolver(puntos);

        System.out.printf("PUNTOS MAS CERCANOS: [%.6f, %.6f] [%.6f, %.6f]%n",
                res[0], res[1], res[2], res[3]);
        System.out.printf("SU DISTANCIA MINIMA= %.6f%n", res[4]);
    }
}
