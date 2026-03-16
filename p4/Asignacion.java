package p4;

import java.io.*;

public class Asignacion {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Uso: java p4.Asignacion <nombreFichero>");
            return;
        }

        int[][] matriz = leerMatriz(args[0]);
        if (matriz == null) return;

        long p1 = algoritmo1(matriz);
        long p2 = algoritmo2(matriz);

        System.out.println("Productividad P1 (Algoritmo 1): " + p1);
        System.out.println("Productividad P2 (Algoritmo 2): " + p2);
        System.out.println("Asignación final (Máximo): " + Math.max(p1, p2));
    }

    public static long algoritmo1(int[][] m) {
        int n = m.length;
        boolean[] puestoOcupado = new boolean[n];
        long suma = 0;

        for (int i = 0; i < n; i++) { 
            int mejorPuesto = -1;
            int maxProd = -1;
            for (int j = 0; j < n; j++) {
                if (!puestoOcupado[j] && m[i][j] > maxProd) {
                    maxProd = m[i][j];
                    mejorPuesto = j;
                }
            }
            if (mejorPuesto != -1) {
                suma += maxProd;
                puestoOcupado[mejorPuesto] = true;
            }
        }
        return suma;
    }

    public static long algoritmo2(int[][] m) {
        int n = m.length;
        boolean[] empleadoAsignado = new boolean[n];
        long suma = 0;

        for (int j = 0; j < n; j++) { 
            int mejorEmpleado = -1;
            int maxProd = -1;
            for (int i = 0; i < n; i++) {
                if (!empleadoAsignado[i] && m[i][j] > maxProd) {
                    maxProd = m[i][j];
                    mejorEmpleado = i;
                }
            }
            if (mejorEmpleado != -1) {
                suma += maxProd;
                empleadoAsignado[mejorEmpleado] = true;
            }
        }
        return suma;
    }

    private static int[][] leerMatriz(String nombre) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombre))) {
            int n = Integer.parseInt(br.readLine().trim());
            int[][] m = new int[n][n];
            for (int i = 0; i < n; i++) {
                String[] linea = br.readLine().split(",");
                for (int j = 0; j < n; j++) {
                    m[i][j] = Integer.parseInt(linea[j].trim());
                }
            }
            return m;
        } catch (Exception e) {
            System.err.println("Error al leer el fichero: " + e.getMessage());
            return null;
        }
    }
}