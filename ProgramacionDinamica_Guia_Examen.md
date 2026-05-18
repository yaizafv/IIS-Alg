# Guía Completa de Programación Dinámica — Examen

---

## 1. ¿Qué es la Programación Dinámica?

La Programación Dinámica (PD) resuelve problemas dividiéndolos en subproblemas más pequeños y **guardando sus resultados en una tabla (matriz)** para no recalcularlos.

**Diferencia con backtracking:** backtracking prueba todo y deshace. PD construye la solución de forma incremental guardando resultados parciales.

**Diferencia con memoización:** memoización es PD recursiva con caché. En tu examen usas **matrices iterativas** (bottom-up), construyendo la tabla desde los casos base hacia arriba.

---

## 2. Estructura Universal con Matriz

Todo problema de PD con matriz sigue este esquema:

```java
// 1. Crear la tabla
int[][] dp = new int[n+1][m+1];

// 2. Inicializar casos base (fila 0 y/o columna 0)
for (int i = 0; i <= n; i++) dp[i][0] = valorBase;
for (int j = 0; j <= m; j++) dp[0][j] = valorBase;

// 3. Rellenar la tabla con la recurrencia
for (int i = 1; i <= n; i++) {
    for (int j = 1; j <= m; j++) {
        dp[i][j] = recurrencia(dp, i, j);  // depende del problema
    }
}

// 4. La solución está en dp[n][m] (normalmente)
System.out.println(dp[n][m]);
```

### Las 4 preguntas clave ante cualquier enunciado:

| # | Pregunta | Ejemplo (Mochila) |
|---|----------|-------------------|
| 1 | ¿Qué representa `dp[i][j]`? | Máximo valor con i objetos y capacidad j |
| 2 | ¿Cuál es el caso base? | `dp[0][j] = 0`, `dp[i][0] = 0` |
| 3 | ¿Cuál es la recurrencia? | ¿Meto el objeto o no? |
| 4 | ¿Dónde está la solución? | `dp[n][W]` |

---

## 3. Problemas y sus Recurrencias

---

### Problema 1 — Mochila 0/1

**Enunciado:** N objetos con peso y valor. Mochila de capacidad W. Maximizar valor sin superar W.

**`dp[i][j]`** = máximo valor usando los primeros `i` objetos con capacidad `j`

**Casos base:**
```java
dp[0][j] = 0;  // sin objetos, valor = 0
dp[i][0] = 0;  // sin capacidad, valor = 0
```

**Recurrencia:**
```java
if (pesos[i-1] > j) {
    // No cabe el objeto i → mismo valor sin él
    dp[i][j] = dp[i-1][j];
} else {
    // Máximo entre: no meterlo vs meterlo
    dp[i][j] = Math.max(dp[i-1][j],
                        dp[i-1][j - pesos[i-1]] + valores[i-1]);
}
```

**Solución:** `dp[n][W]`

**Reconstruir qué objetos se cogieron:**
```java
int j = W;
for (int i = n; i >= 1; i--) {
    if (dp[i][j] != dp[i-1][j]) {
        System.out.println("Objeto " + i + " incluido");
        j -= pesos[i-1];
    }
}
```

**Código completo:**
```java
static void mochilaDP(int[] pesos, int[] valores, int n, int W) {
    int[][] dp = new int[n+1][W+1];

    for (int i = 1; i <= n; i++) {
        for (int j = 0; j <= W; j++) {
            if (pesos[i-1] > j) {
                dp[i][j] = dp[i-1][j];
            } else {
                dp[i][j] = Math.max(dp[i-1][j],
                                    dp[i-1][j - pesos[i-1]] + valores[i-1]);
            }
        }
    }
    System.out.println("Valor máximo: " + dp[n][W]);
}
```

---

### Problema 2 — Cambio de Monedas

**Enunciado:** dado un array de monedas y una cantidad X, encontrar el mínimo número de monedas para llegar a X.

**`dp[i]`** = mínimo número de monedas para hacer la cantidad `i`
> Este es 1D, pero se construye igual de forma iterativa.

**Caso base:**
```java
dp[0] = 0;                          // para cantidad 0, necesitamos 0 monedas
Arrays.fill(dp, Integer.MAX_VALUE); // el resto, infinito (imposible de momento)
dp[0] = 0;
```

**Recurrencia:**
```java
for (int i = 1; i <= X; i++) {
    for (int moneda : monedas) {
        if (moneda <= i && dp[i - moneda] != Integer.MAX_VALUE) {
            dp[i] = Math.min(dp[i], dp[i - moneda] + 1);
        }
    }
}
```

