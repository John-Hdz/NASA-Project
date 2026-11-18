# NASAProject — JavaFX Desktop Client

Aplicación de escritorio desarrollada en **Java + JavaFX** que consume distintas APIs públicas de la NASA, con gestión de usuarios, roles, historial de búsquedas, comentarios y exportación de reportes en PDF.

## Funcionalidades principales

- **Autenticación y roles**: Registro e inicio de sesión con contraseñas cifradas mediante **BCrypt**, además de modo invitado (`Guest Mode`) y permisos diferenciados para administradores (`ADM`).
- **APOD (Astronomy Picture of the Day)**: Consulta de la imagen/descripción astronómica del día, descarga local y registro en base de datos.
- **NASA Image and Video Library**: Búsqueda de imágenes, videos y audios del archivo de la NASA con vista de detalles y descarga.
- **EPIC (Earth Polychromatic Imaging Camera)**: Visualización de fotografías de la Tierra tomadas por el satélite DSCOVR.
- **Panel de administración y reportes**: Gestión de usuarios, historial global de búsquedas, moderación de comentarios, configuración de APIs y generación de reportes PDF con **iText 7**.

## Tecnologías utilizadas

- **Java 21+** / **JavaFX 21**
- **Gradle** (Wrapper incluido)
- **MySQL 8** (`mysql-connector-j`)
- **jBCrypt** para hashing de contraseñas
- **Gson** y **Apache HttpClient** para consumo de APIs REST
- **iText 7**, **ControlsFX**, **BootstrapFX**, **JMetro**, **Ikonli**

## Requisitos previos

1. **JDK 21** o superior instalado.
2. **MySQL Server** en ejecución.
3. Una API Key gratuita de la NASA (puedes solicitarla en [api.nasa.gov](https://api.nasa.gov/) o usar `DEMO_KEY` para pruebas limitadas).

## Configuración

1. **Base de datos**: Ejecuta el script de referencia [database/schema.sql](database/schema.sql) en tu instancia de MySQL para crear las tablas necesarias.
2. **Variables de entorno**: Copia el archivo de ejemplo `.env.example` a `.env` en la raíz del proyecto y completa tus credenciales:

   ```bash
   cp .env.example .env
   ```

   Variables disponibles en `.env`:
   - `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`
   - `NASA_API_KEY`
   - `NASA_APOD_URL`, `NASA_LIBRARY_URL`, `NASA_EPIC_URL`

> **Nota:** El archivo `.env` está incluido en `.gitignore` para evitar exponer credenciales o claves privadas en el repositorio.

## Ejecución

En Windows:

```powershell
.\gradlew.bat run
```

En Linux / macOS:

```bash
./gradlew run
```
