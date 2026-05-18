# Guía Completa de Algoritmos Voraces — Examen

---

## 1. ¿Qué es un Algoritmo Voraz?

Un algoritmo voraz toma en cada paso la **decisión localmente óptima** sin reconsiderar decisiones pasadas. No hay backtracking ni tabla de PD — simplemente avanza hacia adelante eligiendo siempre lo que parece mejor en ese momento.

**Cuándo funciona:** cuando la elección local óptima lleva a la solución global óptima (no siempre es así — hay que demostrarlo o saberlo de antemano).

**Diferencia con PD:** PD considera todas las opciones y guarda resultados. Voraz elige una sola opción en cada paso y no vuelve atrás.

**Diferencia con Backtracking:** Backtracking prueba todo. Voraz prueba una sola opción por paso.

---

## 2. Estructura Universal

```java
// 1. Ordenar los elementos según algún criterio
Arrays.sort(elementos, criterioDeOrden);

// 2. Iterar y elegir greedy
for (elemento : elementos) {
    if (puedoElegir(elemento)) {
        elegir(elemento);
        actualizarEstado();
    }
}

// 3. La solución es lo que se ha elegido
```

### Las 3 preguntas clave ante cualquier enunciado:

| # | Pregunta | Ejemplo (Mochila Fraccional) |
|---|----------|------------------------------|
| 1 | ¿Cómo **ordenamos** los elementos? | Por ratio valor/peso descendente |
| 2 | ¿Cuándo **elegimos** un elemento? | Si cabe en la mochila |
| 3 | ¿Qué **actualizamos** al elegir? | Capacidad restante, valor acumulado |

---

## 3. Algoritmos y su Implementación

---

### Algoritmo 1 — Mochila Fraccional (Voraz)

**Enunciado:** igual que la mochila 0/1 pero puedes coger **fracciones** de los objetos. Maximizar valor.

**Criterio voraz:** ordenar por **ratio valor/peso** de mayor a menor. Siempre coges primero el objeto más "rentable".

**Por qué funciona:** al poder fraccionar, siempre conviene coger lo más rentable primero.

```java
static void mochilaFraccional(int[] pesos, int[] valores, int n, int W) {
    // Crear array de índices ordenados por ratio valor/peso descendente
    Integer[] idx = new Integer[n];
    for (int i = 0; i < n; i++) idx[i] = i;
    Arrays.sort(idx, (a, b) -> Double.compare(
        (double) valores[b] / pesos[b],
        (double) valores[a] / pesos[a]
    ));

    double valorTotal = 0;
    int capacidad = W;

    for (int i : idx) {
        if (capacidad == 0) break;

        if (pesos[i] <= capacidad) {
            // Cogemos el objeto entero
            valorTotal += valores[i];
            capacidad  -= pesos[i];
            System.out.println("Objeto " + i + " completo");
        } else {
            // Cogemos la fracción que cabe
            double fraccion = (double) capacidad / pesos[i];
            valorTotal += valores[i] * fraccion;
            System.out.println("Objeto " + i + " fraccion=" + fraccion);
            capacidad = 0;
        }
    }
    System.out.println("Valor total: " + valorTotal);
}
```

> ⚠️ La mochila fraccional voraz da el óptimo. La mochila 0/1 voraz NO da el óptimo (hay que usar PD).

---

### Algoritmo 2 — Cambio de Monedas Voraz

**Enunciado:** dado un array de monedas y una cantidad X, dar el cambio usando el mínimo número de monedas.

**Criterio voraz:** usar siempre la moneda más grande posible.

**Por qué funciona (solo con ciertas monedas):** con el sistema de monedas estándar (1, 5, 10, 25...) funciona. Con monedas arbitrarias puede NO dar el óptimo — ahí hay que usar PD.

```java
static void cambioMonedasVoraz(int[] monedas, int X) {
    // Ordenar monedas de mayor a menor
    Arrays.sort(monedas);

    int cantidad = X;
    int numMonedas = 0;

    for (int i = monedas.length - 1; i >= 0 && cantidad > 0; i--) {
        while (cantidad >= monedas[i]) {
            cantidad    -= monedas[i];
            numMonedas  ++;
            System.out.println("Moneda: " + monedas[i]);
        }
    }

    if (cantidad == 0) System.out.println("Total monedas: " + numMonedas);
    else System.out.println("Imposible dar el cambio exacto");
}
```

---

### Algoritmo 3 — Planificación de Tareas

**Enunciado:** N tareas, cada una con un plazo (deadline) y un beneficio. Solo puedes hacer una tarea por unidad de tiempo. Maximizar beneficio.

**Criterio voraz:** ordenar por beneficio descendente. Para cada tarea, asignarla al último slot libre antes de su deadline.

