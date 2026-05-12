Pasos para levantar los contenedores:

1. Abrir una terminal y dirigete a la carpeta raiz utilizando el comando cd para mover entre directorios (de preferencia Git Bash).

2. Dirigirse a la carpeta "ContenedoresGrupoC", carpeta principal del proyecto. 

3. Ya dentro de esta carpeta, asegurarse de que exista el archivo de docker-compose y las carpetas de contenedores(correspondiente a cada microservicio) y dentro de cada una de estas, verificar la existencia de la carpeta target/ y  Dockerfile. Puedes confirmar el contenido de los archivos ls. 

4. Ahora se debe realizar el siguiente comando: docker-compose up -d --build

Lista de comandos de valor:

docker-compose down, elimina las instancias
docker-compose up -d --build, crea las instancias
docker-compose up -d, levanta las instancias ya creadas
docker ps, para ver que las instancias están correctamente levantadas

Ver logs de las instancias:

docker logs -f api-usuarios
docker logs -f api-geolocalizacion
docker logs -f api-mascotas
docker logs -f api-coincidencias


