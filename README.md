# TP1 · Spring Boot, API REST y arquitectura en capas

Catálogo de productos (consumo de API externa DummyJSON) + Favoritos (CRUD propio en memoria).
Arquitectura en capas: `Controller → Service → Repository/Client`, con DTOs desacoplados, Bean Validation, manejo uniforme de errores y Swagger/OpenAPI. Sin base de datos (el repository es en memoria).

## Cómo levantar el proyecto

Requiere Java 25. Usar siempre el wrapper:

```
# Windows
.\mvnw.cmd spring-boot:run

# macOS/Linux
./mvnw spring-boot:run
```

Cuando el log muestre `Started DemoApplication`, la app escucha en `http://localhost:8080`.

Compilar y correr tests:

```
.\mvnw.cmd test
```

## Endpoints

| Método | Path | Éxito | Qué hace |
|---|---|---|---|
| GET | `/health` | 200 | Chequeo de salud |
| GET | `/ping` | 200 | Devuelve `pong` |
| GET | `/api/productos?limit=10&skip=0` | 200 | Lista productos (DTO propio, paginación `limit`/`skip` de DummyJSON) |
| GET | `/api/productos/{id}` | 200 / 404 | Un producto por id |
| POST | `/api/favoritos` | 201 | Crea favorito `{productoId, nota}` |
| GET | `/api/favoritos` | 200 | Lista favoritos |
| GET | `/api/favoritos/{id}` | 200 / 404 | Un favorito |
| PUT | `/api/favoritos/{id}` | 200 / 400 / 404 | Actualiza favorito |
| DELETE | `/api/favoritos/{id}` | 204 / 404 | Elimina favorito |

Swagger UI: `http://localhost:8080/swagger-ui.html` (grupos **Productos** y **Favoritos**, cada endpoint con `@Operation`).

## Probar

Opción 1 — archivo `requests.http` (VS Code con extensión REST Client o IntelliJ): ejecutá las requests en orden. Incluye 1 éxito + 1 error por recurso.

Opción 2 — curl:

```
curl "http://localhost:8080/api/productos?limit=5&skip=0"
curl http://localhost:8080/api/productos/1
curl http://localhost:8080/api/productos/999999 -i   # 404

curl -X POST http://localhost:8080/api/favoritos -H "Content-Type: application/json" -d "{\"productoId\":1,\"nota\":\"Regalo\"}" -i  # 201
curl http://localhost:8080/api/favoritos -i          # 200
curl -X POST http://localhost:8080/api/favoritos -H "Content-Type: application/json" -d "{\"productoId\":null,\"nota\":\"\"}" -i  # 400 validación
curl http://localhost:8080/api/favoritos/9999 -i     # 404
```

## Errores (formato uniforme `ProblemDetail`)

* Favorito/producto inexistente → `404` (`RecursoNoEncontradoException`).
* Validación fallida → `400` con propiedad `errores: {campo: motivo}`.
* Caída/timeout de DummyJSON → `502` (`ServicioExternoException`, título "Falla al consumir un servicio externo").

## Estructura

```
controller/  ProductoController, FavoritoController, HealthController, PingController
service/     ProductoService, FavoritoService
repository/  FavoritoRepository (interfaz) + InMemoryFavoritoRepository
model/       Favorito (id, productoId, nota, fechaAgregado)
dto/producto/  ProductoDTO (nombre, descripcion, categoria, marca, precio, rating, stock, imagen)
dto/favorito/  FavoritoRequestDTO (validado) + FavoritoResponseDTO + FavoritoMapper
client/dummyjson/  DummyJsonClient + DummyJsonProducto + DummyJsonProductosResponse
exception/   GlobalExceptionHandler + RecursoNoEncontradoException + ServicioExternoException
config/      RestClientConfig (base-url: app.dummyjson.base-url) + OpenApiConfig
```

## Dependencias

* `spring-boot-starter-webmvc` — Spring MVC + Tomcat.
* `spring-boot-starter-validation` — Bean Validation.
* `springdoc-openapi-starter-webmvc-ui` — Swagger UI / OpenAPI.
