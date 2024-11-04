# Crear la Base de Datos `mercaline_bd`

Este proyecto contiene el script para la creación de la base de datos `mercaline_bd`, diseñada para gestionar productos, usuarios, categorías y listas de favoritos. La base de datos está configurada para funcionar con **MariaDB** (versión 10.4.28 o superior) y **PHP** (versión 8.2.4 o superior).

## Requisitos previos

Antes de comenzar, asegúrate de tener instalados los siguientes componentes en tu entorno:

- **MariaDB**: Versión 10.4.28 o superior.
- **PHP**: Versión 8.2.4 o superior.
- Cliente de base de datos, como **phpMyAdmin**, **HeidiSQL**, o la línea de comandos de **MySQL/MariaDB**.

## Pasos para crear la base de datos

### 1. Descargar el script SQL

El archivo que contiene el script SQL se encuentra en este repositorio, bajo el nombre `mercaline_bd.sql`. Este script incluye:

- La creación de la base de datos `mercaline_bd`.
- La creación de las tablas `users`, `categories`, `status`, `products`, `list_fav`, y `favourites`.
- La inserción de datos iniciales en las tablas `categories` y `status`.

### 2. Acceder al servidor de base de datos

Puedes acceder al servidor de base de datos utilizando uno de los siguientes métodos:

- **phpMyAdmin**: Ingresa a la interfaz web y selecciona la opción "SQL" para ejecutar comandos.
- **HeidiSQL**: Conéctate al servidor y abre una nueva pestaña de consulta SQL.
- **Línea de comandos**: Utiliza el comando `mysql` o `mariadb` para acceder a tu servidor de base de datos:

```bash
mysql -u tu_usuario -p
```

### 3. Crear la base de datos y las tablas

#### Usando phpMyAdmin o HeidiSQL:

1. Abre la opción para ejecutar una consulta SQL.
2. Copia el contenido del archivo `mercaline_bd.sql` o selecciona la opción **Importar** y carga el archivo directamente.
3. Ejecuta el script.

#### Usando la línea de comandos:

1. Después de conectarte a tu servidor de base de datos, usa el siguiente comando para importar el script SQL:

    ```bash
    SOURCE ruta_al_archivo/mercaline_bd.sql;
    ```

2. Asegúrate de que la ruta del archivo `mercaline_bd.sql` sea correcta. Esto ejecutará todo el script, creando la base de datos, las tablas y añadiendo los datos iniciales.

### 4. Verificar la creación de la base de datos

#### Desde phpMyAdmin o HeidiSQL:

- Verifica que la base de datos `mercaline_bd` haya sido creada correctamente navegando por la lista de bases de datos.
- Dentro de `mercaline_bd`, deberías poder ver las tablas `users`, `categories`, `status`, `products`, `list_fav`, y `favourites`.

Para ver las tablas puedes ejecutar la siguiente consulta:

```sql
SHOW TABLES;
```
#### Desde la línea de comandos:

1. Selecciona la base de datos:

    ```bash
    USE mercaline_bd;
    ```

2. Muestra las tablas creadas:

    ```sql
    SHOW TABLES;
    ```

Deberías ver la lista de las tablas mencionadas anteriormente.

### 5. Verificar los datos insertados

Para asegurarte de que los datos iniciales se han insertado correctamente en las tablas `categories` y `status`, puedes ejecutar las siguientes consultas:

#### Verificar categorías:

```sql
SELECT * FROM categories;
````
#### Verificar estados:
```sql
SELECT * FROM status;
````

