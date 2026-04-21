

public class Sustraccion2 {

	static long cont;

	public static void rec2(int n) {
		if (n <= 0)
			cont++;
		else {
			for (int i = 0; i < n; i++)
				cont++; // O(n)
			rec2(n - 1);
			for (int i = 0; i < n; i++)
				cont++; // O(n)
		}
	}

	public static void main(String arg[]) {
		long t1, t2, cont;
		int nVeces = Integer.parseInt(arg[0]);
		for (int n = 1000; n <= 1024000; n *= 2) {

			t1 = System.currentTimeMillis();

			for (int repeticiones = 1; repeticiones <= nVeces; repeticiones++) {
				cont = 0;
				rec2(n);
			}

			t2 = System.currentTimeMillis();

			System.out.println(" n=" + n + "**TIEMPO=" + (t2 - t1) + "**nVeces=" + nVeces);
		} // for
	} // main
} // class