# Guía Completa de Backtracking — Examen

---

## 1. ¿Qué es el Backtracking?

Backtracking es una técnica de búsqueda exhaustiva que **prueba todas las combinaciones posibles** de forma recursiva. Cuando detecta que un camino no puede llevar a una solución, **vuelve atrás** (backtrack) y prueba otra opción.

**Analogía:** estás en un laberinto. En cada cruce eliges un camino. Si llegas a un callejón sin salida, vuelves al último cruce y pruebas otra dirección.

---

## 2. Estructura Universal

Todo backtracking tiene SIEMPRE esta forma:

```java
void backtracking(int nivel, ...parámetros...) {

    if (casoBase) {
        // ¿Es solución? → guardar/mostrar/contar
        return;
    }

    for (cada opción posible) {
        if (esValido(opción)) {       // PODA
            elegir(opción);           // modificar estado
            backtracking(nivel + 1);  // explorar
            deshacer(opción);         // restaurar estado
        }
    }
}
```

### Los 3 pasos que NUNCA fallan:
1. **Elegir** → añadir al estado actual
2. **Explorar** → llamada recursiva
3. **Deshacer** → restaurar estado anterior

> ⚠️ Si no deshaces, el estado queda "contaminado" para las siguientes opciones.

---

## 3. Las 4 Preguntas Clave ante cualquier enunciado

Antes de escribir una sola línea de código, responde:

| # | Pregunta | Ejemplo (tablero) |
|---|----------|-------------------|
| 1 | ¿Cuál es el **caso base**? | Llegar a la celda (N-1, M-1) |
| 2 | ¿Cuáles son las **opciones** en cada paso? | Derecha, abajo, arriba |
| 3 | ¿Cuáles son las **podas**? | No salir del tablero, no repetir celda |
| 4 | ¿Qué **elijo y deshago**? | `path[i][j] = true / false` |

Si respondes estas 4 preguntas, el código se escribe solo.

---

## 4. Tipos de Ejercicios y Cómo Identificarlos

### Tipo 1 — Subconjunto con condición
**Palabras clave en el enunciado:** "elementos que sumen X", "subconjunto que cumpla...", "incluir o no incluir"

**Clave:** cada elemento tiene DOS opciones: incluirlo o no.

```java
// Opción 1: incluir v[nivel]
sol[nivel] = 1;
backtracking(nivel + 1, suma + v[nivel]);

// Opción 2: no incluir v[nivel]
sol[nivel] = 0;
backtracking(nivel + 1, suma);
```

**Caso base:** `nivel == n`
**Poda típica:** `suma > X` → parar

---

### Tipo 2 — Permutaciones
**Palabras clave:** "todas las ordenaciones", "todas las secuencias posibles", "sin repetir"

**Clave:** array `marca[]` para no repetir. En cada posición pruebas todos los no marcados.

```java
for (int i = 0; i < n; i++) {
    if (!marca[i]) {
        sol[nivel] = v[i];
        marca[i] = true;
        backtracking(nivel + 1);
        marca[i] = false;   // deshacer
    }
}
```

**Caso base:** `nivel == n`

---

### Tipo 3 — Combinaciones sin repetición
**Palabras clave:** "grupos de K elementos", "el orden no importa", "sin repetir combinación"

**Clave:** parámetro `inicio` que evita volver atrás en el array (así {1,2,3} y {3,2,1} no se generan ambos).

```java
for (int i = inicio; i < n; i++) {
    sol[nivel] = v[i];
    backtracking(nivel + 1, i + 1);  // i+1: nunca hacia atrás
}
```

**Caso base:** `nivel == K`
**Poda típica:** `n - inicio < K - nivel` (no quedan suficientes elementos)

---

### Tipo 4 — Camino en tablero
**Palabras clave:** "encontrar un camino", "desde (0,0) hasta...", "movimientos posibles son..."

**Clave:** array `path[][]` para no repetir celdas. Movimientos definidos en una tabla.

```java
int[][] movimientos = {{0,1},{1,0},{-1,0},{0,-1}}; // der, abajo, arriba, izq

for (int[] mov : movimientos) {
    int nextRow = row + mov[0];
    int nextCol = col + mov[1];
    if (dentroDelTablero && !path[nextRow][nextCol]) {
        path[nextRow][nextCol] = true;
        backtracking(nextRow, nextCol, suma + tablero[nextRow][nextCol]);
        path[nextRow][nextCol] = false;
    }
}
```

