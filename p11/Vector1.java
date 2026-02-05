/*
	JAVA es sensible a minusculas y mayusculas (minuscula != mayuscula)
	Es norma que una clase comience por letra mayuscula.
	Los metodos y todo tipo de variables comienzan por minuscula.
*/

package p11;

import java.util.Random ; //es la clase que genera numeros aleatorios

/* Esta clase permite trabajar con vectores 
*/

public class Vector1
{
static int []v;
static int []w;

public static void main (String arg [] )
{
	int n= Integer.parseInt(arg[0]);  // n leido de linea de comandos
	v = new int [n] ;

	rellena (v);
	escribe (v);

	int s=suma (v);
	System.out.println ("Suma de los elementos del vector = "+ s);

	int [] m = new int [2];
	maximo (v,m);
	System.out.println ("Valor del maximo del vector = "+ m[1]);
	System.out.println ("Posicion del maximo del vector = "+ m[0]);

	w = new int [n] ;

	rellena (w);
	escribe (w);

	int cont1=coincidencias1 (v,w);
	System.out.println ("Numero coincidencias por Algoritmo cuadratico= "+cont1);

	int cont2=coincidencias2 (v,w);
	System.out.println ("Numero coincidencias por Algoritmo lineal= "+cont2);

	} // fin de main


/* Este metodo da valores aleatorios a un vector de enteros, 
    utiliza para ello la clase Random del paquete java.util  
*/
public static void rellena (int[]a)
{
	Random r= new Random ();
	int n= a.length;
	for(int i=0;i<n;i++)
		a[i]=r.nextInt (199)-99;//valores entre -99 y 99

}  // fin de rellena   


/* Escribe el contenido del vector que se le pasa  
*/
public static void escribe (int[]a)
{
	int n= a.length;
	for (int i=0; i<n; i++ )
		System.out.println ("Elemento "+i+" = "+a[i]);
	System.out.println();

}


/* Este metodo suma los elementos de un vector y devuelve el resultado
*/
public static int suma (int[]a)
{
	int s=0;
	int n= a.length;
	for (int i=0;i<n;i++) s=s+a[i];
	return s;

}  // fin de suma


/* Este metodo calcula el maximo y su posicion y devuelve los dos valores.
*/
public static void maximo (int[]a, int[]m)
{
	int n= a.length;
	m[0]=0; 	// posición inicial
	m[1]= a[0];	// primer elemento como referencia
	for (int i=1;i<n;i++)
		if (a[i]>m[1]) 
		{
			m[0]=i;
			m[1]=a[i];
		}
	}  // fin de maximo


/* Este metodo calcula el numero de coincidencias de los vectores
    de entrada (son de la misma dimension).
    Definimos una coincidencia como el hecho que sobre una misma posicion
    haya el mismo valor en ambos vectores.
    En este caso se resuelve "con la feliz idea" de un bucle doblemente anidado 
*/
public static int coincidencias1 (int[]a,int[]b)
{
	int c=0;
	int n= a.length;
	for (int i=0;i<n;i++)
	    for (int j=0;j<n;j++)
               if (i==j && a[i]==b[j])
             c++;
        return c;
}  // fin de coincidencias1


/* Este metodo calcula (también como el anterior) el numero de coincidencias
    de los vectores de entrada (son de la misma dimension).
    Definimos una coincidencia como el hecho que sobre una misma posicion
    haya el mismos valor en ambos vectores.
    En este caso lo resolvemos con un solo bucle 
*/
public static int coincidencias2 (int[]a,int[]b)
{
	int c=0;
	int n= a.length;
	for (int i=0;i<n;i++) 
          if (a[i]==b[i])
             c++;
        return c;
}  // fin de coincidencias2

}  // fin de clase
