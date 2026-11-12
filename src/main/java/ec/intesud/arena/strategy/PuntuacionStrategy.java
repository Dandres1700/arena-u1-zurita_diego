package ec.intesud.arena.strategy;

import ec.intesud.arena.domain.EstadisticasPartida;
import ec.intesud.arena.domain.Modalidad;

/**
 * PATRON STRATEGY: cada modalidad de torneo calcula los puntos de forma distinta.
 * En lugar de un gran if/else/switch en el servicio, cada regla vive en su propia
 * clase que implementa esta interfaz.
 *
 * SOLID - Open/Closed: para agregar una modalidad nueva creamos UNA clase nueva
 * y no tocamos ninguna de las que ya funcionan.
 */
public interface PuntuacionStrategy {

    /** A que modalidad pertenece esta estrategia. */
    Modalidad modalidad();

    /** Calcula los puntos a partir de las estadisticas de la partida. */
    int calcular(EstadisticasPartida stats);
}
