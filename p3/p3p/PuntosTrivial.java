package p3p;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PuntosTrivial {
	
	public static int[] puntosMasCercanos(double[][] puntos) {
		double minDist = Double.POSITIVE_INFINITY;
		int p1 = 0;
		int p2 = 1;
		for (int i = 0; i < puntos.length - 1; i++) {
			for (int j = i + 1; j < puntos.length; j++) {
				double dist = distancia(puntos[i], puntos[j]);
				if (dist < minDist) {
					minDist = dist;
					p1 = i;
					p2 = j;
				}
			}
		}
		return new int[]{p1, p2};
	}

	private static double distancia(double[] p1, double[] p2) {
		double x = p1[0] - p2[0];
		double y = p1[1] - p2[1];
		return Math.sqrt(x * x + y * y);
	}

	// main para probar funcionamiento y medir tiempos
	public static void main(String args[]) {
		try {
			double[][] puntos = leerFichero(args[0]);
			int[] resultado = puntosMasCercanos(puntos);
			double distanciaMin = distancia(puntos[resultado[0]], puntos[resultado[1]]);
            System.out.println("Puntos más cercanos:");
            System.out.println("(" + puntos[resultado[0]][0] + ", " + puntos[resultado[0]][1] + ")");
            System.out.println("(" + puntos[resultado[1]][0] + ", " + puntos[resultado[1]][1] + ")");
            System.out.printf("Distancia mínima: %.6f\n", distanciaMin);
		} catch (IOException e) {
			System.out.println("Error leyendo el fichero: " + e.getMessage());
		}

	} // main

	public static double[][] leerFichero(String nombreFichero) throws IOException {

		List<double[]> lista = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    
                    linea = linea.trim();
                    if (linea.isEmpty())
                        continue;
                    
                    String[] partes = linea.split("\\s+");
                    
                    double x = Double.parseDouble(partes[0]);
                    double y = Double.parseDouble(partes[1]);
                    
                    lista.add(new double[] { x, y });
                }
            }

		return lista.toArray(new double[0][0]);
	}

}
