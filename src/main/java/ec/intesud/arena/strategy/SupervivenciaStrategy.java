package ec.intesud.arena.strategy;

import ec.intesud.arena.domain.EstadisticasPartida;
import ec.intesud.arena.domain.Modalidad;
import org.springframework.stereotype.Component;

/**
 * SUPERVIVENCIA: 1 punto por cada 10 segundos vivo + 2 por kill - 5 por muerte. Minimo 0.
 * Mira ClasicaStrategy como ejemplo de como se escribe una estrategia.
 */
@Component
public class SupervivenciaStrategy implements PuntuacionStrategy {

    @Override
    public Modalidad modalidad() {
        // TODO 6a: ¿a que modalidad pertenece esta estrategia?
        throw new UnsupportedOperationException("TODO 6a");
    }

    @Override
    public int calcular(EstadisticasPartida stats) {
        // TODO 6b: aplica la formula del enunciado. Cuidado con la division entera
        // (299 segundos -> 29 puntos) y con que el resultado nunca baje de 0.
        throw new UnsupportedOperationException("TODO 6b");
    }
}
