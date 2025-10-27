# Guía de Solución de Problemas - Sistema de Roles

## Problema Identificado
Los usuarios no se guardaban en la base de datos al registrar nuevos usuarios.

## Cambios Realizados

### ✅ 1. Configuración `application.properties` Actualizada

**Ubicación:** `api/src/main/resources/application.properties`

**Cambios realizados:**
- Cambiado `spring.jpa.hibernate.ddl-auto=none` → `spring.jpa.hibernate.ddl-auto=update`
- Añadido logging SQL para debugging:
  ```properties
  logging.level.org.hibernate.SQL=DEBUG
  logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
  logging.level.org.springframework.jdbc=DEBUG
  ```

**Razón:** Con `ddl-auto=none`, Hibernate no crea ni actualiza las tablas. Al cambiar a `update`, Hibernate creará las tablas automáticamente si no existen o las actualizará si cambian las entidades.

### ✅ 2. Verificación de Transacciones

**Estado:** ✅ Todas las clases de servicio ya tienen `@Transactional` correctamente configurado:
- `AuthService` - Tiene `@Transactional` en la clase
- `UserService` - Tiene `@Transactional` en la clase
- `PersonaService` - Tiene `@Transactional` en la clase
- `EstudianteService` - Tiene `@Transactional` en la clase
- `DocenteService` - Tiene `@Transactional` en la clase

### ✅ 3. Verificación de Arquitectura

**Estado:** ✅ Arquitectura correcta implementada:
- ✅ Controlador → Servicio (@Transactional) → Repositorio
- ✅ Todos los servicios inyectan correctamente los repositorios
- ✅ No hay llamadas directas al repositorio desde controladores

### ✅ 4. Verificación de Entidades

**Estado:** ✅ Todas las entidades configuradas correctamente:
- ✅ `Persona` - Sin problemas de `scale`
- ✅ `Estudiante` - Usa `BigDecimal` con `precision=5, scale=2` (correcto)
- ✅ `Docente` - Sin problemas
- ✅ `User` - Configurada correctamente
- ✅ `Role` - Configurada correctamente

### ✅ 5. Configuración de Base de Datos

**Estado:** ✅ Configuración correcta:
- ✅ Driver: `com.mysql.cj.jdbc.Driver`
- ✅ URL: `jdbc:mysql://localhost:3306/sistema_roles`
- ✅ Usuario: `root`
- ✅ Password: `root`

## Pasos para Resolver el Problema

### Paso 1: Crear la Base de Datos

Asegúrate de que la base de datos `sistema_roles` existe en MySQL:

```sql
CREATE DATABASE IF NOT EXISTS sistema_roles;
USE sistema_roles;
```

### Paso 2: Ejecutar el Script SQL (Opcional)

Si prefieres crear las tablas manualmente, ejecuta el archivo `database_setup.sql`:

```bash
mysql -u root -proot < database_setup.sql
```

O alternativamente, Hibernate creará las tablas automáticamente con la configuración `ddl-auto=update`.

### Paso 3: Reiniciar la Aplicación

Reinicia la aplicación Spring Boot para que:
1. Hibernate cree/actualice las tablas
2. Los cambios en `application.properties` se apliquen

```bash
mvn clean spring-boot:run
```

### Paso 4: Verificar los Logs

Después de arrancar la aplicación, deberías ver en los logs:
- Consultas SQL de creación de tablas
- Mensajes de conexión exitosa a la base de datos
- Ejemplos:
  ```
  Hibernate: create table Persona ...
  Hibernate: create table Estudiante ...
  Hibernate: create table Docente ...
  Hibernate: create table users ...
  Hibernate: create table roles ...
  Hibernate: create table user_roles ...
  ```

### Paso 5: Probar el Registro de Usuario

Intenta registrar un nuevo usuario y verifica en los logs:
1. Las consultas SQL de INSERT
2. Que no aparezcan errores de transacción
3. Que los datos se persistan correctamente

**Ejemplo de log esperado:**
```
Hibernate: insert into Persona (apellido, contrasenia, gmail, nombre) values (?, ?, ?, ?)
Hibernate: insert into Estudiante (persona_id, progreso, semestre) values (?, ?, ?)
Hibernate: insert into users (active, persona_id, type, username) values (?, ?, ?, ?)
Hibernate: insert into user_roles (role_id, user_id) values (?, ?)
```

## Cómo Verificar que Funciona

### 1. Verificar Tablas en MySQL

Conéctate a MySQL y verifica que las tablas existen:

```sql
USE sistema_roles;
SHOW TABLES;
```

Deberías ver:
- Persona
- Estudiante
- Docente
- users
- roles
- user_roles

### 2. Verificar Registro de Usuario

Después de registrar un usuario, verifica en la base de datos:

```sql
-- Verificar Persona creada
SELECT * FROM Persona;

-- Verificar Estudiante o Docente creado
SELECT * FROM Estudiante;
SELECT * FROM Docente;

-- Verificar User creado
SELECT * FROM users;

-- Verificar roles asignados
SELECT * FROM user_roles;
```

### 3. Revisar Logs de la Aplicación

Busca en los logs:
- ✅ "Started ApiApplication in X seconds"
- ✅ No errores de conexión a la base de datos
- ✅ Queries SQL ejecutándose correctamente

## Diagnóstico de Problemas Comunes

### Problema: "Table doesn't exist"

**Solución:**
1. Verifica que `ddl-auto=update` está configurado
2. Reinicia la aplicación
3. Verifica que la base de datos `sistema_roles` existe

### Problema: "Connection refused"

**Solución:**
1. Verifica que MySQL está corriendo
2. Verifica las credenciales en `application.properties`
3. Verifica que el puerto 3306 está accesible

### Problema: "Roles no inicializados"

**Solución:**
1. Asegúrate de ejecutar `database_setup.sql` que inserta los roles iniciales
2. O verifica que la tabla `roles` tiene los datos:
   ```sql
   SELECT * FROM roles;
   ```
   Deberías ver `ROLE_ESTUDIANTE` y `ROLE_DOCENTE`

### Problema: Los usuarios no se guardan

**Solución:**
1. Verifica que `@Transactional` está en los servicios
2. Revisa los logs SQL para ver si los INSERT se ejecutan
3. Verifica que no hay errores de validación
4. Comprueba que la transacción se completa correctamente

## Configuración Final Recomendada

### Para Desarrollo
```properties
spring.jpa.hibernate.ddl-auto=update
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

### Para Producción
```properties
spring.jpa.hibernate.ddl-auto=validate
logging.level.org.hibernate.SQL=WARN
```

## Contacto

Si después de seguir estos pasos el problema persiste:
1. Comparte los logs de la aplicación
2. Comparte los logs SQL específicos
3. Verifica que la configuración de la base de datos es correcta
