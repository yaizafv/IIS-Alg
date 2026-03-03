package p3;

/* Resolverenos Fibonacci mediante 4 algorirmos diferentes y 
   tomaremos tiempos para un n ejemplo

 Fibonacci series: 0 1 1 2 3 5 8 13 21 34 55 89 ...*/

public class Fibonacci {

	// main para probar funcionamiento y medir tiempos
	public static void main(String[] arg) {
		int n = 40;

		System.out.println("METODO1: para n= " + n + " es= " + fib1(n));
		int[] v = new int[50];
		System.out.println("METODO2: para n= " + n + " es= " + fib2(n, v));
		System.out.println("METODO3: para n= " + n + " es= " + fib3(n));
		System.out.println("METODO4: para n= " + n + " es= " + fib4(n));

		long t1, t2;
		int x;

		t1 = System.currentTimeMillis();
		for (int nVeces = 0; nVeces < 1000000; nVeces++)
			x = fib1(n);
		t2 = System.currentTimeMillis();
		System.out.println("METODO1 >>> n= " + n + "**" + "TIEMPO= " + (t2 - t1) + " NANOSEGUNDOS");

		t1 = System.currentTimeMillis();
		for (int nVeces = 0; nVeces < 1000000; nVeces++)
			x = fib2(n, v);
		t2 = System.currentTimeMillis();
		System.out.println("METODO2 >>> n= " + n + "**" + "TIEMPO= " + (t2 - t1) + " NANOSEGUNDOS");

		t1 = System.currentTimeMillis();
		for (int nVeces = 0; nVeces < 1000000; nVeces++)
			x = fib3(n);
		t2 = System.currentTimeMillis();
		System.out.println("METODO3 >>> n= " + n + "**" + "TIEMPO= " + (t2 - t1) + " NANOSEGUNDOS");

		t1 = System.currentTimeMillis();
		x = fib4(n);
		t2 = System.currentTimeMillis();
		System.out.println("METODO4 >>> n= " + n + "**" + "TIEMPO= " + (t2 - t1) + " MILISEGUNDOS");
	} // main

	/* iterativa O(n) */
	public static int fib1(int n) {
		int n1 = 0;
		int n2 = 1;
		for (int i = 1; i <= n; i++) {
			int s = n1 + n2;
			n1 = n2;
			n2 = s;
		}
		return n1;
	}

	/* iterativa O(n) */
	public static int fib2(int n, int[] v) {
		v[0] = 0;
		v[1] = 1;
		for (int i = 2; i <= n; i++)
			v[i] = v[i - 1] + v[i - 2];
		return v[n];
	}

	/* recursiva Sustraccion (a=1;b=1;k=0)=>O(n) */
	public static int fib3(int n) {
		return aux(0, 1, n);
	}

	private static int aux(int n1, int n2, int n) {
		if (n < 1)
			return n1;
		return aux(n2, n1 + n2, n - 1);
	}

	/*
	 * recursiva Sustraccion (a=2;b=1 o b=2;k=0)=>O(1.6**n)
	 * es intratable por exponencial
	 */
	public static int fib4(int n) {
		if (n <= 1)
			return n;
		return fib4(n - 1) + fib4(n - 2);
	}

}
