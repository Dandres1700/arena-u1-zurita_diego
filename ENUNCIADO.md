# Proyecto U1 — Arena INTESUD (versión estudiante)

**Programación Avanzada con Java · Evaluación Unidad 1 · Calificación sobre 10**

Recibes un servicio Spring Boot de torneos de videojuegos al que le **faltan piezas**. El proyecto compila,
pero los `TODO` lanzan `UnsupportedOperationException` y las pruebas fallan hasta que lo completes.
Tu trabajo es completar cada `TODO` aplicando lo visto en la unidad, **entendiendo por qué** (hay preguntas
dentro de los comentarios para que pienses).

## Cómo trabajar
1. Crea un repositorio en GitHub y haz **commits pequeños con mensajes claros** (al menos uno por TODO resuelto), en una rama `desarrollo`.
2. Busca los pendientes: en tu IDE busca `TODO` en todo el proyecto (hay 17 numerados).
3. Ejecuta `mvn test` con frecuencia: cada prueba en verde te confirma un avance.
4. Al final: `mvn spring-boot:run` y prueba los endpoints con Postman/Insomnia/Thunder Client (hay una colección en `postman/` y un `requests.http`).

## Qué debes completar
| TODO | Tema de la unidad | Archivo |
|---|---|---|
| 1, 2 | Herencia, composición y polimorfismo | `domain/Equipo` |
| 3 | Genéricos | `domain/Ranking` |
| 4 | Excepciones (validación) | `domain/EstadisticasPartida` |
| 5 | Reglas de negocio + excepciones propias | `domain/Torneo` |
| 6, 7 | Patrón **Strategy** | `strategy/SupervivenciaStrategy`, `VelocidadStrategy` |
| 8 | Inyección de dependencias + Strategy | `strategy/CalculadoraPuntos` |
| 9 | Patrón **Factory** | `factory/TorneoFactory` |
| 10 | Patrón **Adapter** | `adapter/LegadoCsvAdapter` |
| 11 | Manejo de excepciones en la API | `controller/GlobalExceptionHandler` |
| 12, 13 | Capas e inyección por constructor | `service/JugadorService` |
| 14, 15 | Capa service, Strategy y composición | `service/TorneoService` |
| 16, 17 | Pruebas con JUnit (las escribes tú) | `TorneoTest`, `TorneoFactoryTest` |

Usa `ClasicaStrategy`, `LegadoCsvAdapter.convertir`, `RankingTest` y `PuntuacionStrategyTest` como ejemplos de estilo.
**No modifiques** los tests que ya vienen escritos: son los que verifican tu trabajo.

## Reglas de puntuación
| Modalidad | Fórmula | Cupo |
|---|---|---|
| CLASICA | `kills*10 - muertes*3` (mínimo 0) | 16 |
| SUPERVIVENCIA | `segundos/10 + kills*2 - muertes*5` (mínimo 0, división entera) | 32 |
| VELOCIDAD | `max(0, 1000 - segundos) + kills*5` | 8 |

## Ejecución
`mvn spring-boot:run` (H2 en memoria) · con PostgreSQL: `docker compose up -d` y `-Dspring-boot.run.profiles=postgres`.
Requiere JDK 25 (o 17+; si usas otro, cambia `java.version` en el `pom.xml`).

## Entrega y evaluación (sobre 10)
| Criterio | Puntos |
|---|---|
| Pruebas dadas en verde (`mvn test`) | 4 |
| Tus pruebas TODO 16 y 17 bien escritas | 1.5 |
| Calidad del código (nombres, sin repetir, uso correcto de patrones y SOLID) | 1.5 |
| API funcionando: capturas de Postman de un flujo completo (crear → inscribir → partida → ranking → un error 409) | 1.5 |
| Git/GitHub: commits claros y ramas | 1.5 |

Entrega: enlace del repositorio + el PDF con las capturas, además de **respuestas breves (2-3 líneas)** a las preguntas de los TODO 3, 8, 9, 12 y 14.
