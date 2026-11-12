package ec.intesud.arena.domain;

import java.util.Collection;
import java.util.List;

/** Utilidades de ranking. */
public final class Ranking {

    private Ranking() {
    }

    /**
     * TODO 3 (genericos): devuelve los mejores "n" elementos, de mayor a menor puntaje.
     *  - Si hay empate en puntos, ordena por nombre (sin distinguir mayusculas/minusculas).
     *  - Debe funcionar IGUAL para JugadorPuntaje, Equipo o cualquier Puntuable.
     *  - No cambies la firma: <T extends Puntuable> ¿por que se necesita esa cota?
     * Pista: Comparator.comparingInt(...).reversed().thenComparing(...) y limit(n).
     */
    public static <T extends Puntuable> List<T> top(Collection<T> elementos, int n) {
        throw new UnsupportedOperationException("TODO 3: Ranking.top()");
    }
}
