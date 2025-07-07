-- Verificar la estructura actual de la tabla usuario
DESCRIBE usuario;

-- Verificar si hay restricciones o índices únicos
SHOW INDEX FROM usuario;

-- Verificar si hay algún trigger
SHOW TRIGGERS LIKE 'usuario';

-- Probar inserción manual
INSERT INTO usuario (idUsuario, nombre, correo, telefono, calle, ciudad, codigoPostal, pais, password) 
VALUES (12345, 'Test Manual', 'test@manual.com', '123456789', 'Calle Test', 'Ciudad Test', '12345', 'Colombia', 'password123');

-- Verificar la inserción
SELECT * FROM usuario WHERE idUsuario = 12345;

-- Eliminar el registro de prueba
DELETE FROM usuario WHERE idUsuario = 12345;
