# Hospital Camas – API REST

**Autora:** María Soto Alcázar

**Proyecto:** Prueba para Ticarum 03/2025

Aplicación en Spring Boot para gestionar camas de hospital.

## Ejecución
1. Clonar repositorio y entrar a la carpeta:
```bash
   git clone https://github.com/SotoWorld17/hospital-camas.git
   cd hospital-camas
```

2. Compilar y ejecutar:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   - Arrancar en el puerto **8081**.
   - Consola H2 en `http://localhost:8081/h2-console` (JDBC `jdbc:h2:mem:testdb`, usuario `sa`, sin contraseña).

## Swagger / OpenAPI
Documentación disponible en `http://localhost:8081/swagger-ui.html`.

## Colección Postman

En `HospitalCamas.postman_collection.json` se incluyen todas las peticiones para probar la API. Importar en Postman.

## Tests unitarios

- Controladores: `CamaControllerTest`, `HospitalControllerTest` (JUnit5 + Mockito).  
- Repositorios: `CamaRepositoryTest` (@DataJpaTest).

Ejecutar:
```bash
mvn test
```