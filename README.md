## TecnologiasMultimedia
## Cristina Reina León

## AvCont-4(a): Implementació de Compressió LZ-77

Apartado 1: Comprobad que el programa comprime y descomprime correctamente una cadena de 25 bits aleatorios con Mdes = 8 y Ment = 4.
Ment: 4
Mdes: 8
0110101010001111111010010 (Cadena a comprimir)
0110101010010010011010001001100101101101000010011101110 (Cadena comprimida)
0110101010010010011010001001100101101101000010011101110 (Cadena a descomprimir)
0110101010001111111010010 (Cadena descomprimida)
Factor de compresión: 0.45454547

Apartado 2:

Con la ventana deslizante y la de entrada muy pequeñas (mdes, ment < ) se gastan mas bits en convertir longitud y distancia a
bits de longitud fija que lo que nos podriamos ahorrar por evitar repetir el patron encontrado.

Factor de compresión: 0.6944444 (8,4)
Factor de compresión: 0.5085731 (8,4)
Factor de compresión: 0.5622942 (8,4)
Factor de compresión: 0.75757575 (16,32)
Factor de compresión: 0.56693935 (16,32)

Si usamos una medida de la ventana deslizante más grande y una de entrada considerablemente más pequeña, usaremos demasiados bits para hacer referencia a la distancia del patrón, por lo que tampoco conseguiremos que haya compresión.

Factor de compresión: 0.385845 (2048, 8)
Factor de compresión: 0.6533508 (1024, 8)
Factor de compresión: 0.75431037 (1024, 8)
Factor de compresión: 0.6312562 (1024, 256)

He hecho pruebas para todos los valores de ventanas posibles pero no parece que se logre compresion en ningun caso.
No sé si es por algun error en el código (aunque con varias pruebas hechas manualmente y comprimiendo y descomprimiendo
los resultados son correctos) o por algun fallo a la hora de calcular el factor de compresión.

En caso de no ser un fallo, podria ser debido a que estamos generando un string aleatorio, con lo que es mas dificil que se den patrones
de forma aleatoria. Si el string no fuese aleatorio, que fuesen datos reales, seria mucho mas comun encontrar patrones repetidos a
lo largo del string, lo que haria que hubiese una compresión de los datos.

Factor de compresión: 0.5018281 (8, 4)
Factor de compresión: 0.5521811 (16, 4)
Factor de compresión: 0.5449591 (32, 4)
Factor de compresión: 0.49853998 (64, 4)
Factor de compresión: 0.44894817 (128, 4)
Factor de compresión: 0.40897405 (256, 4)
Factor de compresión: 0.38138825 (512, 4)
Factor de compresión: 0.36935416 (1024, 4)
Factor de compresión: 0.385845 (2048, 4)
Factor de compresión: 0.53904206 (16, 8)
Factor de compresión: 0.6232194 (32, 8)
Factor de compresión: 0.667175 (64, 8)
Factor de compresión: 0.68139786 (128, 8)
Factor de compresión: 0.68292683 (256, 8)
Factor de compresión: 0.66806644 (512, 8)
Factor de compresión: 0.65037626 (1024, 8)
Factor de compresión: 0.6533508 (2048, 8)
Factor de compresión: 0.55723614 (32, 16)
Factor de compresión: 0.6204024 (64, 16)
Factor de compresión: 0.656291 (128, 16)
Factor de compresión: 0.704438 (256, 16)
Factor de compresión: 0.72855955 (512, 16)
Factor de compresión: 0.7677964 (1024, 16)
Factor de compresión: 0.80673045 (2048, 16)
Factor de compresión: 0.56419766 (64, 32)
Factor de compresión: 0.60779715 (128, 32)
Factor de compresión: 0.64332324 (256, 32)
Factor de compresión: 0.6817961 (512, 32)
Factor de compresión: 0.72209615 (1024, 32)
Factor de compresión: 0.7763114 (2048, 32)
Factor de compresión: 0.5646072 (128, 64)
Factor de compresión: 0.59900737 (256, 64)
Factor de compresión: 0.634058 (512, 64)
Factor de compresión: 0.67763793 (1024, 64)
Factor de compresión: 0.73800737 (2048, 64)
Factor de compresión: 0.56424314 (256, 128)
Factor de compresión: 0.60272086 (512, 128)
Factor de compresión: 0.65219414 (1024, 128)
Factor de compresión: 0.706001 (2048, 128)
Factor de compresión: 0.5766063 (512, 256)
Factor de compresión: 0.6193046 (1024, 256)
Factor de compresión: 0.6837941 (2048, 256)
Factor de compresión: 0.6013229 (1024, 512)
Factor de compresión: 0.672043 (2048, 512)
Factor de compresión: 0.6803382 (2048, 1024)
