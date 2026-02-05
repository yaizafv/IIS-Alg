from time import time

def primosA4(n):
    """ criba de Eratóstenes (siglo III a. C.)

    calcula y devuelve todos los primos hasta n"""

    listaNumeros=[]
    for i in range (n+1):
        listaNumeros.append(True)

    x=2
    while x**2<=n:
        if listaNumeros[x]:
            paso=2*x
            while paso<=n:
                listaNumeros[paso]=False
                paso=paso+x
        x=x+1

    lSal=[]
    contPrimos=0
    for i in range(2, n+1):
        if listaNumeros[i]:
            lSal.append(i)
            contPrimos=contPrimos+1
    print("Hasta ",n,"  hay",contPrimos,"primos"  )
    #print(lSal)



##OTRA FORMA DE HACERLO

##def primosA4(n):

##        """ criba de Eratóstenes (siglo III a. C.)
##
##    calcula y devuelve todos los primos hasta n"""

##    lSal=[]
##    contPrimos=0
##    multiplos = set()
##    for i in range(2, n+1):
##      if i not in multiplos:
##        lSal.append(i)
##        contPrimos=contPrimos+1
##        multiplos.update(range(i*i, n+1, i))
##    print("Hasta ",n,"  hay",contPrimos,"primos"  )
##    #print(lSal)




def main():
    print("TIEMPOS DEL ALGORITMO A4")
    n=5000
    for casos in range(15):
        t1=time()  # da el tiempo en seg. y con decimales(real)
        lPrimos=primosA4(n)
        t2=time()
        print("n=",n,"***","tiempo =",int(1000*(t2-t1)), "milisegundos)")
    #   print(lPrimos)
        n=n*2

    """ calcula y devuelve todos los primos hasta n"""
    lSal=[2]
main()

