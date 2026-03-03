package p3p;

import java.util.Random;

public class PuntosTrivialTiempos {

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
		return new int[] { p1, p2 };
	}

	private static double distancia(double[] p1, double[] p2) {
		double x = p1[0] - p2[0];
		double y = p1[1] - p2[1];
		return Math.sqrt(x * x + y * y);
	}

	// main para probar funcionamiento y medir tiempos
	public static void main(String args[]) {
		long t1, t2;
		int nPuntos = Integer.parseInt(args[0]);
		t1 = System.currentTimeMillis();
		double[][] puntos = generarPuntosAleatorios(nPuntos);
		int[] resultado = puntosMasCercanos(puntos);
		double distanciaMin = distancia(puntos[resultado[0]], puntos[resultado[1]]);
		t2 = System.currentTimeMillis();
		System.out.println("**TIEMPO=" + (t2 - t1));
		System.out.println("Puntos más cercanos:");
		System.out.println("(" + puntos[resultado[0]][0] + ", " + puntos[resultado[0]][1] + ")");
		System.out.println("(" + puntos[resultado[1]][0] + ", " + puntos[resultado[1]][1] + ")");
		System.out.printf("Distancia mínima: %.6f\n", distanciaMin);
	} // main

	public static double[][] generarPuntosAleatorios(int n) {
		double[][] puntos = new double[n][2];
		Random rand = new Random();
		for (int i = 0; i < n; i++) {
			puntos[i][0] = rand.nextDouble() * 100;
			puntos[i][1] = rand.nextDouble() * 100;
		}
		return puntos;
	}
}