**Solución:** `dp[X]` (si sigue siendo `MAX_VALUE`, no hay solución)

**Código completo:**
```java
static void cambioMonedas(int[] monedas, int X) {
    int[] dp = new int[X+1];
    Arrays.fill(dp, Integer.MAX_VALUE);
    dp[0] = 0;

    for (int i = 1; i <= X; i++) {
        for (int moneda : monedas) {
            if (moneda <= i && dp[i - moneda] != Integer.MAX_VALUE) {
                dp[i] = Math.min(dp[i], dp[i - moneda] + 1);
            }
        }
    }

    if (dp[X] == Integer.MAX_VALUE) System.out.println("Imposible");
    else System.out.println("Mínimo monedas: " + dp[X]);
}
```

---

### Problema 3 — Suma Máxima de Subarray (Kadane)

**Enunciado:** dado un array de enteros (puede haber negativos), encontrar el subarray contiguo con mayor suma.

**`dp[i]`** = suma máxima de subarray que termina en el índice `i`

**Recurrencia:**
```java
dp[0] = v[0];
for (int i = 1; i < n; i++) {
    // ¿Extiendo el subarray anterior o empiezo uno nuevo?
    dp[i] = Math.max(v[i], dp[i-1] + v[i]);
}
```

**Solución:** `max(dp[0], dp[1], ..., dp[n-1])`

**Código completo:**
```java
static void sumaMaximaSubarray(int[] v, int n) {
    int[] dp = new int[n];
    dp[0] = v[0];
    int max = dp[0];

    for (int i = 1; i < n; i++) {
        dp[i] = Math.max(v[i], dp[i-1] + v[i]);
        max = Math.max(max, dp[i]);
    }
    System.out.println("Suma máxima: " + max);
}
```

---

### Problema 4 — Camino Mínimo en Tablero

**Enunciado:** dado un tablero de costes, encontrar el camino de (0,0) a (N-1,M-1) con coste mínimo. Solo puedes ir derecha o abajo.

**`dp[i][j]`** = coste mínimo para llegar a la celda (i,j)

**Casos base:**
```java
dp[0][0] = tablero[0][0];

// Primera fila: solo puedes venir de la izquierda
for (int j = 1; j < cols; j++)
    dp[0][j] = dp[0][j-1] + tablero[0][j];

// Primera columna: solo puedes venir de arriba
for (int i = 1; i < filas; i++)
    dp[i][0] = dp[i-1][0] + tablero[i][0];
```

**Recurrencia:**
```java
for (int i = 1; i < filas; i++) {
    for (int j = 1; j < cols; j++) {
        // Mínimo entre venir de arriba o de la izquierda
        dp[i][j] = tablero[i][j] + Math.min(dp[i-1][j], dp[i][j-1]);
    }
}
```

**Solución:** `dp[filas-1][cols-1]`

**Código completo:**
```java
static void caminoMinimo(int[][] tablero, int filas, int cols) {
    int[][] dp = new int[filas][cols];
    dp[0][0] = tablero[0][0];

    for (int j = 1; j < cols; j++)
        dp[0][j] = dp[0][j-1] + tablero[0][j];

    for (int i = 1; i < filas; i++)
        dp[i][0] = dp[i-1][0] + tablero[i][0];

    for (int i = 1; i < filas; i++)
        for (int j = 1; j < cols; j++)
            dp[i][j] = tablero[i][j] + Math.min(dp[i-1][j], dp[i][j-1]);

    System.out.println("Coste mínimo: " + dp[filas-1][cols-1]);
}
```

---

### Problema 5 — LCS (Longest Common Subsequence)

**Enunciado:** dadas dos cadenas, encontrar la longitud de la subsecuencia común más larga. Una subsecuencia no tiene por qué ser contigua.

**Ejemplo:** LCS de "ABCBDAB" y "BDCAB" → "BCAB" longitud 4

**`dp[i][j]`** = longitud de la LCS de los primeros `i` caracteres de A y los primeros `j` de B

**Casos base:**
```java
dp[0][j] = 0;  // cadena A vacía → LCS = 0
dp[i][0] = 0;  // cadena B vacía → LCS = 0
```

**Recurrencia:**
```java
if (A.charAt(i-1) == B.charAt(j-1)) {
    // Los caracteres coinciden → extendemos la LCS
    dp[i][j] = dp[i-1][j-1] + 1;
} else {
    // No coinciden → cogemos el máximo de ignorar uno u otro
    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
}
```

**Solución:** `dp[n][m]`

