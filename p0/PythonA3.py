from time import time

def primoA3(m):
    """ Devuelve si m es primo o no
    mediante un tercer algoritmo A3"""
    for i in range (2,m//2+1):
        if m%i==0:
            return False
    return True

def listadoPrimos(n):
    """ calcula y devuelve todos los primos hasta n"""
    lSal=[2]
    contPrimos=1
    for i in range (3,n+1,2):
        if primoA3(i):
            lSal.append(i)
            contPrimos=contPrimos+1
    print("Hasta ",n,"  hay",contPrimos,"primos"  )
    #print(lPrimos)



def main():
    print("TIEMPOS DEL ALGORITMO A3")
    n=5000
    for casos in range(8):
        t1=time()  # da el tiempo en seg. y con decimales(real)
        lPrimos=listadoPrimos(n)
        t2=time()
        print("n=",n,"***","tiempo =",int(1000*(t2-t1)), "milisegundos)")
      #  print(lPrimos)
        n=n*2

main()


