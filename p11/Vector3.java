package p11;

/** Esta clase utiliza los metodos estaticos de la clase Vector1.
 *  Sirve para medir tiempos con distintos n del problema,
 *  incrementandolos automaticamente
 */
public class Vector3
{
static int []v;

public static void main (String arg [] )
{
	long t1,t2;

	System.out.println ("n   tTiempo");   
	for ( int n=10000; n<= 81920000; n*=2) // n se va incrementando (*2)
	{
		System.out.print (n+"\t");
		v = new int [n] ;
		Vector1.rellena (v);

		// Medida del tiempo de la operacion suma()
		t1=System.currentTimeMillis();
		int s=Vector1.suma (v);
		t2=System.currentTimeMillis();
		System.out.println ((t2-t1)+"\t");   

        } // fin de for de n del problema
		
	System.out.println("\nFin de la medicion de tiempos *****");

} // fin de main

} // fin de clase
