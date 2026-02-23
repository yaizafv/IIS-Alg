package p2;

/* Este programa sirve para ordenar n elementos con un algoritmo cuadratico
excepto si el vector inicialmente esta ordenado o casi ordenado,
que es de complejidad lineal  (INSERCION ) */

public class Insercion {
	static int[] v;

	/* Ordenacion por el metodo de Insercion */
	public static void insercion(int[] a) {
		int n = a.length;
		for (int i = 1; i <= n - 1; i++) {
			int x = a[i];
			int j = i - 1;
			while (j >= 0 && x < a[j]) {
				a[j + 1] = a[j];
				j = j - 1;
			}
			a[j + 1] = x;
		} // for
	}

	public static void main(String arg[]) {
		int n = Integer.parseInt(arg[0]); // tamanno del problema
		v = new int[n];

		Vector.ordenDirecto(v);
		System.out.println("VECTOR A ORDENAR ES");
		Vector.escribe(v);
		insercion(v);
		System.out.println("VECTOR ORDENADO ES");
		Vector.escribe(v);

		Vector.ordenInverso(v);
		System.out.println("VECTOR A ORDENAR ES");
		Vector.escribe(v);
		insercion(v);
		System.out.println("VECTOR ORDENADO ES");
		Vector.escribe(v);

		Vector.ordenAleatorio(v);
		System.out.println("VECTOR A ORDENAR ES");
		Vector.escribe(v);
		insercion(v);
		System.out.println("VECTOR ORDENADO ES");
		Vector.escribe(v);
	} // fin de main

}
