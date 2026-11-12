package ec.intesud.arena.strategy;

import ec.intesud.arena.domain.EstadisticasPartida;
import ec.intesud.arena.domain.Modalidad;
import org.springframework.stereotype.Component;

/**
 * VELOCIDAD: bono de tiempo = max(0, 1000 - segundos), mas 5 puntos por kill.
 */
@Component
public class VelocidadStrategy implements PuntuacionStrategy {

    @Override
    public Modalidad modalidad() {
        // TODO 7a
        throw new UnsupportedOperationException("TODO 7a");
    }

    @Override
    public int calcular(EstadisticasPartida stats) {
        // TODO 7b: ¿que pasa si la partida dura mas de 1000 segundos? El bono no debe ser negativo.
        throw new UnsupportedOperationException("TODO 7b");
    }
}
