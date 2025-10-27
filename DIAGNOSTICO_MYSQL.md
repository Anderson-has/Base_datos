# Diagnóstico: Datos No se Guardan en MySQL

## Problema
Los datos no se están guardando en la base de datos MySQL, aunque la aplicación parece funcionar correctamente.

## Cambios Realizados

### 1. Corrección en `AuthService.java`
- Cambiado `save()` por `saveAndFlush()` para forzar la persistencia inmediata
- Establecida la relación bidireccional correctamente entre `Persona` y `Estudiante/Docente`

### 2. Mejoras en `application.properties`
- Añadido parámetro `allowPublicKeyRetrieval=true` a la URL de conexión
- Configurado pool de conexiones HikariCP
- Habilitado logging SQL más detallado

## Pasos para Diagnosticar

### Paso 1: Verificar que la Aplicación Arranca

Ejecuta la aplicación y verifica que NO hay errores:

```bash
cd api
mvn clean spring-boot:run
```

**Debes ver:**
```
Started ApiApplication in X seconds
```

**NO debes ver:**
```
Error starting ApplicationContext
UnsatisfiedDependencyException
```

### Paso 2: Verificar Logs SQL

Cuando registres un usuario, deberías ver en los logs algo como:

```
Hibernate: insert into Persona (apellido, contrasenia, gmail, nombre) values (?, ?, ?, ?)
binding parameter [1] as [VARCHAR] - [Apellido]
binding parameter [2] as [VARCHAR] - [$2a$10$...]
binding parameter [3] as [VARCHAR] - [test@example.com]
binding parameter [4] as [VARCHAR] - [Nombre]

Hibernate: insert into Estudiante (persona_id, progreso, semestre) values (?, ?, ?)
binding parameter [1] as [INTEGER] - [1]
binding parameter [2] as [DECIMAL] - [0.00]
binding parameter [3] as [INTEGER] - [1]
```

### Paso 3: Verificar Base de Datos DIRECTAMENTE

Conéctate a MySQL y ejecuta:

```sql
USE sistema_roles;

-- Verificar tablas
SHOW TABLES;

-- Debe mostrar:
-- Estudiante, Docente, Persona, roles, user_roles, users

-- Verificar datos
SELECT * FROM Persona;
SELECT * FROM Estudiante;
SELECT * FROM Docente;
SELECT * FROM users;
```

### Paso 4: Verificar Que la Aplicación Usa la BD Correcta

Verifica que estás consultando la base de datos correcta. Hay dos posibilidades:

**Opción A:** La aplicación usa una base de datos IN MEMORY (H2)
- Verifica que NO hay dependencia de H2 en `pom.xml`

**Opción B:** La aplicación se conecta a una instancia diferente de MySQL
- Verifica la URL en `application.properties`
- Verifica que no hay múltiples instalaciones de MySQL

## Soluciones Posibles

### Solución 1: Verificar Que Estás Consultando la BD Correcta

Ejecuta este comando en MySQL para ver TODAS las bases de datos:

```sql
SHOW DATABASES;
```

Busca si hay otra base de datos que contenga los datos:
- `test`
- `integralearn` 
- Otra base de datos con nombre similar

### Solución 2: Limpiar y Reconstruir

Si hay datos en memoria o en otro lugar:

```bash
cd api
mvn clean
rm -rf target/
mvn clean install
mvn spring-boot:run
```

### Solución 3: Verificar Transacciones

Asegúrate de que el método `register` en `AuthService` tiene `@Transactional`:

```java
@Service
@Transactional  // <-- Esto DEBE estar
public class AuthService {
    // ...
}
```

### Solución 4: Verificar Commit de Transacción

Añade esto temporalmente al final del método `register` en `AuthService`:

```java
public UserDto register(RegisterDto dto) {
    // ... código existente ...
    
    try {
        System.out.println("DEBUG: Usuario guardado con ID: " + saved.getId());
        System.out.println("DEBUG: Persona guardada con ID: " + savedPersona.getId());
        
        // Forzar commit inmediato
        personaRepository.flush();
        estudianteRepository.flush();
        users.flush();
        
        return new UserDto(...);
    } catch (Exception e) {
        System.err.println("ERROR al guardar: " + e.getMessage());
        e.printStackTrace();
        throw e;
    }
}
```

### Solución 5: Verificar Foreign Keys

Si hay errores de foreign key, verifica que las tablas existen:

```sql
USE sistema_roles;

-- Eliminar todas las tablas en orden correcto
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS Estudiante;
DROP TABLE IF EXISTS Docente;
DROP TABLE IF EXISTS Persona;
DROP TABLE IF EXISTS roles;
SET FOREIGN_KEY_CHECKS = 1;
```

Luego reinicia la aplicación para que Hibernate cree las tablas automáticamente con `ddl-auto=update`.

## Comandos Útiles para Diagnóstico

### Ver logs en tiempo real:
```bash
cd api
mvn spring-boot:run 2>&1 | grep -E "(Hibernate|ERROR|WARN|insert)"
```

### Verificar conexión a MySQL:
```bash
mysql -u root -proot -e "USE sistema_roles; SHOW TABLES;"
```

### Contar registros:
```sql
USE sistema_roles;
SELECT 
    (SELECT COUNT(*) FROM Persona) as personas,
    (SELECT COUNT(*) FROM Estudiante) as estudiantes,
    (SELECT COUNT(*) FROM Docente) as docentes,
    (SELECT COUNT(*) FROM users) as users,
    (SELECT COUNT(*) FROM roles) as roles;
```

## Preguntas Clave para Diagnóstico

1. ¿La aplicación arranca sin errores? → Si NO, revisa logs
2. ¿Aparecen queries SQL en los logs? → Si NO, no está conectada a BD
3. ¿Las queries tienen parámetros bindeados? → Si NO, la query no se ejecuta
4. ¿Ves commits en los logs? → Si NO, la transacción no se completa
5. ¿Qué base de datos muestra `SHOW DATABASES`? → Verifica que es la correcta

## Próximos Pasos

1. Ejecuta la aplicación y copia TODOS los logs
2. Haz un registro de usuario
3. Copia las queries SQL que aparecen en los logs
4. Consulta directamente la base de datos
5. Compara lo que ves en los logs vs lo que ves en la BD

Con esta información podremos identificar exactamente dónde está el problema.