```java
static void planificacionTareas(int[] deadlines, int[] beneficios, int n) {
    // Ordenar por beneficio descendente
    Integer[] idx = new Integer[n];
    for (int i = 0; i < n; i++) idx[i] = i;
    Arrays.sort(idx, (a, b) -> beneficios[b] - beneficios[a]);

    int maxDeadline = 0;
    for (int d : deadlines) maxDeadline = Math.max(maxDeadline, d);

    int[] slots = new int[maxDeadline + 1]; // slots[t] = tarea asignada al tiempo t (-1 = libre)
    Arrays.fill(slots, -1);

    int beneficioTotal = 0;

    for (int i : idx) {
        // Buscar el slot libre más tarde antes del deadline
        for (int t = deadlines[i]; t >= 1; t--) {
            if (slots[t] == -1) {
                slots[t] = i;
                beneficioTotal += beneficios[i];
                System.out.println("Tarea " + i + " en slot " + t);
                break;
            }
        }
    }
    System.out.println("Beneficio total: " + beneficioTotal);
}
```

---

### Algoritmo 4 — Dijkstra (Camino más corto)

**Enunciado:** dado un grafo con pesos, encontrar el camino más corto desde un nodo origen a todos los demás.

**Criterio voraz:** en cada paso, elegir el nodo no visitado con menor distancia acumulada.

**Representación:** matriz de adyacencia `grafo[i][j]` = peso de la arista (0 si no hay arista).

```java
static void dijkstra(int[][] grafo, int n, int origen) {
    int[] dist    = new int[n];     // distancia mínima desde origen
    boolean[] vis = new boolean[n]; // nodos ya procesados

    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[origen] = 0;

    for (int iter = 0; iter < n; iter++) {
        // Elegir el nodo no visitado con menor distancia (paso voraz)
        int u = -1;
        for (int v = 0; v < n; v++) {
            if (!vis[v] && (u == -1 || dist[v] < dist[u])) u = v;
        }

        if (dist[u] == Integer.MAX_VALUE) break; // resto inaccesible
        vis[u] = true;

        // Actualizar distancias de los vecinos
        for (int v = 0; v < n; v++) {
            if (grafo[u][v] != 0 && dist[u] + grafo[u][v] < dist[v]) {
                dist[v] = dist[u] + grafo[u][v];
            }
        }
    }

    System.out.println("Distancias desde nodo " + origen + ":");
    for (int i = 0; i < n; i++) {
        System.out.println("  Nodo " + i + ": " +
            (dist[i] == Integer.MAX_VALUE ? "inaccesible" : dist[i]));
    }
}
```

**Parser de grafo (matriz de adyacencia):**
```
4          <- número de nodos
0 2 0 6    <- fila 0 (0 = sin arista)
2 0 3 0    <- fila 1
0 3 0 1    <- fila 2
6 0 1 0    <- fila 3
```

---

### Algoritmo 5 — Kruskal (Árbol de expansión mínima)

**Enunciado:** dado un grafo, encontrar el árbol de expansión mínima (conectar todos los nodos con el mínimo coste total).

**Criterio voraz:** ordenar aristas por peso ascendente. Añadir una arista solo si no forma ciclo.

**Estructura Union-Find** para detectar ciclos:

```java
static int[] padre;

static int find(int x) {
    if (padre[x] != x) padre[x] = find(padre[x]); // compresión de camino
    return padre[x];
}

static void union(int a, int b) {
    padre[find(a)] = find(b);
}

static void kruskal(int[][] aristas, int n, int numAristas) {
    // aristas[i] = {peso, nodoA, nodoB}
    Arrays.sort(aristas, (a, b) -> a[0] - b[0]); // ordenar por peso

    padre = new int[n];
    for (int i = 0; i < n; i++) padre[i] = i;

    int costeTotal = 0;
    int aristasUsadas = 0;

    for (int[] arista : aristas) {
        int peso = arista[0], u = arista[1], v = arista[2];

        if (find(u) != find(v)) { // no forma ciclo
            union(u, v);
            costeTotal += peso;
            aristasUsadas++;
            System.out.println("Arista " + u + "-" + v + " peso=" + peso);
        }

        if (aristasUsadas == n - 1) break; // árbol completo
    }
    System.out.println("Coste mínimo: " + costeTotal);
}
```

**Parser de aristas:**
```
4          <- número de nodos
5          <- número de aristas
1 0 1      <- arista: peso=1, nodo0=0, nodo1=1
4 0 2      <- arista: peso=4, nodo0=0, nodo1=2
2 1 2      <- arista: peso=2, nodo0=1, nodo1=2
5 1 3      <- arista: peso=5, nodo0=1, nodo1=3
3 2 3      <- arista: peso=3, nodo0=2, nodo1=3
```

---

### Algoritmo 6 — Prim (Árbol de expansión mínima)

**Enunciado:** igual que Kruskal. Diferente estrategia.

