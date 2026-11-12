package ec.intesud.arena.domain;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * TODO 16 (JUnit): escribe estas pruebas SIN Spring ni base de datos (mira RankingTest como ejemplo).
 * Usa assertEquals / assertThrows. Cada test debe fallar si la regla no existe.
 */
class TorneoTest {

    @Test
    void inscribeJugadoresHastaElCupo() {
        fail("TODO: crea un Torneo con cupo 2, inscribe 2 jugadores y verifica getJugadores().size()");
    }

    @Test
    void rechazaCuandoElCupoEstaLleno() {
        fail("TODO: cupo 1, inscribe 2 jugadores -> ReglaNegocioException con 'Cupo lleno'");
    }

    @Test
    void rechazaJugadorRepetidoSinImportarMayusculas() {
        fail("TODO: inscribe 'ana' y luego 'ANA' -> ReglaNegocioException");
    }

    @Test
    void rechazaInscripcionesEnTorneoFinalizado() {
        fail("TODO: llama a finalizar() y luego inscribe -> ReglaNegocioException");
    }
}
