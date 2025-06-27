
# Despliegue Dockerizado de la API

Este proyecto ha sido dockerizado utilizando `Dockerfile` y `docker-compose.yml`, permitiendo su despliegue rápido y replicable en cualquier entorno compatible con Docker.

---

## Estructura del Proyecto

La estructura mínima esperada es la siguiente:

```
.
├── docker-compose.yml
├── Dockerfile
└── target/
    └── api-bits-1.0.jar
```

> **Nota:** Se debe generar el archivo `.jar` antes de ejecutar Docker.

---

## Pasos para el Despliegue

### Construcción del `.jar`

Desde la raíz del proyecto, se ejecuta el siguiente comando para construir el archivo `.jar`:

```bash
./mvnw clean package
```

Este comando generará un archivo `.jar` dentro de la carpeta `target/`.

---

### Ejecución de los Contenedores

Una vez construido el `.jar`, se procede a levantar los servicios:

```bash
docker compose up --build
```

Este comando realiza lo siguiente:

- Levanta una base de datos PostgreSQL (`db`) configurada con:
  - **Nombre de base de datos:** `bits`
  - **Usuario:** `postgres`
  - **Contraseña:** `danilamasguapa`
- Espera que la base de datos esté en estado saludable (`healthcheck`)
- Construye la imagen de la aplicación y la levanta en un contenedor llamado `bits-app`
- Expone la API en `http://localhost:8080`

---

### Verificación del Funcionamiento

Para comprobar que el servicio está corriendo correctamente, se puede ingresar a:

- [http://localhost:8080](http://localhost:8080)  
- Si se utiliza Swagger: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

También se puede verificar con el siguiente comando:

```bash
docker ps
```

---

### Finalización del Servicio

Para detener y limpiar los contenedores:

```bash
docker compose down
```

Esto detendrá los servicios y eliminará los contenedores.  
> Los datos de la base de datos se conservarán gracias al volumen `db-data`.

---

## Notas Adicionales

- Se recomienda asegurarse de que **solo exista un `.jar` dentro del directorio `target/`** para evitar errores al construir la imagen.
