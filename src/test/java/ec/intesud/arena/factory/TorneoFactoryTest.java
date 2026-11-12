package ec.intesud.arena.factory;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/** TODO 17 (JUnit): escribe las pruebas de la Factory. Pista: @ParameterizedTest + @CsvSource. */
class TorneoFactoryTest {

    @Test
    void cadaModalidadTieneSuCupo() {
        fail("TODO: verifica los 3 cupos (CLASICA 16, SUPERVIVENCIA 32, VELOCIDAD 8)");
    }

    @Test
    void elTorneoNaceAbiertoYSinEspaciosEnElNombre() {
        fail("TODO: crear('  Copa  ', ...) -> nombre 'Copa' y estado ABIERTO");
    }
}
