from time import time

def primoA1(m):
    """ Devuelve si m es primo o no, mediante un algoritmo
    sencillo, que etiquetamos como A1"""
    contDiv=0
    for i in range (1,m+1):
        if m%i==0:
            contDiv=contDiv+1
    return contDiv==2

def listadoPrimos(n):
    """ calcula y escribe todos los primos hasta n"""
    lSal=[]
    contPrimos=0
    for i in range (2,n+1):
        if primoA1(i):
            lSal.append(i)
            contPrimos=contPrimos+1
    print("Hasta ",n,"  hay",contPrimos,"primos"  )
     #  print(lPrimos)
    return lSal


def main():
    print("TIEMPOS DEL ALGORITMO A1")
    n=5000
    for casos in range(8):
        t1=time()  # da el tiempo en seg. y con decimales(real)
        lPrimos=listadoPrimos(n)
        t2=time()
        print("n=",n,"***","tiempo =",int(1000*(t2-t1)), "milisegundos)")
        n=n*2


main()

# Complejidad O(n^2)