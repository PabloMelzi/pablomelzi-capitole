Aquí tienes un ejemplo de README completo para tu proyecto, cubriendo descripción, arquitectura, endpoints, ejecución, testing y detalles técnicos.

```markdown
# Prueba de Precios - API Spring Boot

## Descripción

API REST desarrollada en Java con Spring Boot para consultar precios de productos según fecha, producto y marca. Implementa lógica de prioridad y filtrado por rangos de fechas, siguiendo el caso de uso clásico de ZARA.

## Arquitectura

- **Domain Layer:**  
  - Entidades y lógica de negocio (`Price`, `GetPriceService`, `PriceNotFoundException`).
- **Application Layer:**  
  - Servicios de aplicación (`GetPriceServiceImpl`).
- **Infrastructure Layer:**  
  - Controladores REST (`PriceController`), manejo de excepciones (`GlobalExceptionHandler`), persistencia JPA (`JpaPriceRepository`, `PriceRepositoryAdapter`).

## Endpoints

### GET `/prices`

Consulta el precio aplicable para un producto, marca y fecha.

**Parámetros:**
- `applicationDate` (ISO-8601, ej: `2020-06-14T16:00:00`)
- `productId` (long)
- `brandId` (long)

**Ejemplo:**
```
GET /prices?applicationDate=2020-06-14T16:00:00&productId=35455&brandId=1
```

**Respuesta exitosa (`200 OK`):**
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 2,
  "priority": 1,
  "price": 25.45,
  "currency": "EUR",
  "startDate": "2020-06-14T15:00:00",
  "endDate": "2020-06-14T18:30:00"
}
```

**Error (`404 Not Found`):**
```json
{
  "timestamp": "2024-06-10T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "No price found for productId 35455, brandId 1, date 2020-06-14T10:00:00"
}
```

## Ejecución

1. **Requisitos:**
    - Java 17+
    - Maven 3.8+
    - Base de datos compatible con JPA (H2 en memoria por defecto para tests)

2. **Compilar y ejecutar:**
   ```
   mvn clean install
   mvn spring-boot:run
   ```

3. **Base de datos:**
    - Los datos de ejemplo se cargan desde `src/main/resources/data.sql`.

## Testing

- **Unitarios:**
    - Lógica de negocio (`GetPriceServiceTest`)
- **Integración:**
    - Repositorio JPA (`JpaPriceRepositoryIntegrationTest`)
- **Sistema:**
    - Endpoint REST (`PriceControllerSystemTest`)

Ejecuta todos los tests:
```
mvn test
```

## Lógica de Selección de Precio

1. Se filtran los precios por producto, marca y fecha (`startDate <= fecha <= endDate`).
2. Se selecciona el precio con mayor prioridad.
3. Si no hay precios válidos, se lanza `PriceNotFoundException` y se responde `404`.

## Estructura de Carpetas

- `src/main/java/com/pablomelzi/prueba/domain` — Lógica de negocio
- `src/main/java/com/pablomelzi/prueba/application` — Servicios de aplicación
- `src/main/java/com/pablomelzi/prueba/infrastructure/in` — Controladores y excepciones
- `src/main/java/com/pablomelzi/prueba/infrastructure/out/persistence` — Persistencia JPA
- `src/test/java/com/pablomelzi/prueba` — Tests unitarios, integración y sistema

## Dependencias Principales

- Spring Boot
- Spring Data JPA
- Hibernate Validator
- H2 Database (test)
- JUnit 5
- AssertJ

## Ejemplo de Consulta

```
curl "http://localhost:8080/prices?applicationDate=2020-06-14T16:00:00&productId=35455&brandId=1"
```

## Contacto

Desarrollado por Pablo Melzi Puerto 
[GitHub](https://github.com/PabloMelzi)

---

**Nota:**  
Para personalizar la base de datos, modifica la configuración en `application.properties`.
