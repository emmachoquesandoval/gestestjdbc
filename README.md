# gestestjdbc

## Español

### ¿Qué hace esta aplicación?

`gestestjdbc` es una pequeña aplicación web Java para consultar estudiantes almacenados en una base de datos MySQL y mostrarlos en una página HTML. La aplicación está construida con Maven, Jakarta Servlet y JDBC, y usa un servlet para ejecutar una consulta SQL y devolver la lista en formato HTML.

La lógica principal se encuentra en:

- `src/main/java/com/gestestjdbc/database/DatabaseConnection.java`: establece la conexión a MySQL.
- `src/main/java/com/gestestjdbc/servlets/AppController.java`: ejecuta la consulta `SELECT nombre, curso FROM alumnos ORDER BY id` y genera la respuesta HTML.
- `src/main/webapp/index.html`: página inicial con un botón para consultar los alumnos.

La aplicación no crea ni modifica registros; solo realiza una lectura desde la base de datos para mostrarlos en una vista sencilla.

### Arquitectura general

```mermaid
flowchart LR
    A[Usuario] --> B[index.html]
    B --> C[form action="controlador"]
    C --> D[AppController.java]
    D --> E[DatabaseConnection.java]
    E --> F[(MySQL / alumnos)]
    F --> D
    D --> G[HTML con lista de alumnos]
    G --> A
```

### Requisitos previos

- Java 21
- Maven
- MySQL Server
- Tomcat 10 o superior (o cualquier contenedor compatible con Jakarta Servlet 6)

### Base de datos requerida

La aplicación espera una base de datos llamada `alumnos` y una tabla llamada `alumnos` con al menos estas columnas:

```sql
CREATE DATABASE alumnos;
USE alumnos;

CREATE TABLE alumnos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    curso VARCHAR(50) NOT NULL
);

INSERT INTO alumnos (nombre, curso) VALUES
('Ana', 'DAW2'),
('Luis', 'DAM2'),
('Marta', 'SMR2');
```

> Importante: el código actual usa el nombre `alumnos` exactamente en la consulta SQL (`SELECT nombre, curso FROM alumnos ORDER BY id`). Si tu entorno usa otra convención de mayúsculas/minúsculas, ajusta la tabla y la configuración del acceso a la base de datos.

### Configuración de conexión

El archivo `DatabaseConnection.java` incluye la conexión por defecto:

```java
private static final String DEFAULT_URL =
    "jdbc:mysql://10.127.168.128:3306/alumnos?serverTimezone=UTC";

private static final String USER = "daw2";
private static final String PASSWORD = "secreto";
```

Si tu MySQL está en otra máquina, puerto o credenciales distintas, edita ese archivo antes de compilar y desplegar la aplicación.

### Cómo ejecutarlo

#### 1) Clonar el proyecto

```bash
git clone https://github.com/emmachoquesandoval/gestestjdbc.git
cd gestestjdbc
```

#### 2) Compilar el proyecto

```bash
mvn clean package
```

Esto generará el artefacto WAR en la carpeta `target`:

```text
target/gestestjdbc.war
```

#### 3) Desplegar en Tomcat

- Copia el archivo `target/gestestjdbc.war` en la carpeta `webapps` de Tomcat.
- Inicia Tomcat.
- La aplicación quedará disponible en una URL similar a:

```text
http://localhost:8080/gestestjdbc/
```

#### 4) Probar la aplicación

- Abre la URL anterior.
- Haz clic en el botón "Consultar alumnos".
- La aplicación ejecuta el servlet `/controlador` y muestra la lista de estudiantes.

---

## English

### What does this application do?

`gestestjdbc` is a small Java web application that reads student data from a MySQL database and displays it on an HTML page. It is built with Maven, Jakarta Servlet, and JDBC, and it uses a servlet to run a SQL query and return the result as HTML.

The main logic is in:

- `src/main/java/com/gestestjdbc/database/DatabaseConnection.java`: creates the connection to MySQL.
- `src/main/java/com/gestestjdbc/servlets/AppController.java`: runs the query `SELECT nombre, curso FROM alumnos ORDER BY id` and generates the page output.
- `src/main/webapp/index.html`: initial page with a button to query the students.

This project is a read-only application; it does not insert, update, or delete records.

### High-level architecture

```mermaid
flowchart LR
    A[User] --> B[index.html]
    B --> C[form action="controlador"]
    C --> D[AppController.java]
    D --> E[DatabaseConnection.java]
    E --> F[(MySQL / alumnos)]
    F --> D
    D --> G[HTML with student list]
    G --> A
```

### Prerequisites

- Java 21
- Maven
- MySQL Server
- Tomcat 10 or newer (or any container compatible with Jakarta Servlet 6)

### Required database

The app expects a database named `alumnos` and a table named `alumnos` with at least these columns:

```sql
CREATE DATABASE alumnos;
USE alumnos;

CREATE TABLE alumnos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    curso VARCHAR(50) NOT NULL
);

INSERT INTO alumnos (nombre, curso) VALUES
('Ana', 'DAW2'),
('Luis', 'DAM2'),
('Marta', 'SMR2');
```

> Note: the current code queries `alumnos` exactly as written in the SQL statement. If your environment uses a different schema naming convention, adjust the table name and the database configuration accordingly.

### Database configuration

`DatabaseConnection.java` currently contains the default connection values:

```java
private static final String DEFAULT_URL =
    "jdbc:mysql://10.127.168.128:3306/alumnos?serverTimezone=UTC";

private static final String USER = "daw2";
private static final String PASSWORD = "secreto";
```

If your MySQL instance is running on another host, port, or uses different credentials, edit that file before compiling or deploying.

### How to run it

#### 1) Clone the repository

```bash
git clone https://github.com/emmachoquesandoval/gestestjdbc.git
cd gestestjdbc
```

#### 2) Build the project

```bash
mvn clean package
```

This will produce a WAR artifact in the `target` folder:

```text
target/gestestjdbc.war
```

#### 3) Deploy to Tomcat

- Copy `target/gestestjdbc.war` into Tomcat's `webapps` directory.
- Start Tomcat.
- Access the app at a URL like:

```text
http://localhost:8080/gestestjdbc/
```

#### 4) Test the application

- Open the app URL.
- Click the button "Consultar alumnos".
- The servlet `/controlador` will run and display the student list.

---

## Resumen rápido

- Es una aplicación web Java + MySQL.
- Consulta la tabla `alumnos` y muestra los nombres y cursos.
- Requiere MySQL, Java 21 y Tomcat.
- La aplicación se despliega como un archivo `.war` y se accede desde la ruta base del proyecto.

## Troubleshooting

Si la aplicación falla al cargar:

1. Verifica que MySQL esté levantado y accesible.
2. Comprueba que la base de datos `alumnos` y la tabla `alumnos` existan.
3. Confirma que la URL, usuario y contraseña en `DatabaseConnection.java` coinciden con tu entorno.
4. Revisa los logs de Tomcat para ver el detalle exacto de la excepción SQL.
5. Si el problema persiste, asegúrate de que `mvn clean package` se ejecute correctamente antes del despliegue.

## Licencia

Este proyecto se entrega como ejemplo académico de conexión a bases de datos desde Java web con Servlet y JDBC.
