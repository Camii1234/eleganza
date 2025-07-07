-- Script para verificar la estructura de la tabla producto
-- Ejecutar en MySQL para verificar que la tabla tenga todos los campos necesarios

-- Verificar estructura de la tabla
DESCRIBE producto;

-- Si la tabla no existe, crearla con esta estructura:
CREATE TABLE IF NOT EXISTS producto (
    idProducto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DOUBLE NOT NULL,
    talla VARCHAR(50),
    categoria VARCHAR(50) NOT NULL,
    stock INT DEFAULT 0,
    estado VARCHAR(20) DEFAULT 'Activo',
    imagen LONGBLOB,
    descripcion TEXT
);

-- Verificar si hay datos en la tabla
SELECT COUNT(*) as total_productos FROM producto;

-- Mostrar algunos productos existentes
SELECT idProducto, nombre, precio, talla, categoria, stock, estado, 
       LENGTH(imagen) as imagen_size, 
       SUBSTRING(descripcion, 1, 50) as descripcion_preview 
FROM producto 
ORDER BY idProducto DESC 
LIMIT 5;
