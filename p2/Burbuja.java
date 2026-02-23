package p2;

/* Este programa sirve para ordenar n elementos con un algoritmo cuadratico
( BURBUJA O INTERCAMBIO DIRECTO */

public class Burbuja {
	static int[] v;

	/* Ordenacion por el metodo de Burbuja */
	public static void burbuja(int[] a) {
		int n = a.length;
		int x;
		for (int i = 0; i <= n - 2; i++)
			for (int j = n - 1; j > i; j--)
				if (a[j - 1] > a[j]) {
					// intercambio de valores
					x = a[j - 1];
					a[j - 1] = a[j];
					a[j] = x;
				}
	}

	public static void main(String arg[]) {
		int n = Integer.parseInt(arg[0]); // tamanno del problema
		v = new int[n];

		Vector.ordenDirecto(v);
		System.out.println("VECTOR A ORDENAR ES");
		Vector.escribe(v);
		burbuja(v);
		System.out.println("VECTOR ORDENADO ES");
		Vector.escribe(v);

		Vector.ordenInverso(v);
		System.out.println("VECTOR A ORDENAR ES");
		Vector.escribe(v);
		burbuja(v);
		System.out.println("VECTOR ORDENADO ES");
		Vector.escribe(v);

		Vector.ordenAleatorio(v);
		System.out.println("VECTOR A ORDENAR ES");
		Vector.escribe(v);
		burbuja(v);
		System.out.println("VECTOR ORDENADO ES");
		Vector.escribe(v);
	} // fin de main

}
