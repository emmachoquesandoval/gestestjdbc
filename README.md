# gestestjdbc

## Conexión con MySQL

La aplicación consulta la tabla `ALUMNOS` de la base `alumnos`. La tabla debe
tener las columnas `id`, `nombre` y `curso`.

En IntelliJ IDEA, abre la configuración de ejecución de Tomcat y agrega estas
variables en **Environment variables**:

```text
DB_USER=tu_usuario;DB_PASSWORD=tu_contraseña
```

La URL predeterminada es `jdbc:mysql://localhost:3306/alumnos?serverTimezone=UTC`.
Si tu MySQL usa otro host, puerto o nombre de base, configura también
`DB_URL`, por ejemplo:

```text
DB_URL=jdbc:mysql://localhost:3306/alumnos?serverTimezone=UTC;DB_USER=tu_usuario;DB_PASSWORD=tu_contraseña
```

El controlador usa el conector MySQL incluido en Maven. Si la conexión o la
consulta falla, el detalle queda en el log de Tomcat; revisa también que las
columnas coincidan con las indicadas arriba.
