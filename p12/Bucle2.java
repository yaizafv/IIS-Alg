package p12;

public class Bucle2 {
	public static long bucle2(long n) {
		long cont = 0;
		long n1 = n;
		do {
			for (long i = 1; i <= n; i++)
				for (long j = n; j >= 0; j -= 2)
					cont++;
			n1 = n1 / 3;
		} while (n1 >= 1);
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
				c = bucle2(n);

			t2 = System.currentTimeMillis();

			System.out.println(n + "\t" + (t2 - t1) + "\t" + nVeces + "\t\t" + c);

		} // for
	} // main
} // class