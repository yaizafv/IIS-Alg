package p12;

import java.util.Arrays;

public class Bucle1 {

	public static long bucle1(long n) {
		long cont = 0;
		long n1 = 1;
		while (n1 <= n * n) {
			for (long i = 1; i <= 2 * n; i += 3)
				cont++;
			n1 = 3 * n1;
		}
		return cont;
		// Complejidad: O(nlog(n)) (casi lineal)
	}

	public static void main(String arg[]) {
		/*
		 * long t1, t2;
		 * int nVeces = Integer.parseInt(arg[0]);
		 * 
		 * System.out.println("n\ttiempo\trepeticiones\tcontador");
		 * 
		 * for (long n = 100; n <= 819200; n *= 2) {
		 * long c = 0;
		 * t1 = System.currentTimeMillis();
		 * 
		 * for (int repeticiones = 1; repeticiones <= nVeces; repeticiones++)
		 * c = bucle1(n);
		 * 
		 * t2 = System.currentTimeMillis();
		 * 
		 * System.out.println(n + "\t" + (t2 - t1) + "\t" + nVeces + "\t\t" + c);
		 * } // for
		 */

		backtracking(0);
	} // main

	public static void calculo() {
		int[] pesos = { 10, 20, 30, 40, 50 };
		int[] valores = { 20, 30, 66, 40, 60 };
		int pesoMax = 100;
		int[] ratio = { 2, 1, 2, 1, 1 }; // Ignorando el double

		int sumaPesos = 0;
		int valorTotal = 0;
		int[] resultado = new int[pesos.length];

		for (int i = 0; i < pesos.length; i++) {
			// 1. Obtenemos el valor máximo actual
			int max = obtenerMax(ratio);
			if (max <= 0)
				break; // Si ya no hay más ratios, paramos (en este caso no haria falta por el tamaño
						// de los arrays)

			// 2. Buscamos dónde está ese valor
			int indexOfMax = obtenerIndiceMayor(max, ratio);

			// 3. Comprobamos si cabe ANTES de sumar
			if (sumaPesos + pesos[indexOfMax] <= pesoMax) {
				sumaPesos += pesos[indexOfMax];
				resultado[i] = valores[indexOfMax];
				valorTotal += valores[indexOfMax];
				System.out.println("Metido objeto de peso: " + pesos[indexOfMax] + " y valor: " + valores[indexOfMax]);
			}

			// 4. ¡BORRAMOS AQUÍ! Después de haber usado el índice
			ratio[indexOfMax] = 0;
		}

		System.out.println("Valor total almacenado: " + valorTotal);
	}

	public static int obtenerMax(int[] ratio) {
		int max = 0;
		for (int i = 0; i < ratio.length; i++) {
			if (ratio[i] > max) {
				max = ratio[i];
			}
		}
		return max;
	}

