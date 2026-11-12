package ec.intesud.arena.dto;

import ec.intesud.arena.domain.EstadoTorneo;
import ec.intesud.arena.domain.Modalidad;
import ec.intesud.arena.domain.Torneo;

public record TorneoResponse(
        Long id, String nombre, Modalidad modalidad,
        EstadoTorneo estado, int cupoMaximo, int inscritos) {

    public static TorneoResponse desde(Torneo t) {
        return new TorneoResponse(t.getId(), t.getNombre(), t.getModalidad(),
                t.getEstado(), t.getCupoMaximo(), t.getJugadores().size());
    }
}
