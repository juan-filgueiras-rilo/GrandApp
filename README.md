# GrandApp

Backend

Para arrancar el backend, descargar el módulo GrandServer del proyecto.
A continuación tenemos que cambiar la url a la que apunta el proyecto Android, para esto en la clase “GrandServer.kt” hay que cambiar la IP de la variable “url” por la de la máquina en la que tengamos levantado el backend.
## Requirements

- Java SE 11+.
- Maven 3+.
- MySQL 8+. (opcional)

## Database creation

```
Start Mysql server if not running (e.g. mysqld).

mysqladmin -u root create granduser

mysql -u root
    CREATE USER 'granduser'@'localhost' IDENTIFIED BY 'granduser';
    GRANT ALL PRIVILEGES ON paproject.* to 'granduser'@'localhost' WITH GRANT OPTION;
    exit
```

## Run

```
cd backend
mvn sql:execute (only first time to create tables)
mvn spring-boot:run [-P mysql] (Si no se ejecuta con esta flag, levantará la BD en memoria con H2
```
