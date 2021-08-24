# Proyecto - Mutantes
Proyecto para mercado libre - Mutantes; creada con SpringBoot, MYSQL, JPA. Creado por **Angy Natalia Muñoz Bolaños.**

# Ajustar la BD
Para ajustar los datos de la BD (Usuario, Contraseña y Url de la Base de datos), se debe modificar el archivo application.properties

# Levantar los servicios
Para levantar los servicios, se ejecuta el archivo MutantesApplication.java

# Generar JAR
Para generar el JAR es necesario correr en la carpeta raiz del proyecto, el comando **mvn clean install**. De esta forma se generara en la carpeta **DirectorioRaizProyecto/Target/**
 el JAR correspondiente a la aplicacion para cargar al servidor.

# Endpoints (Proyecto local)
GET: http://localhost:8080/mercadolibre/stats
POST: http://localhost:8080/mercadolibre/mutant
