package ec.intesud.arena.adapter;

import ec.intesud.arena.domain.EstadisticasPartida;
import ec.intesud.arena.exception.FormatoInvalidoException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * PATRON ADAPTER: un servidor antiguo entrega los resultados como texto:
 *
 *     nick;kills;muertes;segundos
 *     ana;7;2;300
 *
 * Esa forma NO coincide con lo que nuestro sistema espera, y no podemos cambiar
 * el servidor viejo. El adaptador traduce el formato legado a nuestro modelo.
 * El resto de la aplicacion nunca se entera de que existe el formato "con punto y coma".
 */
@Component
public class LegadoCsvAdapter implements ImportadorResultados {

    @Override
    public List<ResultadoImportado> convertir(String origen) {
        if (origen == null || origen.isBlank()) {
            throw new FormatoInvalidoException("No hay datos para importar");
        }
        List<ResultadoImportado> resultados = new ArrayList<>();
        String[] lineas = origen.split("\\R"); // \R = cualquier salto de linea
        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i].trim();
            if (!linea.isEmpty()) {
                resultados.add(convertirLinea(linea, i + 1));
            }
        }
        return resultados;
    }

    /**
     * TODO 10 (Adapter + excepciones): traduce UNA linea "nick;kills;muertes;segundos" a un
     * ResultadoImportado.
     *  - Si no tiene exactamente 4 columnas o el nick esta vacio -> FormatoInvalidoException
     *    con el mensaje:  "Linea <numero>: se esperaba nick;kills;muertes;segundos"
     *  - Si un valor no es numero o las estadisticas son invalidas -> FormatoInvalidoException
     *    con el mensaje que empiece por "Linea <numero>: datos invalidos"
     *  - Se toleran espacios alrededor de cada valor.
     * Pista: Integer.parseInt lanza NumberFormatException, y el record lanza
     * IllegalArgumentException. ¿Existe una relacion entre ambas excepciones?
     */
    private ResultadoImportado convertirLinea(String linea, int numero) {
        throw new UnsupportedOperationException("TODO 10: LegadoCsvAdapter.convertirLinea()");
    }
}
