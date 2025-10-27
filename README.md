# API Sistema de Roles - IntegraLearn

Esta API ha sido adaptada para trabajar con la base de datos `sistema_roles` que incluye las tablas Persona, Estudiante y Docente.

## Configuración de la Base de Datos

1. **Crear la base de datos:**
   ```sql
   CREATE DATABASE sistema_roles;
   USE sistema_roles;
   ```

2. **Ejecutar el script de configuración:**
   ```bash
   mysql -u root -p sistema_roles < database_setup.sql
   ```

## Estructura de la Base de Datos

### Tablas Principales:
- **Persona**: Información básica de usuarios (gmail, nombre, apellido, contraseña)
- **Estudiante**: Información específica de estudiantes (semestre, progreso)
- **Docente**: Información específica de docentes (cargo)
- **users**: Tabla wrapper para compatibilidad con autenticación
- **roles**: Roles del sistema (ROLE_ESTUDIANTE, ROLE_DOCENTE)

### Relaciones:
- Una Persona puede ser un Estudiante O un Docente (relación 1:1)
- Un User está asociado a una Persona (relación 1:1)
- Un User puede tener múltiples Roles (relación N:M)

## Endpoints de la API

### Autenticación
- `POST /api/auth/register` - Registrar nuevo usuario
- `POST /api/auth/login` - Iniciar sesión
- `GET /api/auth/me` - Obtener información del usuario actual

### Personas
- `GET /api/personas` - Listar todas las personas
- `GET /api/personas/{id}` - Obtener persona por ID
- `GET /api/personas/gmail/{gmail}` - Obtener persona por email
- `POST /api/personas` - Crear nueva persona
- `PUT /api/personas/{id}` - Actualizar persona
- `DELETE /api/personas/{id}` - Eliminar persona

### Estudiantes
- `GET /api/estudiantes` - Listar todos los estudiantes
- `GET /api/estudiantes/{id}` - Obtener estudiante por ID
- `GET /api/estudiantes/semestre/{semestre}` - Estudiantes por semestre
- `GET /api/estudiantes/progreso/{progreso}` - Estudiantes con progreso mayor a X (usar BigDecimal)
- `POST /api/estudiantes` - Crear nuevo estudiante
- `PUT /api/estudiantes/{id}` - Actualizar estudiante
- `PUT /api/estudiantes/{id}/progreso` - Actualizar solo progreso (usar BigDecimal)
- `DELETE /api/estudiantes/{id}` - Eliminar estudiante

### Docentes
- `GET /api/docentes` - Listar todos los docentes
- `GET /api/docentes/{id}` - Obtener docente por ID
- `GET /api/docentes/cargo/{cargo}` - Docentes por cargo
- `POST /api/docentes` - Crear nuevo docente
- `PUT /api/docentes/{id}` - Actualizar docente
- `DELETE /api/docentes/{id}` - Eliminar docente

## Ejemplo de Registro

```json
POST /api/auth/register
{
    "email": "estudiante@ejemplo.com",
    "username": "estudiante@ejemplo.com",
    "password": "password123",
    "type": "ESTUDIANTE",
    "semester": 3,
    "firstName": "Juan",
    "lastName": "Pérez"
}
```

## Ejemplo de Actualización de Progreso

```json
PUT /api/estudiantes/1/progreso
75.50
```

## Notas Importantes

- **BigDecimal**: El campo `progreso` en la entidad Estudiante usa `BigDecimal` para mayor precisión con decimales
- **Hibernate 6**: Compatible con las nuevas restricciones de tipos de datos de Hibernate 6
- **Precisión**: El campo progreso tiene precisión DECIMAL(5,2) en la base de datos

## Configuración

La aplicación está configurada para conectarse a:
- **Base de datos**: `sistema_roles`
- **Puerto**: `8080`
- **Usuario**: `root`
- **Contraseña**: (vacía por defecto)

Puedes modificar estos valores en `src/main/resources/application.properties`.

## Compilación y Ejecución

```bash
# Compilar
mvn clean compile

# Ejecutar
mvn spring-boot:run
```

La API estará disponible en `http://localhost:8080`