**Caso base:** llegar a la celda destino
**Podas típicas:** fuera del tablero, celda ya visitada, suma > objetivo

---

### Tipo 5 — Distribución / Asignación
**Palabras clave:** "asignar", "colorear", "colocar sin conflicto", "N-Reinas", "Sudoku"

**Clave:** función `esValido()` que comprueba restricciones antes de asignar.

```java
for (int opcion = 1; opcion <= K; opcion++) {
    if (esValido(nivel, opcion)) {
        asignacion[nivel] = opcion;     // elegir
        backtracking(nivel + 1);
        asignacion[nivel] = 0;          // deshacer
    }
}
```

**Caso base:** `nivel == n` (todo asignado)

---

### Tipo 6 — Generación de strings / palabras
**Palabras clave:** "generar todas las palabras", "alfabeto", "longitud L", "sin dos letras iguales consecutivas"

**Clave:** igual que permutaciones pero con caracteres. La condición varía según el enunciado.

```java
for (int i = 0; i < tamAlf; i++) {
    if (condicion(nivel, alfabeto[i])) {
        sol[nivel] = alfabeto[i];
        backtracking(nivel + 1);
        // deshacer implícito: se sobreescribe
    }
}
```

**Caso base:** `nivel == L`

---

## 5. Las Podas más Comunes

Las podas son condiciones que evitan explorar caminos inválidos. Cuantas más podas, más eficiente.

| Poda | Cuándo usarla | Código |
|------|--------------|--------|
| Ya hay solución | Cuando solo buscas UNA solución | `!isSolution` |
| Fuera del tablero | Caminos en matrices | `row >= 0 && row < filas && col >= 0 && col < cols` |
| Celda visitada | Caminos sin repetir celda | `!path[row][col]` |
| Suma excedida | Cuando hay objetivo numérico | `suma + siguiente <= objetivo` |
| Sin elementos suficientes | Combinaciones | `n - inicio >= K - nivel` |
| Conflicto de asignación | Coloreo, reinas, sudoku | función `esValido()` |

---

## 6. Variables de Estado Más Habituales

| Variable | Tipo | Para qué sirve |
|----------|------|----------------|
| `sol[]` | `int[]` | Guarda la solución en construcción |
| `marca[]` | `boolean[]` | Marca qué elementos ya se usaron (permutaciones) |
| `path[][]` | `boolean[][]` | Marca qué celdas ya se visitaron (tableros) |
| `pathSolution[][]` | `boolean[][]` | Guarda la solución cuando se encuentra |
| `isSolution` | `boolean` | Para cuando solo buscas UNA solución |
| `cont` | `int` | Cuenta el número de soluciones |
| `mejor` | `int` | Guarda el mejor valor encontrado (optimización) |

---

## 7. ¿Busco UNA solución o TODAS?

### Una sola solución
Usa `isSolution` y para en cuanto la encuentres:

```java
static boolean isSolution = false;

// En el caso base:
if (condicion) {
    isSolution = true;
    // guardar solución
    return;
}

// En las podas:
if (!isSolution && ...) {
    ...
}
```

### Todas las soluciones
Sin `isSolution`, simplemente no paras:

```java
static int cont = 0;

// En el caso base:
if (condicion) {
    cont++;
    mostrar();
    return;  // return pero sin isSolution → sigue explorando
}
```

### La mejor solución (optimización)
Guardas la mejor encontrada hasta ahora:

```java
static int mejorValor = 0;
static int[] mejorSol;

// En el caso base:
if (valorActual > mejorValor) {
    mejorValor = valorActual;
    mejorSol = sol.clone();
}
```

---

## 8. El nivel y el caso base

El `nivel` es un contador de profundidad. Lo más importante es saber **cuándo empieza**.

- Si marcas la primera celda/elemento **antes** de llamar al backtracking → empieza en `nivel=0` y el caso base es `nivel == n-1`
- Si marcas dentro del backtracking → empieza en `nivel=0` y el caso base es `nivel == n`

