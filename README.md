
<div align="center">
  <img src="https://capsule-render.vercel.app/api?type=rect&color=0:0b0f19,50:162447,100:1f4068&height=170&section=header&text=NASA%20APIs%20Desktop%20Client&fontSize=36&fontColor=ffffff&fontAlignY=42&desc=Aplicaci%C3%B3n%20de%20Escritorio%20en%20JavaFX%20%C2%B7%20Consumo%20REST%20y%20Persistencia%20MySQL&descSize=15&descAlignY=66" width="100%" alt="NASA APIs Desktop Client Banner" />

  <br/>

  ![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
  ![JavaFX](https://img.shields.io/badge/JavaFX-GUI-007396?style=for-the-badge&logo=java&logoColor=white)
  ![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
  ![Gson](https://img.shields.io/badge/Google_Gson-JSON-4285F4?style=for-the-badge&logo=google&logoColor=white)
  ![iText](https://img.shields.io/badge/iText-PDF_Reports-D32F2F?style=for-the-badge&logo=adobeacrobatreader&logoColor=white)
  ![BCrypt](https://img.shields.io/badge/BCrypt-Security-2E7D32?style=for-the-badge&logo=letsencrypt&logoColor=white)
</div>

---

## Descripción General

**NASA APIs Desktop Client** es una aplicación de escritorio desarrollada en **Java** y **JavaFX** que centraliza la consulta, visualización, descarga local y generación de reportes de servicios web oficiales de la **NASA**, respaldada por una base de datos relacional en **MySQL**.

El sistema implementa control de acceso por roles (**Administrador**, **Usuario Registrado** y **Modo Invitado**), gestión dinámica de credenciales y endpoints de API desde base de datos, concurrencia mediante hilos para evitar bloqueos en la interfaz gráfica y exportación automatizada de reportes en PDF.

---

## Servicios Integrados (NASA Open APIs)

| Servicio | Endpoint Base | Funcionalidades Principales |
| :--- | :--- | :--- |
| **APOD** *(Astronomy Picture of the Day)* | `api.nasa.gov/planetary/apod` | Consulta de la imagen astronómica diaria con metadatos, descarga local, almacenamiento binario en MySQL, sección de comentarios y exportación de reportes en PDF. |
| **NASA Image and Video Library** | `images-api.nasa.gov/search` | Buscador multimedia con filtros por tipo de recurso (imágenes, audio y video), vista previa en cuadrícula dinámica (`FlowPane`), descarga local y registro de historial por usuario. |
| **EPIC** *(Earth Polychromatic Imaging Camera)* | `api.nasa.gov/EPIC/api/natural` | Visualización de fotografías terrestres capturadas por la cámara DSCOVR con navegación secuencial, coordenadas del centroide, posiciones J2000 (Sol, Luna, DSCOVR) y cuaterniones de actitud. |

---

## Arquitectura y Características Técnicas

* **Patrón DAO (Data Access Object):** Abstracción completa de la capa de persistencia mediante la interfaz genérica `Dao<T>` e implementaciones dedicadas (`UsuarioDao`, `ApodDao`, `APIDAO`, `ComentarioDao`, `historialDao`) sobre JDBC.
* **Patrón Singleton:** Gestión centralizada y sincronizada de la sesión activa (`UsuarioActual`) para controlar permisos y visibilidad de módulos en toda la aplicación.
* **Concurrencia en JavaFX:** Ejecución de peticiones HTTP, deserialización JSON con **Gson** y carga de vistas FXML en subprocesos independientes (`Thread` + `Platform.runLater()`) para mantener la fluidez de la interfaz.
* **Seguridad y Control de Roles:** Cifrado de contraseñas con **BCrypt** (`PasswordUtil`), auditoría de accesos y separación de vistas entre usuarios estándar, invitados y administradores.
* **Reportería Automatizada:** Generación de documentos PDF en formato horizontal (`A3`) con imágenes incrustadas y metadatos obtenidos desde MySQL utilizando la librería **iText**.

---

## Estructura del Proyecto

```text
NASAProject/
├── src/main/java/
│   ├── baseDatos/            # Conexión JDBC (MySQLConnection), interfaz Dao<T> y clases DAO
│   ├── datosCompartidos/     # Utilidades globales: PasswordUtil (BCrypt), RepoteApod (iText), UsuarioActual
│   ├── Modelos/              # Mapeo JSON con Gson para APOD, EPIC y NASA Image & Video Library
│   ├── models/               # Entidades relacionales de la base de datos MySQL
│   └── controllers/          # Controladores FXML (Login, Home, APOD, Library, EPIC, Admin)
├── src/main/resources/
│   ├── fxml/                 # Vistas de interfaz gráfica JavaFX
│   └── styles/               # Hojas de estilo CSS personalizadas
└── database/
    └── schema.sql            # Script de creación de tablas relacionales en MySQL

```

---

## Esquema de Base de Datos (MySQL)

| Tabla | Propósito |
| --- | --- |
| `usuarios` / `tipousuario` / `registro` | Gestión de perfiles, credenciales hasheadas y asignación de roles (`ADM`, `NRM`, `INV`). |
| `apis` / `apikey` | Configuración dinámica de URLs base y claves de autenticación de la NASA con fechas de vigencia. |
| `apod` / `imagenes` | Almacenamiento en caché de metadatos e imágenes descargadas en formato `LONGBLOB`. |
| `comentariosapod` | Registro de reseñas y comentarios de usuarios asociados a cada fecha de APOD. |
| `busquedasnasa` / `historialbusqueda` / `accesos` | Auditoría de inicios de sesión e historial de consultas realizadas por cada usuario. |

---

## Instalación y Configuración

1. **Clonar el repositorio:**
```bash
git clone https://github.com/John-Hdz/NASA-Project.git
cd nasa-apis-javafx

```


2. **Configurar la base de datos:**
* Crear una base de datos en **MySQL** e importar el script SQL incluido en el proyecto.
* Actualizar las credenciales de conexión (`hostname`, `dbname`, `dbport`, `dbuser`, `dbpass`) en la clase `MySQLConnection`.


3. **Configurar la API Key de la NASA:**
* Obtener una clave gratuita en [api.nasa.gov](https://api.nasa.gov/).
* Registrar la clave en la tabla `apikey` o actualizarla directamente desde el panel **API's Configuration** iniciando sesión con un usuario administrador.


4. **Compilar y ejecutar:**
```bash
mvn clean javafx:run

```


---

## Autoría

Desarrollado en el **Tecnológico Nacional de México en Celaya** (Ingeniería en Sistemas Computacionales — Tópicos Avanzados de Programación):

* **Juan José Hernández Pérez**
* Jacqueline Jinez Matehuala
* Diego Ramírez Vázquez
* Gadiel Karim Ríos Rojas
