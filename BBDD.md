# Estructura de la base de datos

## Tablas
### Hospital
- **id** (BIGINT, PK, autogenerado)
- **nombre** (VARCHAR)

### Dependencia
- **id** (BIGINT, PK, autogenerado)
- **nombre** (VARCHAR)
- **hospital_id** (BIGINT, FK -> hospital.id)

### Cama
- **id** (BIGINT, PK, asignado manualmente en POST)
- **estado** (VARCHAR, valores: LIBRE, OCUPADA, EN_REPARACIÓN, AVERIADA, BAJA)
- **hospital_id** (BIGINT, FK -> hospital.id)
- **dependencia_id** (BIGINT, FK -> dependencia.id)

## Relaciones
- Un `Hospital` 1:N `Dependencia`.
- Una `Dependencia` 1:N `Cama`.
- Una `Cama` N:1 `Hospital` y N:1 `Dependencia`.

## Decisiones de diseño
- Uso de H2 en modo archivo (`jdbc:h2:file:./data/testdb;DB_CLOSE_ON_EXIT=FALSE`) para que los datos persistan entre reinicios.
- Enumeración de `EstadoCama` como STRING en la tabla `cama` para leer fácilmente.
- `@GeneratedValue` en `Hospital.id` y `Dependencia.id`, pero `Cama.id` se fija "a mano" al crear la cama (endpoint POST `/camas/{id}`).
- Al arrancar, el componente `DataLoader` precarga dos hospitales y tres dependencias por hospital; así el sistema ya tiene datos iniciales.

