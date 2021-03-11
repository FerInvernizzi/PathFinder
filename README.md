# Algoritmo de PathFinding en Java

## Programado por: Emilliano Yozzi y Fernando Invernizzi

### Este proyecto tomo aproximadamente 5hrs.

Estabamos muy aburridos y se me ocurrio la idea de programar un algoritmo de pathfinding, cuando lo mencione a Emi y a mi se nos iluminaron los ojos.

Y lo hicimos!

Cabe recalcar que no usamos algoritmos de guia, sino que lo craneamos nosotros totalmente.

### Precondiciones:

- La matriz/mapa debe ser cuadrada/o.

- Debe haber solo UN punto de partida y UN final.

- Ambos puntos deben estar dentro de la matriz.

### Observaciones:

- El programa funciona si principio y fin estan en las mismas coordenadas, nos indica que hay que caminar 0 casillas y concluye la ejecucion.

- En caso de que hayan espacios vacios de 2 x 2, creemos que el algoritmo no es super eficiente, dado a que opera para cada posibilidad de movimiento, en orden "relampago" (arriba, izquierda, derecha, abajo), por lo que verifica todos los posibles casos antes de continuar.

De hecho usamos esto para hacer unas pruebas con matrices vacias de distintos tamaños:

---

Tamaño - Tiempo que tarda en ejecutarlo
.4 x 4 - 0s -> Netbeans siendo
.5 x 5 - 0s -> poco preciso
.6 x 6 - 5s
.7 x 7 - +18min (lo paramos porque no terminaba mas)

---

Todas las funciones estan comentadas en español asi que, en caso de querer saber como funciona, leerlo.
