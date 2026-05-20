package mayo20;

// CAMINOS SIMPLES ENTRE DOS NODOS DIFERENTES DE UN GRAFO

// SE CALCULAN TODOS LOS CAMINOS POSIBLES PARA IR CALCULANDO EL MEJOR (MENOR COSTE)
// SE HACE PODA AL SER PESOS POSITIVOS Y ESTAR CALCULANDO UN COSTE MINIMO
// LA COMPLEJIDAD TEMPORAL por backtracking es factorial (intratable)

import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Examen 
{
	static int n;
	static String[] v; // vector de n nodos
	static int[][] w; // matriz de pesos de aristas
	static int origen;
	static int destino;
	static int filas;
	static int cols;
	static boolean[] marca;// para no repetir nodos (caminos simples)
	static int[] camino; // trayectoria de camino que va calculando
	static int coste; // coste acumulado del camino que va calculando
	static int longitud; // nivel o longitud de camino que va calculando
	static int nsol; // el numero total de caminos hallados hasta destino
	static int objetivo;

	static int[] caminoMejor; // para anotar el camino mejor
	static int costeMejor; // coste del camino mejor
	static int longMejor; // numero aristas del camino mejor

	public static void main(String arg[]) 
	{
		loadData(arg[0]);
		
		v = new String[n];
		darValorNodos();

		origen = Integer.parseInt(arg[1]);
		destino = Integer.parseInt(arg[2]);
		objetivo = Integer.parseInt(arg[3]);
		
		System.out.println("El NODO ORIGEN ES " + v[origen]);
		System.out.println("El NODO DESTINO ES " + v[destino]);

		marca = new boolean[n];
		for (int j = 0; j < n; j++)
			marca[j] = false;
		marca[origen] = true;

		camino = new int[n];
		caminoMejor = new int[n];

		camino[0] = origen;
		longitud = 0;
		nsol = 0;
		coste = 0;
		costeMejor = 10000000; // mayor que cualquier posible
		
		backtracking(origen);

	} // fin de método main


	static void darValorNodos()
		// un ejemplo de valores genericos a los nodos
	{
		for (int i = 0; i < n; i++)
			v[i] = "NODO" + i;
		System.out.println("LOS NODOS SE LLAMAN ASI:");
		for (int i = 0; i < n; i++)
			System.out.println(v[i]);
	}

	static void backtracking(int actual) // es recursivo
	{
		if (actual == destino) // es estado solución
		{
			if (coste == objetivo) 
			{
				for (int l = 0; l <= longitud; l++)
					caminoMejor[l] = camino[l];
				costeMejor = coste;
				longMejor = longitud;
				nsol++;
				mostrarSolucion();
				return;				
			}
		}

		else
			for (int j = 0; j < n; j++)
				if (!marca[j] && w[actual][j] != -1) 
					{
						longitud++;
						coste = coste + w[actual][j];
						marca[j] = true;
						camino[longitud] = j;
						backtracking(j);
						longitud--;
						coste = coste - w[actual][j];
						marca[j] = false;
					}
	}  // de backtracking

	static void mostrarSolucion() {
        System.out.println("---- CAMINO ----");
        for (int i = 0; i < caminoMejor.length; i++) {
			System.out.print(caminoMejor[i]);
            System.out.println();
        }
    }

	static void loadData(String nomFich) {
        try (BufferedReader br = new BufferedReader(new FileReader(nomFich))) {
            n = Integer.parseInt(br.readLine().trim());
            w = new int[n][n];
            for (int i = 0; i < n; i++) {
                String[] linea = br.readLine().split(",");
                for (int j = 0; j < n; j++) {
                    w[i][j] = Integer.parseInt(linea[j].trim());
                }
            }
        } catch (Exception e) {
            System.err.println("Error al leer el fichero: " + e.getMessage());
        }
    }

} // de clase
