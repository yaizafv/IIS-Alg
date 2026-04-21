package p5;

import java.io.*;
import java.util.*;

public class Ferry {

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("fnf");
            System.exit(1);
        }

        Scanner sc = new Scanner(new File(args[0]));
        int L = sc.nextInt();
        List<Integer> vehiculos = new ArrayList<>();
        while (sc.hasNextInt()) {
            vehiculos.add(sc.nextInt());
        }
        sc.close();

        int n = vehiculos.size();
        int[] v = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            v[i] = vehiculos.get(i - 1);
        }

        int[] S = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            S[i] = S[i - 1] + v[i];
        }

        boolean[][] dp = new boolean[n + 1][L + 1];
        dp[0][0] = true;

        int k = 0;

        for (int i = 1; i <= n; i++) {
            boolean alguno = false;
            for (int p = 0; p <= L; p++) {
                if (!dp[i - 1][p])
                    continue;
                if (p + v[i] <= L) {        //babor
                    dp[i][p + v[i]] = true;
                    alguno = true;
                }
                if (S[i] - p <= L) {        //estribor
                    dp[i][p] = true;
                    alguno = true;
                }
            }
            if (!alguno)
                break;      //no entran mas
            k = i;
        }

        System.out.println("Han llegado un total de " + n + " vehículos (" + k + " viajarán).");

        System.out.println("Tabla con los cálculos realizados:");
        System.out.printf("%-4s", "V/L");
        for (int p = 0; p <= L; p++)
            System.out.printf("%3d", p);
        System.out.println();
        for (int i = 0; i <= k; i++) {
            System.out.printf("%-4d", i);
            for (int p = 0; p <= L; p++) {
                System.out.printf("%3s", dp[i][p] ? "T" : "F");
            }
            System.out.println();
        }
        System.out.println();

        int pActual = -1;
        for (int p = 0; p <= L; p++) {
            if (dp[k][p]) {
                pActual = p;
                break;
            }
        }

        String[] asignacion = new String[k + 1];
        for (int i = k; i >= 1; i--) {
            if (S[i] - pActual <= L && pActual <= L && dp[i - 1][pActual]) {
                asignacion[i] = "estribor";
            } else {
                asignacion[i] = "babor";
                pActual = pActual - v[i];
            }
        }

        System.out.println("Posible asignación:");
        int totalBabor = 0, totalEstribor = 0;
        for (int i = 1; i <= k; i++) {
            System.out.println("  Vehículo " + i + " (longitud " + v[i] + ") a " + asignacion[i] + ".");
            if (asignacion[i].equals("babor"))
                totalBabor += v[i];
            else
                totalEstribor += v[i];
        }
        System.out.println();
        System.out.println("Ocupación final: Babor " + totalBabor + "m / Estribor "
                + totalEstribor + "m (válido <= " + L + ").");
    }
}
