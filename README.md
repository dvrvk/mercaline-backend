# 🚀 Proyecto Mercaline: Guía de Construcción y Despliegue con Docker

Este proyecto está diseñado para ser ejecutado con contenedores Docker, incluyendo servicios para el **frontend**, **backend**, y la **base de datos**. A continuación, se proporciona una guía paso a paso para construir y desplegar la aplicación.

---

## 📂 Estructura del Proyecto

El proyecto debe organizarse de la siguiente manera:

![image](https://github.com/user-attachments/assets/a9a84bc1-83b9-4696-9256-6e395420116a)

- **`docker-compose.yml`**: Archivo principal para orquestar los contenedores.
- **`prueba_frond/angular-front/Dockerfile`**: Dockerfile para el frontend Angular.
- **`mercaline/mercaline/Dockerfile`**: Dockerfile para el backend Spring Boot.
- **`sql/init.sql`**: Script SQL para inicializar la base de datos MySQL.

---

## 📜 Requisitos Previos

1. **Docker y Docker Compose** instalados en tu máquina.
   - [Instalar Docker](https://docs.docker.com/get-docker/)
   - [Instalar Docker Compose](https://docs.docker.com/compose/install/)

2. Asegúrate de que los proyectos de **frontend** y **backend** están configurados correctamente:
   - El frontend debe estar ubicado en `prueba_frond/angular-front`.
   - El backend debe estar empaquetado como un archivo JAR en `mercaline/mercaline/target`.

---

## 🛠️ Instrucciones para Construir y Desplegar

### 1️⃣ Copiar los Archivos
- Asegúrate de copiar el archivo `docker-compose.yml` en la raíz del proyecto junto con las carpetas `prueba_frond`, `mercaline`, y `sql`.

### 2️⃣ Construcción de Contenedores
En la terminal, navega hasta la raíz del proyecto y ejecuta:

```bash
docker-compose build
````
Este comando construirá las imágenes de Docker para los servicios frontend, backend, y MySQL.

### 3️⃣ Inicio de los Servicios
Una vez construidas las imágenes, inicia los contenedores con:

```bash
docker-compose up
```
Esto iniciará todos los servicios y los conectará en la red definida.

### 4️⃣ Acceso a los Servicios
- **Frontend:** Accede al frontend desde tu navegador en [http://localhost](http://localhost).
- **Backend:** La API REST estará disponible en [http://localhost:8080](http://localhost:8080).
- **Base de Datos:** La base de datos MySQL estará accesible en el puerto `3306`.

### 5️⃣🧹 Detener y Limpiar los Contenedores
Para detener los contenedores, utiliza:

```bash
docker-compose down
```
Este comando también eliminará las redes creadas, pero mantendrá los volúmenes de datos.


### ⚙️ Personalización
- **Cambiar Puertos:** Puedes modificar los puertos expuestos en `docker-compose.yml` si es necesario.
- **Actualizar el Script SQL:** Edita `sql/init.sql` para añadir datos iniciales o cambiar la estructura de la base de datos.

---

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

