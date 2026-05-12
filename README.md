Pasos para levantar los contenedores:

1. Dirigirse a la carpeta del proyecto, donde se encuentran las carpetas de los componentes backend, frontend y bd (también donde está el docker-compose)

2. Ya dentro de esta carpeta, se debe abrir Git Bash.

3. Ahora se debe realizar el siguiente comando: docker-compose up -d --build

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
