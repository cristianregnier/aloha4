Se simula un shell de sistema operativo.

Lo propuesta es que el shell se instancié utilizando un puntero al sistema de archivos sobre el cual estará operando.
De esta menra se evita que sea el shell el que mantenga el estado del filesystem.

Adicionalmente se compone al shell con un parser de comandos. El parser realiza el análisis sintácitco de la cadena
de texto ingresada por consola y la transforma en un objecto Command para su posterior procesamiento.

Una mejora posible sería pasar por parámetro el parser al shell desacoplandose de esta forma la interpretación de los comandos.

En esta versión de la solución es el mismo shell el que procesa los comandos. Una mejora sería delegar la ejecución de
los comandos al sistema de archivos utilizando el patrón gof command. Esta solución requiere un poco mas de tiempo de análisis
y desarrollo.

Documentación consultada para diseñar la solución:
