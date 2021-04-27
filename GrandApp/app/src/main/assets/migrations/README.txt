La ruta assets migrations existe porque en ocasiones necesitamos alterar el diseño de la SQLite. Por ejemplo, añadir columnas nuevas a tablas
ya existentes en versiones anteriores.

Dada la librería de base de datos que usamos, ActiveAndroid, para modificar una tabla ya existente debemos seguir los siguiente pasos:

1. En el manifest subir la versión de la BD (AA_DB_VERSION)
2. En la carpeta assets/migrations colocar un NUMERO_NUEVA_VERSION.sql con las instrucciones SQL a lanzar.


Schema migrations


Whenever your schema changes you need to increment the database version number, either through Configuration or AA_DB_VERSION meta-data. 
If new classes are added, ActiveAndroid will automatically add them to the database. 
If you want to change something in an existing table however (e.g. add a column or rename a table), this is done using sql-scripts named 
<NewVersion>.sql, where NewVersion is the AA_DB_VERSION, in assets/migrations.

ActiveAndroid will execute a script if its filename is greater than the old database-version and smaller or equal to the new version.

Let's assume you added a column color to the Items table. You now need to increase AA_DB_VERSION to 2 and provide a script 2.sql.

ALTER TABLE Items ADD COLUMN color INTEGER;


/*CRUD OPERATIONS*/
https://guides.codepath.com/android/activeandroid-guide