package p2;

/* Esta clase mide tiempos del metodo Burbuja
para los 3 supuestos de orden inicial (ordenado, inverso y aleatorio)
en el comando de ejecucion hay que poner la opcion elegida y
despues hay que poner el numero de repeticiones deseado */

public class BurbujaTiempos {

	static int[] v;

	public static void main(String arg[]) {
		long t1, t2;
		String opcion = arg[0];
		int repeticiones = Integer.parseInt(arg[1]);

		for (int n = 10000; n < 100000000; n *= 2) {
			v = new int[n];
			long t = 0;

			for (int i = 1; i <= repeticiones; i++) {
				if (opcion.compareTo("ordenado") == 0)
					Vector.ordenDirecto(v);
				else if (opcion.compareTo("inverso") == 0)
					Vector.ordenInverso(v);
				else
					Vector.ordenAleatorio(v);

				t1 = System.currentTimeMillis();
				Burbuja.burbuja(v);
				t2 = System.currentTimeMillis();
				t = t + (t2 - t1);
			}

			System.out.println("n=" + n + "\tTiempo=" + t + "  \tRepeticiones=" + repeticiones);
		}
	}
}
