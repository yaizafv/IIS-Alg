package p6;

import java.io.*;
import java.util.*;

public class AlmacenajeContenedoresTiempos {

    static int capacidad;
    static int[] objetos;
    static int n;
    static int[] cargaContenedor;
    static int[] asignacion;
    static int mejorNumContenedores;
    static int[] mejorAsignacion;
    static long llamadasRecursivas;

    static final long TIMEOUT_MS = 60000; // 60 segundos
    static long tiempoInicio;
    static boolean tiempoAgotado;

    public static void main(String[] args) throws IOException {
        String[] ficheros = {
                "test00.txt", "test01.txt", "test02.txt", "test03.txt", "test04.txt",
                "test05.txt", "test06.txt", "test07.txt", "test08.txt", "test09.txt"
        };

        System.out.printf("%-20s %15s %15s%n", "Fichero", "Contenedores", "Tiempo(ms)");
        System.out.println("-".repeat(55));

        for (String fichero : ficheros) {
            File f = new File(fichero);
            if (!f.exists()) {
                f = new File("test/" + fichero);
                if (!f.exists()) {
                    System.out.printf("%-20s  No encontrado%n", fichero);
                    continue;
                }
            }

            leerEntrada(f.getPath());

            tiempoInicio = System.currentTimeMillis();
            tiempoAgotado = false;
            llamadasRecursivas = 0;

            Integer[] array = new Integer[n];
            for (int i = 0; i < n; i++)
                array[i] = i;
            Arrays.sort(array, (a, b) -> objetos[b] - objetos[a]);
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

            long tiempo = System.currentTimeMillis() - tiempoInicio;

            if (tiempoAgotado) {
                System.out.printf("%-20s %15d %15s%n", fichero, mejorNumContenedores,
                        tiempo + " (TIMEOUT)");
            } else {
                System.out.printf("%-20s %15d %15d%n", fichero, mejorNumContenedores, tiempo);
            }
        }
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
        for (int i = objetoActual; i < n; i++)
            sumaRestante += objetos[i];
        int libreActual = 0;
        for (int j = 0; j < contenedoresUsados; j++)
            libreActual += (capacidad - cargaContenedor[j]);
        int noQuedan = Math.max(0, sumaRestante - libreActual);
        return contenedoresUsados + (noQuedan + capacidad - 1) / capacidad;
    }

    static void backtracking(int objetoActual, int contenedoresUsados) {
        if (tiempoAgotado)
            return;
        if (System.currentTimeMillis() - tiempoInicio > TIMEOUT_MS) {
            tiempoAgotado = true;
            return;
        }
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
                int nuevosContenedores = (j == contenedoresUsados) ? contenedoresUsados + 1 : contenedoresUsados;
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
        for (int i = 0; i < n; i++)
            objetos[i] = lista.get(i);
        br.close();
    }
}