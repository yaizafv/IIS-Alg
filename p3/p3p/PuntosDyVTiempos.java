package p3p;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PuntosDyVTiempos {

	// Clase auxiliar para manejar los resultados (puntos y distancia)
    public static class Resultado {
        int p1, p2;
        double distancia;

        public Resultado(int p1, int p2, double distancia) {
            this.p1 = p1;
            this.p2 = p2;
            this.distancia = distancia;
        }
    }

    public static Resultado puntosMasCercanos(double[][] puntos) {
		if (puntos == null || puntos.length < 2) return null;
        // Creamos un array de índices para no perder la referencia original del fichero
        Integer[] indices = new Integer[puntos.length];
        for (int i = 0; i < puntos.length; i++) indices[i] = i;

        // 1. Pre-ordenar por coordenada X
        Arrays.sort(indices, (i1, i2) -> Double.compare(puntos[i1][0], puntos[i2][0]));

        return calcularRecursivo(puntos, indices, 0, puntos.length);
    }

    private static Resultado calcularRecursivo(double[][] puntos, Integer[] indicesX, int izq, int der) {
        int medio = izq + (der - izq) / 2;
        double lineaDivision = puntos[indicesX[medio]][0];

        Resultado resIzq = calcularRecursivo(puntos, indicesX, izq, medio);
        Resultado resDer = calcularRecursivo(puntos, indicesX, medio, der);

        Resultado minRes = (resIzq.distancia < resDer.distancia) ? resIzq : resDer;
        double d = minRes.distancia;

        List<Integer> franja = new ArrayList<>();
        for (int i = izq; i < der; i++) {
            if (Math.abs(puntos[indicesX[i]][0] - lineaDivision) < d) {
                franja.add(indicesX[i]);
            }
        }
		

        franja.sort((i1, i2) -> Double.compare(puntos[i1][1], puntos[i2][1]));

        for (int i = 0; i < franja.size(); i++) {
            for (int j = i + 1; j < franja.size() && (puntos[franja.get(j)][1] - puntos[franja.get(i)][1]) < d; j++) {
                double dist = distancia(puntos[franja.get(i)], puntos[franja.get(j)]);
                if (dist < d) {
                    d = dist;
                    minRes = new Resultado(franja.get(i), franja.get(j), d);
                }
            }
        }

        return minRes;
    }


	private static double distancia(double[] p1, double[] p2) {
		double x = p1[0] - p2[0];
		double y = p1[1] - p2[1];
		return Math.sqrt(x * x + y * y);
	}

	// main para probar funcionamiento y medir tiempos
	public static void main(String args[]) {
		Long t1, t2;
		try {
			double[][] puntos = leerFichero(args[0]);
			t1 = System.currentTimeMillis();
			Resultado resultado = puntosMasCercanos(puntos);
			t2 = System.currentTimeMillis();
			System.out.println("**TIEMPO=" + (t2 - t1));
			System.out.println("Puntos más cercanos (Divide y Vencerás):");
            System.out.printf("P1: (%.2f, %.2f)\n", puntos[resultado.p1][0], puntos[resultado.p1][1]);
            System.out.printf("P2: (%.2f, %.2f)\n", puntos[resultado.p2][0], puntos[resultado.p2][1]);
            System.out.printf("Distancia mínima: %.6f\n", resultado.distancia);
		} catch (IOException e) {
			System.out.println("Error leyendo el fichero: " + e.getMessage());
		}

	} // main

	public static double[][] leerFichero(String nombreFichero) throws IOException {

		List<double[]> lista = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(nombreFichero));

		String linea;

		linea = br.readLine();
		while ((linea = br.readLine()) != null) {
			linea = linea.trim();
			if (linea.isEmpty())
				continue;
			String[] partes = linea.split("[,\\s]+");
			if (partes.length < 2) {
				System.out.println("Línea inválida: " + linea);
				continue;
			}
			double x = Double.parseDouble(partes[0]);
			double y = Double.parseDouble(partes[1]);
			lista.add(new double[] { x, y });
		}
		br.close();
		return lista.toArray(new double[0][0]);
	}

}
