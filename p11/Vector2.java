package p11;

/** Esta clase utiliza los metodos estaticos de Vector1.
 *  Realiza la medicion de tiempos de la operacion de suma
 **/
public class Vector2
{
static int []v;

public static void main (String arg [] )
{
	int n= Integer.parseInt (arg[0]);  //n del problema leido por linea de comandos
	v = new int [n] ;
	Vector1.rellena (v);

	// Declara variables de tipo long para recoger el datos de milisegundos
	long t1,t2;

	t1=System.currentTimeMillis();	// milisegundos actuales al comienzo del algoritmo a medir
	int s=Vector1.suma (v);
	t2=System.currentTimeMillis();	// milisegundos actuales al final del algoritmo a medir

	System.out.println ("t1="+t1+"  *** t2="+t2);

	System.out.println ("n= "+n+"\t"+"Tiempo metodo suma = "+(t2-t1));

	System.out.println ("Resultado de la suma de elementos = "+ s);

} // fin de main

} // fin de clase

