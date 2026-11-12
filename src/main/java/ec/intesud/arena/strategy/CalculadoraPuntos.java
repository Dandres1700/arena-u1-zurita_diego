package ec.intesud.arena.strategy;

import ec.intesud.arena.domain.EstadisticasPartida;
import ec.intesud.arena.domain.Modalidad;
import ec.intesud.arena.exception.ReglaNegocioException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * CONTEXTO del patron Strategy: elige la estrategia correcta segun la modalidad.
 * Spring detecta todos los @Component que implementan PuntuacionStrategy y nos entrega
 * la lista completa en el constructor (inyeccion de dependencias).
 */
@Component
public class CalculadoraPuntos {

    private final Map<Modalidad, PuntuacionStrategy> estrategias = new EnumMap<>(Modalidad.class);

    public CalculadoraPuntos(List<PuntuacionStrategy> lista) {
        // TODO 8a (DI + Strategy): llena el mapa "estrategias" usando la modalidad de cada
        // estrategia como clave. Sin if/else: una sola iteracion.
    }

    public int calcular(Modalidad modalidad, EstadisticasPartida stats) {
        // TODO 8b: busca la estrategia de la modalidad y delega el calculo en ella.
        // Si no existe, lanza ReglaNegocioException("No hay estrategia de puntuacion para ...").
        // Pregunta para pensar: ¿que principio SOLID se cumple al NO usar un switch aqui?
        throw new UnsupportedOperationException("TODO 8b");
    }
}
