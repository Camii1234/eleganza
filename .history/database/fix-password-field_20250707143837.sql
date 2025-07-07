-- Script para ampliar el campo password en la tabla usuario
-- El campo actual es varchar(15) pero SHA-256 genera 64 caracteres

USE bdboutique;

-- Ampliar el campo password para soportar hashes SHA-256
ALTER TABLE usuario MODIFY COLUMN password VARCHAR(255);

-- Verificar el cambio
DESCRIBE usuario;
