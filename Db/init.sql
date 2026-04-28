-- Crear las bases de datos para cada microservicio
CREATE DATABASE IF NOT EXISTS usuariosdb;
CREATE DATABASE IF NOT EXISTS mascotasdb;
CREATE DATABASE IF NOT EXISTS geodb;

-- Otorgar permisos al usuario root para que los microservicios puedan conectarse (opcional según configuración)
GRANT ALL PRIVILEGES ON usuariosdb.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON mascotasdb.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON geodb.* TO 'root'@'%';
FLUSH PRIVILEGES;

-- Dejamos las tablas vacías. 
-- Spring Boot (Hibernate) se encargará de crear las tablas 'usuarios' y 'organizaciones' automáticamente.