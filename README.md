# Algoritmo de PathFinding en Java

## Programado por: Emilliano Yozzi y Fernando Invernizzi

### Este proyecto tomo aproximadamente 5hrs.

Estabamos muy aburridos y se me ocurrió la idea de programar un algoritmo de PathFinding. Cuando lo mencione, a Emi y a mi se nos iluminaron los ojos.

Y lo hicimos!

Cabe recalcar que no usamos algoritmos de guia, sino que el 100% del codigo fue craneado por nosotros.

El objetivo de este proyecto es crear un algoritmo que, dado cualquier laberinto, sea capaz de encontrar y mostrar el camino que hay que seguir desde el punto de inicio hasta el punto final. En caso de que hayan varios caminos, mostrará siempre el más corto.

Nota: Todas las funciones estan comentadas en español.
Nota again: Actualmente tiene implementada una interfaz gráfica que permite crear laberintos mucho más rápido y sin perder tu salud mental.
### Forma de uso:
Se comienza con una matriz de 10x10, pero se puede cambiar al tamaño deseado usando el panel de la derecha. 
Las casillas verdes se consideran como terreno caminable, mientras que las azules se consideran como "agua" o "pared". La casilla roja es el punto de inicio y la violeta el de llegada, aunque no hay diferencia si se usan al revés.
Usando el panel de la derecha se puede dibujar el laberinto y colocar la entrada y salida en cualquier posición del mapa.
Al presionar "Buscar camino" se marca con amarillo el camino más corto entre la salida y la entrada.
El botón "Obtener mapa" te brinda un código que representa el mapa que dibujaste. Esto es útil para compartir un mapa con otra persona, ya que con el botón "Cargar mapa", se carga el código ingresado arriba.

### Precondiciones:

- Se espera que el punto de inicio y final existan en el tablero antes de intentar buscar el camino. 
### Observaciones:

- Nuestro algoritmo recorre en forma recursiva todos los posibles caminos y luego compara los caminos que llegaron a la meta y se queda con el más corto. En caso de que hayan espacios vacios en la matriz de 2 x 2, el algoritmo pierde eficiencia, dado que tiene que recorrer los mismos caminos, pero con alguna leve variación. 

- Cuanto más grandes son los espacios vacíos, más posibles caminos tiene que calcular el algoritmo.

De hecho usamos esto para hacer algunas pruebas con matrices vacias de distintos tamaños...

---

Experimento 1:

Dejamos la matriz vacía y colocamos los puntos de entrada y salida en esquinas opuestas. Esto es lo que pasó:

(Tamaño - Tiempo de ejecución)

- 4 x 4 - 0s

- 5 x 5 - 0s

- 6 x 6 - 5s

- 7 x 7 - +18min (lo paramos porque no terminaba mas)

---

Experimento 2:

Ahora se modificó el código haciéndolo mucho más eficiente para estos casos y apenas un poco más eficiente para laberintos normales (se podría decir que el algoritmo ahora es mas inteligente c;). Entonces, repetimos el experimento anterior:

- 4 x 4 - 0s

- 5 x 5 - 0s

- 6 x 6 - 0s

- 7 x 7 - 0s

- 8 x 8 - 0s

- 9 x 9 - 0s

- 10 x 10 - 1s

- 11 x 11 - 5s

- 12 x 12 - 52s

Si bien, nuevamente aumenta muy rapido el tiempo de ejecución en base al tamaño de la matriz. Con la modificación se logró poder superar areas de 9x9 sin un impacto notable en el tiempo de ejecución y tambíen se logró reducir la velocidad con la que aumenta en base al tamaño de la matriz, ya que en el experimento 1 pasaba de 5s a +18min y en este de 5s a 52s.
---
### Cambiamos el metodo de creacion de mapas.

Ahora la funcion crear mapa recibe un string de ceros y unos para espacio vacio y muros, 3 para la posicion inicial y 4 para el destino, lo que nos permite crear mas rapidamente mapas para probar todos los casos que queramos.

Un par de alteraciones menores nacieron a raiz de este cambio, como los parametros que le pasamos a la funcion recursiva, entre otras cosas.
