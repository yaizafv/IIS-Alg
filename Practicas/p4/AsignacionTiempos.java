package p4;

import java.util.Random;

public class AsignacionTiempos {
    public static void main(String[] args) {
        int[] tamaños = {1024, 2048, 4096, 8192, 16384}; 
        System.out.println("n\t\tTiempo (ms)");
        System.out.println("---------------------------");

        for (int n : tamaños) {
            int[][] matriz = generarAleatoria(n);
            
            long t1 = System.currentTimeMillis();
            long p1 = Asignacion.algoritmo1(matriz);
            long p2 = Asignacion.algoritmo2(matriz);
            long max = Math.max(p1, p2);
            long t2 = System.currentTimeMillis();

            long total = t2 - t1;
            if (total > 60000) { 
                System.out.println(n + "\t\tFdT");
                break;
            } else {
                System.out.println(n + "\t\t" + total);
            }
        }
    }

    private static int[][] generarAleatoria(int n) {
        Random rnd = new Random();
        int[][] m = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m[i][j] = rnd.nextInt(9000) + 1000; 
            }
        }
        return m;
    }
}