	public static void formaDinamica() {
		int[] pesos = { 10, 20, 30, 40, 50 };
		int[] valores = { 20, 30, 66, 40, 60 };
		int W = 100;
		int n = pesos.length;
		int[][] dp = new int[n + 1][W + 1]; // maximo valor usando los primeros i objetos con capacidad j

		for (int i = 1; i <= n; i++) {
			int w = pesos[i - 1], v = valores[i - 1];
			for (int j = 0; j <= W; j++) {
				if (w > j) { // no cabe
					dp[i][j] = dp[i - 1][j];
				} else {
					dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - w] + v); // maximizar (quedarse con el maximo)
				}
			}
		}

		System.out.println(dp[n][W]);

	}

	// Dado un conjunto de monedas con distintos valores,
	// ¿cuál es el mínimo número de monedas necesario para formar una cantidad
	// exacta S?
	public static int minMonedas(int[] monedas, int S) {
		int n = monedas.length;
		int[] dp = new int[S + 1];
		// caso base
		dp[0] = 0;
		Arrays.fill(dp, 1, S + 1, Integer.MAX_VALUE); // todo imposible al inicio
		for (int i = 0; i < n; i++) {
			for (int j = monedas[i]; j <= S; j++) {
				if (dp[j - monedas[i]] != Integer.MAX_VALUE) {
					dp[j] = Math.min(dp[j], dp[j - monedas[i]] + 1);
				}
			}
		}

		return dp[S];
	}

	private static int[] v = { 1, 2, 3, 4, 5 };
	private static int sumaDada = 6;
	private static int[] sol = new int[v.length];

	// Dado
	public static void backtracking2(int indice, int sumaAcumulada) {
		if (sumaDada == sumaAcumulada) {
			return;
		}
		if (indice == v.length || sumaAcumulada > sumaDada) {
			return;
		}

		for (int i = indice; i < v.length; i++) {
			sol[i] = v[i];
			sumaAcumulada += v[i];
			backtracking2(i + 1, sumaAcumulada);
			sol[i] = 0;
			sumaAcumulada -= v[i];
		}
	}

	private static boolean[] usado = new boolean[v.length];

	public static void permutaciones(int[] sol, boolean usado[], int posicion) {
		if (posicion == v.length) {
			return;
		}
		for (int i = 0; i < v.length; i++) { // en cada posicion hay que probar todos los elementos de v
			if (!usado[i]) {
				sol[i] = v[i];
				usado[i] = true;
				permutaciones(sol, usado, posicion + 1);
				sol[i] = 0;
				usado[i] = false;
			}
		}
	}

	static int[][] laberinto = {
			{ 0, 0, 1, 0 },
			{ 1, 0, 1, 0 },
			{ 1, 0, 0, 0 },
			{ 1, 1, 0, 0 }
	};
	int[] df = { -1, 1, 0, 0 }; // arriba, abajo, quieto, quieto
	int[] dc = { 0, 0, -1, 1 }; // quieto, quieto, izquierda, derecha

	static void resolver(int fila, int col, boolean[][] visitado) {
		if (fila == 3 && col == 3) {
			return;
		}
		for (int i = fila; i < laberinto.length; i++) {
			for (int j = col; j < laberinto[i].length; j++) {
				if (!visitado[i][j] && laberinto[i][j] != 1) {

					visitado[i][j] = true;
				}

			}
		}
	}

	private static int N = 4;
	private static int[] tablero = new int[N];

	// Colocar N reinas en un tablero N×N de forma que ninguna se ataque entre sí.
	// Una reina ataca en su misma fila, columna y diagonal.
	public static void backtracking(int col) {
		if (col == N) {
			imprimirSolucion(); // Caso base
			return;
		}
		for (int fila = 0; fila < N; fila++) {
			if (esValida(col, fila)) {
				tablero[col] = fila; // coloco la reina
				backtracking(col + 1); // avanzo a la siguiente columna
			}
		}
	}

	private static boolean esValida(int col, int fila) {
		for (int c = 0; c < col; c++) {
			int f = tablero[c];

			if (f == c)
				return false; // misma fila

			if (Math.abs(f - fila) == Math.abs(c - col))
				return false; // misma columna
		}
		return true;
	}

	// Mochila 0/1 — tienes objetos con peso y valor, mochila de capacidad W
	public static void mochilaDP() {
		int[] pesos = { 3, 6, 8, 1 };
		int[] valores = { 8, 1, 4, 2 };
		int capacidad = 15;

		int[][] dp = new int[pesos.length + 1][capacidad + 1];

		for (int i = 0; i <= pesos.length; i++) {
			dp[i][0] = 0;
		}

		for (int i = 0; i <= capacidad; i++) {
			dp[0][i] = 0;
		}

		for (int i = 1; i <= pesos.length; i++) {
			for (int j = 1; j <= capacidad; j++) {
				dp[i][j] = dp[i - 1][j];
				if (j >= pesos[i - 1]) {
					dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - pesos[i - 1]] + valores[i - 1]);
				}
			}
		}

		System.out.println(dp[pesos.length][capacidad]);

	}

	public static void scheduler(int[] actividades, int[] tiempos, int maxTareas) {
		Arrays.sort(tiempos);
		

	}

	public static boolean subsetSum2(int[] numbers, int target) {
		int n = numbers.length;
		boolean[][] dp = new boolean[n + 1][target + 1];
		for (int i = 0; i <= n; i++) {
			dp[i][0] = true;
		}

		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= target; j++) {
				dp[i][j] = dp[i - 1][j];
				if (numbers[i - 1] <= j) {
					dp[i][j] = dp[i - 1][j] || dp[i - 1][j - numbers[i - 1]];
				}
			}
		}

		return dp[n][target];

	}

	public static int inversiones() {
		int[] costes = { 3, 4, 6, 2 };
		int[] beneficios = { 1, 2, 3, 1 };
		int n = costes.length;
		int max = 5;
		int[][] dp = new int[n + 1][max + 1];
		for (int i = 0; i <= n; i++) {
			dp[i][0] = 0;
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= max; j++) {
				dp[i][j] = dp[i - 1][j]; // no cabe
				if(costes[i - 1] <= j) {
					dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - costes[i - 1]] + beneficios[i - 1]);
				}
			}
		}

		return dp[n][max];
	}

	private static void imprimirSolucion() {
		for (int col = 0; col < N; col++) {
			System.out.print("Columna " + col + " → Fila " + tablero[col] + "  |  ");
		}
		System.out.println();
	}

	public static int obtenerIndiceMayor(int max, int[] ratio) {
		for (int i = 0; i < ratio.length; i++) {
			if (ratio[i] == max) {
				return i;
			}
		}
		return -1;
	}

	public static boolean subsetSum(int[] numbers, int target) {
		boolean[][] table = new boolean[numbers.length + 1][target + 1];

		for (int i = 0; i < numbers.length; i++) {
			table[i][0] = true;
		}

		for (int i = 1; i <= numbers.length; i++) {
			for (int j = 1; j <= target; j++) {
				if (numbers[i - 1] > j) {
					table[i][j] = table[i - 1][j];
				} else {
					table[i][j] = table[i - 1][j] || table[i - 1][j - numbers[i - 1]];
				}
			}
		}

		return table[numbers.length][target];
	}

	public static int countWays(int[] numbers, int target) {

		int n = numbers.length;
		int[][] dp = new int[n + 1][target + 1];

		// Paso 4 — casos base
		for (int i = 0; i <= n; i++) {
			dp[i][0] = 1;
		}

		// Paso 5 — rellenar la tabla
		for (int i = 1; i <= n; i++) {
			for (int s = 0; s <= target; s++) {

				// Opción A — no meto el número
				dp[i][s] = dp[i - 1][s];

				// Opción B — meto el número (si cabe)
				if (numbers[i - 1] <= s) { // cabe el numero en la suma?
					dp[i][s] = dp[i][s] + dp[i - 1][s - numbers[i - 1]];
				}
			}
		}

		// Respuesta final
		return dp[n][target];
	}

	/**
	 * Tienes una fila de casas. Cada casa tiene una cantidad de dinero. No puedes
	 * robar dos casas seguidas.
	 * ¿Cuál es el máximo dinero que puedes robar?
	 * Casas: {2, 7, 9, 3, 1} (dinero de cada una)
	 * Respuesta: 12
	 * → Robas las casas 1, 3 y 5 → 2 + 9 + 1 = 12
	 */
	public static int rob(int[] houses) {

		int n = houses.length;
		int[] dp = new int[n];

		// Paso 4 — casos base
		for (int i = 0; i <= n; i++) {
			dp[i] = 1;
		}

		// Paso 5 — rellenar el array
		for (int i = 2; i < n; i++) {
			// recurrencia...
		}

		// Respuesta final
		return dp[n - 1];
	}

	/**
	 * Tienes una cuerda de longitud n. Puedes cortarla en trozos. Cada trozo de
	 * longitud i vale price[i] euros.
	 * ¿Cuál es el máximo valor que puedes obtener?
	 * n = 4
	 * prices = {0, 2, 5, 7, 8}
	 * ↑ ↑ ↑ ↑ ↑
	 * l=0 l=1 l=2 l=3 l=4
	 * Respuesta: 10
	 * → Dos trozos de longitud 2 → 5 + 5 = 10€
	 */
	public static int cutRod(int[] prices, int nu) {
		int n = prices.length;
		int[][] dp = new int[n + 1][nu + 1];

		for (int i = 0; i <= n; i++) {
			dp[i][0] = 1;
		}

		for (int i = 1; i < n; i++) {
			for (int j = 1; j < n; j++) {
				dp[i][j] = dp[i - 1][j];

			}
		}
		return dp[n + 1][n + 1];
	}
} // class