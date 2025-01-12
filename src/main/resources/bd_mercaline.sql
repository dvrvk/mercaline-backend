-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `mercaline_bd`
--

-- --------------------------------------------------------
--
-- BORRAR BASE DE DATOS ANTERIOR
--
-- DROP DATABASE IF EXISTS mercaline_bd;
--
-- CREAR DE NUEVO LA BASE DE DATOS
--
CREATE DATABASE IF NOT EXISTS mercaline_bd;
USE mercaline_bd;

--
-- BORRAR LAS TABLAS
--
-- Eliminar tablas si existen
DROP TABLE IF EXISTS favourites;
DROP TABLE IF EXISTS list_fav;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS status;
DROP TABLE IF EXISTS users;
--
-- Estructura de tabla para la tabla `users`
--
CREATE TABLE users (
    id BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(250) NOT NULL,
    name VARCHAR(100) NOT NULL,
    lastname VARCHAR(250) NOT NULL,
    email VARCHAR(250) UNIQUE NOT NULL,
    tel VARCHAR(15)
);

--
-- Estructura de tabla para la tabla `products`
--
CREATE TABLE categories (
    id BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100)
);

--
-- Estructura de tabla para la tabla `status`
--
CREATE TABLE status (
    id BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

--
-- Estructura de tabla para la tabla `products`
--
CREATE TABLE products (
    id BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    price DECIMAL(36,2) NOT NULL,
    category BIGINT(20) NOT NULL,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status BIGINT(20) NOT NULL,
    sold BIT(1) DEFAULT b'0',
    url_image VARCHAR(1000),
    cp VARCHAR(50) NOT NULL,
    user_id BIGINT(20) NOT NULL,
    FOREIGN KEY (category) REFERENCES categories(id),
    FOREIGN KEY (status) REFERENCES status(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE
);

--
-- Estructura de tabla para la tabla `list_fav`
--
CREATE TABLE list_fav (
    id BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    user_id BIGINT(20) NOT NULL,
    UNIQUE (name, user_id),
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE
);

--
-- Estructura de tabla para la tabla `favourites`
--
CREATE TABLE favourites (
    id BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    list_fav_id BIGINT(20) NOT NULL,
    product_id BIGINT(20) NOT NULL,
    FOREIGN KEY (list_fav_id) REFERENCES list_fav(id)
    ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id)
    ON DELETE CASCADE
);

--
-- Datos de tabla para la tabla `categories`
--
INSERT INTO categories (name) VALUES
('coches'),
('motos'),
('motos y accesorios'),
('moda y accesorios'),
('tecnología y electrónica'),
('deporte y ocio'),
('bicicletas'),
('hogar y jardín'),
('electrodomésticos'),
('cine, libros y música'),
('niños y bebés'),
('coleccionismo'),
('construcción y reformas'),
('industria y agricultura'),
('otros');

--
-- Datos de tabla para la tabla `status`
--
INSERT INTO status (name) VALUES
('sin abrir'),
('en su caja'),
('nuevo'),
('como nuevo'),
('en buen estado'),
('en condiciones aceptables'),
('lo ha dado todo');

--
-- Borrar datos de la tabla 'users'
--
-- TRUNCATE TABLE users;
--
--
-- Datos de prueba para la tabla 'users'
-- El password es 'password123'
--
INSERT INTO users (username, password, name, lastname, email, tel) VALUES
('jdoe', '$2a$12$VW1WfM65jy6k5EQ2CAJcZ.EUOcMUQAbTmYi59.5Nfru1mkoZnYWDq', 'John', 'Doe', 'johndoe@example.com', '123456789'),
('asmith', '$2a$12$VW1WfM65jy6k5EQ2CAJcZ.EUOcMUQAbTmYi59.5Nfru1mkoZnYWDq', 'Alice', 'Smith', 'alicesmith@example.com', '987654321'),
('bwhite', '$2a$12$VW1WfM65jy6k5EQ2CAJcZ.EUOcMUQAbTmYi59.5Nfru1mkoZnYWDq', 'Bob', 'White', 'bobwhite@example.com', '555123456'),
('cjohnson', '$2a$12$VW1WfM65jy6k5EQ2CAJcZ.EUOcMUQAbTmYi59.5Nfru1mkoZnYWDq', 'Charlie', 'Johnson', 'charliejohnson@example.com', '444123456');

--
-- Borrar datos de la tabla 'products'
--
-- TRUNCATE TABLE 'products';
--
-- Datos de prueba para la tabla 'products'
--
INSERT INTO products (name, description, price, category, status, url_image, cp, user_id, sold) VALUES
('Laptop', 'Laptop de última generación con 16GB de RAM y 512GB SSD', 1200.00, 5, 3, 'https://picsum.photos/id/1/200/300', '40.416775,-3.703790', 1, 0),
('Smartphone', 'Smartphone con pantalla de 6.5 pulgadas y cámara de 48MP', 800.00, 5, 3, 'https://picsum.photos/id/2/200/300', '40.416775,-3.703790', 2, 0),
('Bicicleta', 'Bicicleta de montaña con suspensión delantera y trasera', 400.00, 7, 4, 'https://picsum.photos/id/3/200/300', '40.416775,-3.703790', 3, 0),
('TV 4K', 'Televisor 4K de 55 pulgadas con HDR y Smart TV', 900.00, 5, 3, 'https://picsum.photos/id/4/200/300', '40.416775,-3.703790', 4, 0),
('Camiseta', 'Camiseta de algodón 100% con diseño gráfico', 20.00, 4, 6, 'https://picsum.photos/id/5/200/300', '40.416775,-3.703790', 1, 0),
('Sofá', 'Sofá de 3 plazas con tapizado en tela', 700.00, 8, 4, 'https://picsum.photos/id/6/200/300', '40.416775,-3.703790', 2, 1),
('Libro', 'Novela de ciencia ficción', 15.00, 10, 7, 'https://picsum.photos/id/7/200/300', '40.416775,-3.703790', 3, 0),
('Reproductor de música', 'Reproductor de música portátil con capacidad de 32GB', 100.00, 5, 3, 'https://picsum.photos/id/8/200/300', '40.416775,-3.703790', 4, 0),
('Cámara DSLR', 'Cámara réflex digital con lente de 18-55mm', 550.00, 5, 3, 'https://picsum.photos/id/9/200/300', '40.416775,-3.703790', 1, 0),
('Tablet', 'Tablet con pantalla de 10 pulgadas y 64GB de almacenamiento', 300.00, 5, 3, 'https://picsum.photos/id/10/200/300', '40.416775,-3.703790', 2, 1),
('Zapatillas deportivas', 'Zapatillas deportivas para correr de alta calidad', 120.00, 4, 4, 'https://picsum.photos/id/11/200/300', '40.416775,-3.703790', 3, 1),
('Lavadora', 'Lavadora con capacidad de 8kg y funciones avanzadas', 650.00, 8, 3, 'https://picsum.photos/id/12/200/300', '40.416775,-3.703790', 4, 0),
('Auriculares', 'Auriculares inalámbricos con cancelación de ruido', 200.00, 5, 3, 'https://picsum.photos/id/13/200/300', '40.416775,-3.703790', 1, 0),
('Reloj inteligente', 'Reloj inteligente con monitor de ritmo cardíaco y GPS', 250.00, 5, 3, 'https://picsum.photos/id/14/200/300', '40.416775,-3.703790', 2, 0),
('Mesa de comedor', 'Mesa de comedor para 6 personas, fabricada en madera de roble', 450.00, 8, 4, 'https://picsum.photos/id/15/200/300', '40.416775,-3.703790', 3, 0),
('Patinete eléctrico', 'Patinete eléctrico plegable con autonomía de 25km', 350.00, 7, 4, 'https://picsum.photos/id/16/200/300', '40.416775,-3.703790', 4, 1),
('Cámara de seguridad', 'Cámara de seguridad para el hogar con visión nocturna', 150.00, 5, 3, 'https://picsum.photos/id/17/200/300', '40.416775,-3.703790', 1, 0),
('Lámpara de pie', 'Lámpara de pie moderna con control táctil', 80.00, 8, 4, 'https://picsum.photos/id/18/200/300', '40.416775,-3.703790', 2, 0),
('Monitor', 'Monitor de 27 pulgadas con resolución 4K', 400.00, 5, 3, 'https://picsum.photos/id/19/200/300', '40.416775,-3.703790', 3, 1),
('Cafetera', 'Cafetera de espresso con molinillo integrado', 300.00, 8, 3, 'https://picsum.photos/id/20/200/300', '40.416775,-3.703790', 4, 0);


COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