**Código completo:**
```java
static void lcs(String A, String B) {
    int n = A.length(), m = B.length();
    int[][] dp = new int[n+1][m+1];

    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) {
            if (A.charAt(i-1) == B.charAt(j-1)) {
                dp[i][j] = dp[i-1][j-1] + 1;
            } else {
                dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
            }
        }
    }
    System.out.println("Longitud LCS: " + dp[n][m]);
}
```

---

### Problema 6 — Distancia de Edición (Edit Distance)

**Enunciado:** dadas dos cadenas, encontrar el mínimo número de operaciones (insertar, eliminar, sustituir) para transformar A en B.

**`dp[i][j]`** = mínimo operaciones para transformar los primeros `i` chars de A en los primeros `j` de B

**Casos base:**
```java
dp[i][0] = i;  // eliminar i caracteres
dp[0][j] = j;  // insertar j caracteres
```

**Recurrencia:**
```java
if (A.charAt(i-1) == B.charAt(j-1)) {
    // Caracteres iguales → no hace falta operación
    dp[i][j] = dp[i-1][j-1];
} else {
    dp[i][j] = 1 + Math.min(
        dp[i-1][j],    // eliminar de A
        Math.min(
            dp[i][j-1],    // insertar en A
            dp[i-1][j-1]   // sustituir
        )
    );
}
```

**Solución:** `dp[n][m]`

**Código completo:**
```java
static void editDistance(String A, String B) {
    int n = A.length(), m = B.length();
    int[][] dp = new int[n+1][m+1];

    for (int i = 0; i <= n; i++) dp[i][0] = i;
    for (int j = 0; j <= m; j++) dp[0][j] = j;

    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) {
            if (A.charAt(i-1) == B.charAt(j-1)) {
                dp[i][j] = dp[i-1][j-1];
            } else {
                dp[i][j] = 1 + Math.min(dp[i-1][j],
                               Math.min(dp[i][j-1], dp[i-1][j-1]));
            }
        }
    }
    System.out.println("Distancia de edición: " + dp[n][m]);
}
```

---

## 4. Parser de Fichero para PD

```java
static void loadData(String nomFich) {
    try {
        BufferedReader br = new BufferedReader(new FileReader(nomFich));

        // Leer un entero
        int n = Integer.parseInt(br.readLine().trim());

        // Leer array en una línea
        String[] partes = br.readLine().trim().split(" ");
        int[] v = new int[n];
        for (int i = 0; i < n; i++) v[i] = Integer.parseInt(partes[i]);

        // Leer matriz
        int[][] tablero = new int[filas][cols];
        for (int i = 0; i < filas; i++) {
            partes = br.readLine().trim().split(" ");
            for (int j = 0; j < cols; j++)
                tablero[i][j] = Integer.parseInt(partes[j]);
        }

        // Leer cadena
        String A = br.readLine().trim();

        br.close();
    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
    }
}
```

---

## 5. Cómo Identificar el Tipo de Problema

```
¿Qué pide el enunciado?
│
├── Maximizar/minimizar valor con restricción de peso/capacidad
│   └── MOCHILA 0/1
│
├── Mínimo número de monedas/pasos para llegar a X
│   └── CAMBIO DE MONEDAS
│
├── Máxima suma de elementos contiguos
│   └── SUMA MÁXIMA DE SUBARRAY (Kadane)
│
├── Camino de menor coste en una matriz (solo der/abajo)
│   └── CAMINO MÍNIMO EN TABLERO
│
├── Subsecuencia común más larga entre dos cadenas
│   └── LCS
│
└── Mínimas operaciones para transformar una cadena en otra
    └── DISTANCIA DE EDICIÓN
```

---

## 6. Errores Más Comunes en PD

| Error | Consecuencia | Solución |
|-------|-------------|----------|
| Tamaño de la tabla `n` en vez de `n+1` | ArrayIndexOutOfBounds | Siempre `new int[n+1][m+1]` cuando índices van de 0 a n |
| Olvidar inicializar casos base | Resultados incorrectos | Inicializa fila 0 y columna 0 antes del doble bucle |
| Usar `i` cuando es `i-1` en el array original | Off-by-one | En la tabla dp el índice `i` corresponde a `v[i-1]` |
| No usar `Integer.MAX_VALUE` para "imposible" | Sumas incorrectas al comparar | Comprueba siempre `!= Integer.MAX_VALUE` antes de sumar |
| Leer la solución de la celda equivocada | Resultado incorrecto | La solución es casi siempre `dp[n][m]` o `dp[n][W]` |
