-- Reference MySQL Schema for NASAProject
-- Adjust engine/charset/constraints as needed for your MySQL 8+ instance.

CREATE DATABASE IF NOT EXISTS nasa CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE nasa;

CREATE TABLE IF NOT EXISTS tipoUsuario (
    cveUsuario VARCHAR(10) PRIMARY KEY,
    NomTipoUsuario VARCHAR(50) NOT NULL
);

INSERT IGNORE INTO tipoUsuario (cveUsuario, NomTipoUsuario) VALUES
    ('ADM', 'Administrador'),
    ('NRM', 'Normal'),
    ('INV', 'Invitado');

CREATE TABLE IF NOT EXISTS registro (
    email VARCHAR(150) PRIMARY KEY,
    contraseña VARCHAR(255) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    fechaRegistro DATE,
    rol VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS usuarios (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    cveUsuario VARCHAR(10) DEFAULT 'NRM',
    Email VARCHAR(150) NOT NULL,
    NombreUsuario VARCHAR(100),
    Apellidos VARCHAR(100),
    FechaNac VARCHAR(20),
    Nacionalidad VARCHAR(80),
    FOREIGN KEY (cveUsuario) REFERENCES tipoUsuario(cveUsuario),
    FOREIGN KEY (Email) REFERENCES registro(email) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS accesos (
    idAcceso INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(150) NOT NULL,
    fechaAcceso DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS historialbusqueda (
    idHistorial INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(150) NOT NULL,
    busqueda VARCHAR(255) NOT NULL,
    fechaBusqueda DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS busquedasnasa (
    idBusqueda INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS comentariosapod (
    idComentario INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(150) NOT NULL,
    fechaComentario DATETIME NOT NULL,
    fecha DATE NOT NULL,
    comentario TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS imagenes (
    idImagen INT AUTO_INCREMENT PRIMARY KEY,
    nombreImagen VARCHAR(255) NOT NULL,
    imagen LONGBLOB,
    Url VARCHAR(500) NOT NULL
);

CREATE TABLE IF NOT EXISTS apod (
    idApod INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descripcion TEXT,
    tipo VARCHAR(50),
    url VARCHAR(500) NOT NULL,
    fecha DATE
);

CREATE TABLE IF NOT EXISTS apis (
    idApi INT PRIMARY KEY,
    Api VARCHAR(255) NOT NULL,
    TipoApi VARCHAR(20) NOT NULL
);

INSERT IGNORE INTO apis (idApi, Api, TipoApi) VALUES
    (1, 'https://api.nasa.gov/planetary/apod?', 'APOD'),
    (2, 'https://images-api.nasa.gov/search?', 'LIBRY'),
    (3, 'https://api.nasa.gov/EPIC/api/natural?', 'EPIC');

CREATE TABLE IF NOT EXISTS apiKey (
    idApiKey INT PRIMARY KEY,
    ApiKey VARCHAR(255) NOT NULL,
    FechaCreacion DATE,
    FechaVencimiento DATE
);
