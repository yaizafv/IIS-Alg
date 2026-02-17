package p12;

public class Bucle3 {
	public static long bucle3(long n) {
		long cont = 0;
		long n1 = n;
		long i = 1;
		while (i <= 2 * n) {
			for (long j = i; j >= 0; j -= 2)
				for (long k = 1; k <= n; k *= 2)
					cont++;
			i++;
		}
		return cont;
		// Complejidad: O(n^2log(n)) casi cuadratica
	}

	public static void main(String arg[]) {
		long t1, t2;
		int nVeces = Integer.parseInt(arg[0]);

		System.out.println("n\ttiempo\trepeticiones\tcontador");

		for (long n = 100; n <= 819200; n *= 2) {
			long c = 0;
			t1 = System.currentTimeMillis();

			for (int repeticiones = 1; repeticiones <= nVeces; repeticiones++)
				c = bucle3(n);

			t2 = System.currentTimeMillis();
			System.out.println(n + "\t" + (t2 - t1) + "\t" + nVeces + "\t\t" + c);

		} // for
	} // main
} // class