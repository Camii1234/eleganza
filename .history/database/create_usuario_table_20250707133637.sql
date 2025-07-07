-- Script para crear la tabla usuario
-- Base de datos: bdboutique

CREATE DATABASE IF NOT EXISTS bdboutique;
USE bdboutique;

CREATE TABLE IF NOT EXISTS usuario (
    idUsuario INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(20) NOT NULL,
    calle VARCHAR(150) NOT NULL,
    ciudad VARCHAR(50) NOT NULL,
    codigoPostal VARCHAR(10) NOT NULL,
    pais VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    fechaRegistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índice para mejorar el rendimiento en búsquedas por correo
CREATE INDEX idx_usuario_correo ON usuario(correo);
