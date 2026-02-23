package p2;

/* Este programa sirve para ordenar n elementos con un algoritmo cuadratico
( SELECCION ) */

public class Seleccion {
	static int[] v;

	/* Ordenacion por el metodo de Seleccion */
	public static void seleccion(int[] a) {
		int n = a.length;
		int x;
		int posmin;
		for (int i = 0; i < n - 1; i++) { // Buscar la posicion del mas pequenno de los que quedan
			posmin = i;
			for (int j = i + 1; j < n; j++)
				if (a[j] < a[posmin])
					posmin = j;
			// Intercambia el que toca con el mas peque�o
			x = a[posmin];
			a[posmin] = a[i];
			a[i] = x;

		} // for
	}

	public static void main(String arg[]) {
		int n = Integer.parseInt(arg[0]); // tamanno del problema
		v = new int[n];

		Vector.ordenDirecto(v);
		System.out.println("VECTOR A ORDENAR ES");
		Vector.escribe(v);
		seleccion(v);
		System.out.println("VECTOR ORDENADO ES");
		Vector.escribe(v);

		Vector.ordenInverso(v);
		System.out.println("VECTOR A ORDENAR ES");
		Vector.escribe(v);
		seleccion(v);
		System.out.println("VECTOR ORDENADO ES");
		Vector.escribe(v);

		Vector.ordenAleatorio(v);
		System.out.println("VECTOR A ORDENAR ES");
		Vector.escribe(v);
		seleccion(v);
		System.out.println("VECTOR ORDENADO ES");
		Vector.escribe(v);
	} // fin de main

}
