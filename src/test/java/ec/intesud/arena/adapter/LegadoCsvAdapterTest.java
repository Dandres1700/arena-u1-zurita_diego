package ec.intesud.arena.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ec.intesud.arena.exception.FormatoInvalidoException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class LegadoCsvAdapterTest {

    private final LegadoCsvAdapter adapter = new LegadoCsvAdapter();

    @Test
    void conviertePorLineaIgnorandoLasVacias() {
        List<ResultadoImportado> res = adapter.convertir("ana;7;2;300\n\n beto ; 3 ; 1 ; 120 \n");

        assertEquals(2, res.size());
        assertEquals("ana", res.get(0).nick());
        assertEquals(7, res.get(0).estadisticas().kills());
        assertEquals("beto", res.get(1).nick());
        assertEquals(120, res.get(1).estadisticas().segundos());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "ana;7;2",            // faltan columnas
            "ana;siete;2;300",    // no es numero
            "ana;-1;2;300",       // kills negativo
            "ana;1;2;0",          // duracion 0
            ";1;2;300"            // sin nick
    })
    void lineasInvalidasLanzanFormatoInvalido(String linea) {
        assertThrows(FormatoInvalidoException.class, () -> adapter.convertir(linea));
    }

    @Test
    void elMensajeIndicaElNumeroDeLinea() {
        FormatoInvalidoException e = assertThrows(FormatoInvalidoException.class,
                () -> adapter.convertir("ana;7;2;300\nmala"));
        assertTrue(e.getMessage().contains("Linea 2"));
    }

    @Test
    void textoVacioONuloEsInvalido() {
        assertThrows(FormatoInvalidoException.class, () -> adapter.convertir("   "));
        assertThrows(FormatoInvalidoException.class, () -> adapter.convertir(null));
    }
}
