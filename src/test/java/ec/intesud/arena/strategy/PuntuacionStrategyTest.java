package ec.intesud.arena.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ec.intesud.arena.domain.EstadisticasPartida;
import ec.intesud.arena.domain.Modalidad;
import ec.intesud.arena.exception.ReglaNegocioException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Test UNITARIO puro: no arranca Spring, es rapido. Patron AAA:
 * Arrange (preparar), Act (ejecutar), Assert (verificar).
 */
class PuntuacionStrategyTest {

    // @ParameterizedTest + @CsvSource: el mismo test se ejecuta una vez por cada fila
    @ParameterizedTest(name = "kills={0} muertes={1} segundos={2} -> {3} pts")
    @CsvSource({
            "5, 2, 300, 44",   // 5*10 - 2*3
            "0, 10, 100, 0",   // negativo -> se corta en 0
            "1, 0, 60, 10"
    })
    @DisplayName("CLASICA: 10 por kill, -3 por muerte, minimo 0")
    void clasica(int kills, int muertes, int segundos, int esperado) {
        int puntos = new ClasicaStrategy().calcular(new EstadisticasPartida(kills, muertes, segundos));
        assertEquals(esperado, puntos);
    }

    @ParameterizedTest(name = "kills={0} muertes={1} segundos={2} -> {3} pts")
    @CsvSource({
            "5, 2, 300, 30",   // 30 + 10 - 10
            "0, 10, 100, 0",   // 10 - 50 -> 0
            "0, 0, 299, 29"    // division entera
    })
    @DisplayName("SUPERVIVENCIA: 1 por 10 s, +2 por kill, -5 por muerte, minimo 0")
    void supervivencia(int kills, int muertes, int segundos, int esperado) {
        int puntos = new SupervivenciaStrategy().calcular(new EstadisticasPartida(kills, muertes, segundos));
        assertEquals(esperado, puntos);
    }

    @ParameterizedTest(name = "kills={0} muertes={1} segundos={2} -> {3} pts")
    @CsvSource({
            "5, 2, 300, 725",  // 700 + 25
            "0, 10, 100, 900",
            "3, 0, 1200, 15"   // bono de tiempo no baja de 0
    })
    @DisplayName("VELOCIDAD: 1000 - segundos + 5 por kill")
    void velocidad(int kills, int muertes, int segundos, int esperado) {
        int puntos = new VelocidadStrategy().calcular(new EstadisticasPartida(kills, muertes, segundos));
        assertEquals(esperado, puntos);
    }

    private CalculadoraPuntos calculadora;

    @BeforeEach
    void preparar() {
        calculadora = new CalculadoraPuntos(
                List.of(new ClasicaStrategy(), new SupervivenciaStrategy(), new VelocidadStrategy()));
    }

    @Test
    @DisplayName("La calculadora elige la estrategia segun la modalidad (polimorfismo)")
    void calculadoraEligeEstrategia() {
        EstadisticasPartida stats = new EstadisticasPartida(5, 2, 300);
        assertEquals(44, calculadora.calcular(Modalidad.CLASICA, stats));
        assertEquals(30, calculadora.calcular(Modalidad.SUPERVIVENCIA, stats));
        assertEquals(725, calculadora.calcular(Modalidad.VELOCIDAD, stats));
    }

    @Test
    @DisplayName("Sin estrategia registrada se lanza ReglaNegocioException")
    void sinEstrategia() {
        CalculadoraPuntos vacia = new CalculadoraPuntos(List.of());
        EstadisticasPartida stats = new EstadisticasPartida(1, 1, 10);
        assertThrows(ReglaNegocioException.class, () -> vacia.calcular(Modalidad.CLASICA, stats));
    }

    @Test
    @DisplayName("Las estadisticas invalidas no se pueden crear")
    void estadisticasInvalidas() {
        assertThrows(IllegalArgumentException.class, () -> new EstadisticasPartida(-1, 0, 10));
        assertThrows(IllegalArgumentException.class, () -> new EstadisticasPartida(1, 0, 0));
    }
}