**Ejemplo del Golf Numérico:**
```java
// Antes de llamar:
path[fila][0] = true;          // ya elegimos 1 celda
backtracking(0, fila, 0, ...); // nivel=0 significa que llevamos 1 celda

// Caso base:
if (nivel == H - 1)  // cuando nivel=H-1, llevamos H celdas en total
```

---

## 9. Parser de Fichero — Patrones Habituales

El parser casi siempre es un `BufferedReader`. Los patrones que se repiten:

```java
BufferedReader br = new BufferedReader(new FileReader(nomFich));

// Leer un entero
int n = Integer.parseInt(br.readLine().trim());

// Leer dos enteros en la misma línea
String[] dims = br.readLine().trim().split(" ");
int filas = Integer.parseInt(dims[0]);
int cols  = Integer.parseInt(dims[1]);

// Leer una fila de una matriz
String[] partes = br.readLine().trim().split(" ");
for (int j = 0; j < cols; j++) {
    tablero[i][j] = Integer.parseInt(partes[j]);
}

// Leer hasta una línea centinela (ej: -1 -1)
String linea;
while ((linea = br.readLine()) != null) {
    String[] partes = linea.trim().split(" ");
    int a = Integer.parseInt(partes[0]);
    if (a == -1) break;
    // procesar...
}

br.close();
```

> Siempre envuelve en `try-catch (IOException e)`

---

## 10. Plantilla Completa Genérica

Copia esto y adapta las partes marcadas con `// <---`:

```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Plantilla {

    static int n;                    // <--- tamaño del problema
    static int[] v;                  // <--- datos de entrada
    static int[] sol;                // <--- solución en construcción
    static boolean isSolution = false;

    public static void main(String[] args) {
        new Plantilla("input.txt");
    }

    public Plantilla(String nomFich) {
        loadData(nomFich);
        sol = new int[n];

        // Llamada inicial al backtracking
        backtracking(0 /*, otros parámetros */);

        if (!isSolution) System.out.println("No existe solución");
    }

    static void backtracking(int nivel /*, otros parámetros */) {

        if (/* caso base */) {          // <---
            if (/* es solución */) {    // <---
                isSolution = true;
                // mostrar o guardar
            }
            return;
        }

        for (/* cada opción */) {       // <---
            if (!isSolution && /* podas */) {  // <---
                // elegir                      // <---
                backtracking(nivel + 1);
                // deshacer                    // <---
            }
        }
    }

    static void loadData(String nomFich) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(nomFich));
            // leer datos                       // <---
            br.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

---

## 11. Resumen Visual — Tipos de Problemas

```
¿Qué tienes que hacer?
│
├── Elegir elementos de un array
│   ├── El orden importa → PERMUTACIONES (usa marca[])
│   └── El orden NO importa → COMBINACIONES (usa inicio)
│
├── Recorrer una matriz
│   └── CAMINO EN TABLERO (usa path[][], movimientos[][])
│
├── Asignar valores sin conflicto
│   └── DISTRIBUCIÓN (usa esValido())
│       ├── Reinas → comprobar fila, columna, diagonal
│       ├── Coloreo → comprobar vecinos en grafo
│       └── Sudoku → comprobar fila, columna, subcuadrado
│
└── Generar secuencias de caracteres
    └── PALABRAS (usa alfabeto[], condición según enunciado)
```

---

## 12. Errores Más Comunes

| Error | Consecuencia | Solución |
|-------|-------------|----------|
| Olvidar deshacer | Estado contaminado, resultados incorrectos | Siempre simetría: elegir ↔ deshacer |
| Caso base mal definido | Bucle infinito o soluciones incorrectas | Pregúntate cuándo el problema está "completo" |
| Poda con `> 0` en vez de `>= 0` | Pierde la fila/columna 0 del tablero | Usar `>= 0` para límites inferiores |
| No guardar `pathSolution` | Pierdes la solución al deshacer | Copiar el camino cuando `isSolution = true` |
| Confundir fila y columna | Resultados incorrectos | Nombrar siempre `row` y `col`, nunca `x` e `y` |
| No usar `.trim()` en el parser | `NumberFormatException` con espacios | Siempre `.trim().split(" ")` |