**Criterio voraz:** empezar desde un nodo y en cada paso añadir el nodo más cercano al árbol ya construido.

```java
static void prim(int[][] grafo, int n) {
    int[] dist    = new int[n];     // distancia mínima al árbol
    boolean[] enArbol = new boolean[n];
    int[] padre   = new int[n];

    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[0] = 0;
    padre[0] = -1;

    int costeTotal = 0;

    for (int iter = 0; iter < n; iter++) {
        // Elegir el nodo no añadido más cercano al árbol (paso voraz)
        int u = -1;
        for (int v = 0; v < n; v++) {
            if (!enArbol[v] && (u == -1 || dist[v] < dist[u])) u = v;
        }

        enArbol[u] = true;
        costeTotal += dist[u];
        if (padre[u] != -1)
            System.out.println("Arista " + padre[u] + "-" + u + " peso=" + dist[u]);

        // Actualizar distancias de vecinos
        for (int v = 0; v < n; v++) {
            if (grafo[u][v] != 0 && !enArbol[v] && grafo[u][v] < dist[v]) {
                dist[v]  = grafo[u][v];
                padre[v] = u;
            }
        }
    }
    System.out.println("Coste mínimo: " + costeTotal);
}
```

> **Kruskal vs Prim:** Kruskal ordena aristas y usa Union-Find. Prim crece desde un nodo como Dijkstra. Ambos dan el mismo resultado.

---

## 4. Parser de Fichero para Voraces

```java
static void loadData(String nomFich) {
    try {
        BufferedReader br = new BufferedReader(new FileReader(nomFich));

        // Leer número de nodos y aristas
        int n = Integer.parseInt(br.readLine().trim());
        int m = Integer.parseInt(br.readLine().trim());

        // Leer matriz de adyacencia
        int[][] grafo = new int[n][n];
        for (int i = 0; i < n; i++) {
            String[] partes = br.readLine().trim().split(" ");
            for (int j = 0; j < n; j++)
                grafo[i][j] = Integer.parseInt(partes[j]);
        }

        // Leer lista de aristas
        int[][] aristas = new int[m][3]; // {peso, u, v}
        for (int i = 0; i < m; i++) {
            String[] partes = br.readLine().trim().split(" ");
            aristas[i][0] = Integer.parseInt(partes[0]); // peso
            aristas[i][1] = Integer.parseInt(partes[1]); // nodo u
            aristas[i][2] = Integer.parseInt(partes[2]); // nodo v
        }

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
├── Maximizar valor cogiendo objetos (puedes fraccionar)
│   └── MOCHILA FRACCIONAL
│       criterio: ratio valor/peso descendente
│
├── Mínimas monedas para dar cambio (monedas estándar)
│   └── CAMBIO DE MONEDAS VORAZ
│       criterio: moneda más grande posible
│
├── Maximizar beneficio asignando tareas con deadline
│   └── PLANIFICACIÓN DE TAREAS
│       criterio: beneficio descendente, slot más tarde posible
│
├── Camino más corto desde un nodo a todos los demás
│   └── DIJKSTRA
│       criterio: nodo no visitado con menor distancia
│
└── Conectar todos los nodos con coste mínimo
    ├── KRUSKAL → ordena aristas, usa Union-Find
    └── PRIM    → crece desde un nodo, como Dijkstra
```

---

## 6. Tabla Comparativa — Cuándo Usar Cada Técnica

| Problema | Backtracking | PD | Voraz |
|----------|-------------|-----|-------|
| Mochila 0/1 | ✅ (lento) | ✅ (óptimo) | ❌ (no da óptimo) |
| Mochila fraccional | ✅ (lento) | ✅ | ✅ (óptimo y rápido) |
| Cambio monedas | ✅ | ✅ (siempre óptimo) | ⚠️ (solo con monedas estándar) |
| Camino mínimo grafo | ❌ | ✅ (Floyd-Warshall) | ✅ (Dijkstra) |
| Árbol expansión mínima | ❌ | ❌ | ✅ (Kruskal/Prim) |
| Permutaciones/combinaciones | ✅ | ❌ | ❌ |

---

## 7. Errores Más Comunes en Voraces

| Error | Consecuencia | Solución |
|-------|-------------|----------|
| Usar voraz en mochila 0/1 | Resultado no óptimo | Usar PD para mochila 0/1 |
| No ordenar antes de iterar | Resultado incorrecto | Siempre ordenar primero según el criterio |
| En Dijkstra, no comprobar `MAX_VALUE` al relajar | Overflow al sumar | Comprobar `dist[u] != MAX_VALUE` antes de sumar |
| En Kruskal, olvidar Union-Find | Se forman ciclos | Siempre comprobar `find(u) != find(v)` |
| Comparar doubles con `==` | Errores de precisión | Usar `Math.abs(a-b) < 1e-9` o trabajar con enteros |
