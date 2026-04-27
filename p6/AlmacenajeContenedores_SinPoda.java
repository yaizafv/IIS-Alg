package p6;

import java.io.*;
import java.util.*;

public class AlmacenajeContenedores {

    static int capacidad;
    static int[] objetos;
    static int n;
    static int[] cargaContenedor;
    static int[] asignacion;
    static int mejorNumContenedores;
    static int[] mejorAsignacion;
    static long llamadasRecursivas = 0;

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("fnf");
            return;
        }
        leerEntrada(args[0]);

        // Ordenar de mayor a menor
        Integer[] array = new Integer[n];
        for (int i = 0; i < n; i++) array[i] = i;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (objetos[array[j]] > objetos[array[i]]) {
                    int temp = array[i]; array[i] = array[j]; array[j] = temp;
                }
            }
        }
        int[] objetosOrdenados = new int[n];
        for (int i = 0; i < n; i++) objetosOrdenados[i] = objetos[array[i]];
        objetos = objetosOrdenados;

        mejorAsignacion = new int[n];
        mejorNumContenedores = obtenerMejor(mejorAsignacion);

        cargaContenedor = new int[n];
        asignacion = new int[n];
        Arrays.fill(asignacion, -1);

        backtracking(0, 0);

        System.out.println("Llamadas recursivas: " + llamadasRecursivas);
        System.out.println("Lista de contenedores y objetos contenidos:");
        List<List<Integer>> contenedores = new ArrayList<>();
        for (int i = 0; i < mejorNumContenedores; i++) contenedores.add(new ArrayList<>());
        for (int i = 0; i < n; i++) contenedores.get(mejorAsignacion[i]).add(objetos[i]);
        for (int i = 0; i < mejorNumContenedores; i++) {
            Collections.reverse(contenedores.get(i));       //para que tenga la misma salida
            System.out.print("Contenedor " + (i + 1) + ": ");
            for (int tam : contenedores.get(i)) System.out.print(tam + " ");
            System.out.println();
        }
        System.out.println("El número de contenedores necesario es " + mejorNumContenedores + ".");
    }

    static int obtenerMejor(int[] asignacion) {
        int[] cargas = new int[n];
        int numContenedores = 0;
        for (int i = 0; i < n; i++) {
            boolean colocado = false;
            for (int j = 0; j < numContenedores; j++) {
                if (cargas[j] + objetos[i] <= capacidad) {
                    cargas[j] += objetos[i];
                    asignacion[i] = j;
                    colocado = true;
                    break;
                }
            }
            if (!colocado) {
                asignacion[i] = numContenedores;
                cargas[numContenedores] = objetos[i];
                numContenedores++;
            }
        }
        return numContenedores;
    }

    /*
     * BACKTRACKING SIN PODA:
     * Complejidad O(n!)
     * Se elimina completamente la llamada a cotaInferior().
     * El algoritmo explora TODAS las posibles asignaciones de objetos
     * a contenedores sin descartar ninguna rama, actualizando la mejor
     * solución cada vez que llega a una hoja del árbol de búsqueda.
     *
     * Consecuencia: la complejidad es exponencial "pura", sin reducción
     * por poda. Para instancias grandes tardará mucho más que la versión
     * con poda, pero encuentra igualmente la solución óptima.
     */
    static void backtracking(int objetoActual, int contenedoresUsados) {
        llamadasRecursivas++;
        if (objetoActual == n) {
            if (contenedoresUsados < mejorNumContenedores) {
                mejorNumContenedores = contenedoresUsados;
                mejorAsignacion = Arrays.copyOf(asignacion, n);
            }
            return;
        }

        // SIN PODA: se ha eliminado la condición:
        // if (cotaInferior(objetoActual, contenedoresUsados) >= mejorNumContenedores) return;

        int limite = Math.min(contenedoresUsados + 1, mejorNumContenedores - 1);
        boolean nuevoUsado = false;

        for (int j = 0; j < limite; j++) {
            if (cargaContenedor[j] == 0) {
                if (nuevoUsado) continue;
                nuevoUsado = true;
            }
            if (cargaContenedor[j] + objetos[objetoActual] <= capacidad) {
                cargaContenedor[j] += objetos[objetoActual];
                asignacion[objetoActual] = j;
                int nuevosContenedores = (j == contenedoresUsados)
                        ? contenedoresUsados + 1
                        : contenedoresUsados;
                backtracking(objetoActual + 1, nuevosContenedores);
                cargaContenedor[j] -= objetos[objetoActual];
                asignacion[objetoActual] = -1;
            }
        }
    }

    static void leerEntrada(String fichero) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fichero));
        capacidad = Integer.parseInt(br.readLine().trim());
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        List<Integer> lista = new ArrayList<>();
        while (st.hasMoreTokens()) lista.add(Integer.valueOf(st.nextToken()));
        n = lista.size();
        objetos = new int[n];
        for (int i = 0; i < n; i++) objetos[i] = lista.get(i);
        br.close();
    }
}
