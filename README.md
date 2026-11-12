# Arena INTESUD — Proyecto U1

**Programación Avanzada con Java · Unidad 1 · "Entregar el núcleo Java probado como servicio Spring Boot"**

Servicio REST para organizar **torneos de videojuegos**: se registran jugadores, se inscriben a un torneo,
se cargan los resultados de sus partidas y se consulta el ranking (por jugador y por equipo).
Cada modalidad de torneo puntúa distinto, y los resultados también se pueden importar desde un "servidor antiguo".

## Cómo ejecutarlo

Requisitos: JDK 25 (o 17+), Maven 3.9+ (o Spring Tools / IntelliJ, que ya lo traen). No hace falta instalar base de datos.

```bash
mvn spring-boot:run        # arranca en http://localhost:8080 con H2 en memoria y datos de ejemplo
mvn test                   # ejecuta todas las pruebas
```

> Si tu PC solo tiene JDK 21 o 17, cambia `<java.version>25</java.version>` en el `pom.xml`.

**Con PostgreSQL** (requisito de la unidad):
```bash
docker compose up -d       # o crea la base "arena" en tu PostgreSQL local
mvn spring-boot:run -Dspring-boot.run.profiles=postgres
```
Datos de conexión en `application-postgres.properties`. La consola de H2 (perfil por defecto) está en `/h2-console`.

## Dónde está cada tema de la Unidad 1

| Tema de la unidad | Dónde verlo |
|---|---|
| Clases, paquetes, Maven | Paquetes por responsabilidad en `ec.intesud.arena.*`; `pom.xml` |
| Abstracción e interfaces | `domain/Puntuable`, `domain/Participante` (abstracta) |
| Herencia y polimorfismo | `JugadorPuntaje` y `Equipo` extienden `Participante`; `describir()` se comporta distinto |
| Composición | `Equipo` **tiene** una lista de `JugadorPuntaje` |
| Excepciones | `exception/*` + `controller/GlobalExceptionHandler` (400 / 404 / 409) |
| Genéricos | `Ranking.top(...)` con `<T extends Puntuable>`, `ApiResponse<T>`, `JpaRepository<Jugador, Long>` |
| SOLID | Comentarios `SOLID -` en `PuntuacionStrategy`, `CalculadoraPuntos`, `TorneoService`, `Puntuable` |
| Patrón **Factory** | `factory/TorneoFactory` |
| Patrón **Strategy** | `strategy/*` (una clase por modalidad) |
| Patrón **Adapter** | `adapter/LegadoCsvAdapter` |
| Pruebas JUnit / Mockito | `src/test/java/...` (unitarias, parametrizadas, con mocks y de capa web) |
| Spring: DI y capas | Controller → Service → Repository, todo con inyección por constructor |

## Endpoints

| Método | Ruta | Qué hace |
|---|---|---|
| POST | `/api/jugadores` | Crea un jugador `{"nick":"Ana","equipo":"Rojos"}` |
| GET | `/api/jugadores` | Lista jugadores |
| POST | `/api/torneos` | Crea torneo `{"nombre":"Copa","modalidad":"CLASICA"}` (CLASICA, SUPERVIVENCIA, VELOCIDAD) |
| GET | `/api/torneos` · `/api/torneos/{id}` | Lista / consulta torneos |
| POST | `/api/torneos/{id}/inscripciones/{jugadorId}` | Inscribe un jugador |
| POST | `/api/torneos/{id}/partidas` | Registra partida `{"jugadorId":1,"kills":5,"muertes":2,"segundos":300}` |
| POST | `/api/torneos/{id}/importar` | Importa texto plano `nick;kills;muertes;segundos` (una línea por jugador) |
| POST | `/api/torneos/{id}/finalizar` | Cierra el torneo |
| GET | `/api/torneos/{id}/ranking?top=3` | Ranking de jugadores |
| GET | `/api/torneos/{id}/ranking-equipos` | Ranking de equipos |

Pruebas manuales: importa `postman/Arena-U1.postman_collection.json` en Postman/Insomnia/Thunder Client
o usa `requests.http` (VS Code con REST Client / IntelliJ). Al arrancar ya existe el torneo `1` con 4 jugadores (ids 1 a 4).

## Reglas de puntuación (Strategy)

| Modalidad | Fórmula | Cupo (Factory) |
|---|---|---|
| CLASICA | `kills*10 - muertes*3` (mínimo 0) | 16 |
| SUPERVIVENCIA | `segundos/10 + kills*2 - muertes*5` (mínimo 0) | 32 |
| VELOCIDAD | `max(0, 1000 - segundos) + kills*5` | 8 |

## Tu tarea
Lee `ENUNCIADO.md`: esta versión tiene partes en blanco (`TODO`) que debes completar.
