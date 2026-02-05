package p0;

import java.util.ArrayList;

public class JavaA1 {

   public static boolean primoA1(int m) {
      int contDiv = 0;
      for (int i = 1; i <= m; i++)
         if (m % i == 0)
            contDiv = contDiv + 1;
      return contDiv == 2;
   }

   public static void listadoPrimos(int n) {
      ArrayList<Integer> lista = new ArrayList<Integer>();
      int contPrimos = 0;

      for (int i = 2; i <= n; i++)
         if (primoA1(i)) {
            lista.add(i);
            contPrimos++;
         }

      System.out.println("Hay " + contPrimos + " primos hasta " + n);
      // System.out.println(lista);

   }

   public static void main(String arg[]) {

      System.out.println("TIEMPO EN JAVA DEL ALGORITMO A1");

      long t1, t2; // obligatoriamente de tipo long para no desbordar
      // la toma de tiempos en Java se ver� con m�s profundidad en la sesi�n siguiente

      for (int n = 5000; n <= 1000000; n *= 2) {
         t1 = System.currentTimeMillis(); // milisegundos (sin decimales)

         listadoPrimos(n);

         t2 = System.currentTimeMillis();

         System.out.println(t1 + "///" + t2);

         System.out.println("n=" + n + "**** tiempo = " + (t2 - t1) + " milisegundos \n");

      }
   } // de main

} // de clase
