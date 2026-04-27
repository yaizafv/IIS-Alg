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
        Integer[] array = new Integer[n];
        for (int i = 0; i < n; i++)
            array[i] = i;

        // ordena de mayor a menor
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (objetos[array[j]] > objetos[array[i]]) {

                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
        
        int[] objetosOrdenados = new int[n];
        for (int i = 0; i < n; i++)
            objetosOrdenados[i] = objetos[array[i]];
        objetos = objetosOrdenados;

        mejorAsignacion = new int[n];
        mejorNumContenedores = obtenerMejor(mejorAsignacion);

        cargaContenedor = new int[n];
        asignacion = new int[n];
        for (int i = 0; i < asignacion.length; i++) {
            asignacion[i] = -1;
        }
        backtracking(0, 0);

        System.out.println("Lista de contenedores y objetos contenidos:");
        List<List<Integer>> contenedores = new ArrayList<>();
        for (int i = 0; i < mejorNumContenedores; i++) {
            contenedores.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            contenedores.get(mejorAsignacion[i]).add(objetos[i]);
        }
        for (int i = 0; i < mejorNumContenedores; i++) {
            System.out.print("Contenedor " + (i + 1) + ": ");
            for (int tam : contenedores.get(i))
                System.out.print(tam + " ");
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

    static int cotaInferior(int objetoActual, int contenedoresUsados) {
        int sumaRestante = 0;
        for (int i = objetoActual; i < n; i++) {
            sumaRestante += objetos[i];
        }
        int libreActual = 0;
        for (int j = 0; j < contenedoresUsados; j++) {
            libreActual += (capacidad - cargaContenedor[j]);
        }
        int noQuedan = Math.max(0, sumaRestante - libreActual);
        return contenedoresUsados + (noQuedan + capacidad - 1) / capacidad; // redondea
    }

    static void backtracking(int objetoActual, int contenedoresUsados) {
        llamadasRecursivas++;
        if (objetoActual == n) {
            if (contenedoresUsados < mejorNumContenedores) {
                mejorNumContenedores = contenedoresUsados;
                mejorAsignacion = Arrays.copyOf(asignacion, n);
            }
            return;
        }
        if (cotaInferior(objetoActual, contenedoresUsados) >= mejorNumContenedores)
            return;
        int limite = Math.min(contenedoresUsados + 1, mejorNumContenedores - 1);

        boolean nuevoUsado = false;

        for (int j = 0; j < limite; j++) {
            if (cargaContenedor[j] == 0) {
                if (nuevoUsado)
                    continue;
                nuevoUsado = true;
            }

            if (cargaContenedor[j] + objetos[objetoActual] <= capacidad) {
                cargaContenedor[j] += objetos[objetoActual];
                asignacion[objetoActual] = j;
                int nuevosContenedores;
                if (j == contenedoresUsados) {
                    nuevosContenedores = contenedoresUsados + 1;
                } else {
                    nuevosContenedores = contenedoresUsados;
                }
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
        while (st.hasMoreTokens())
            lista.add(Integer.valueOf(st.nextToken()));
        n = lista.size();
        objetos = new int[n];
        for (int i = 0; i < n; i++) {
            objetos[i] = lista.get(i);
        }
        br.close();
    }
}
