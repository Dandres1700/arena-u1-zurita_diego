package ec.intesud.arena.strategy;

import ec.intesud.arena.domain.EstadisticasPartida;
import ec.intesud.arena.domain.Modalidad;
import org.springframework.stereotype.Component;

/** CLASICA: cada kill suma 10 y cada muerte resta 3. Nunca baja de 0. */
@Component
public class ClasicaStrategy implements PuntuacionStrategy {

    @Override
    public Modalidad modalidad() {
        return Modalidad.CLASICA;
    }

    @Override
    public int calcular(EstadisticasPartida stats) {
        return Math.max(0, stats.kills() * 10 - stats.muertes() * 3);
    }
}
