package ec.intesud.arena.dto;

import ec.intesud.arena.domain.Jugador;

public record JugadorResponse(Long id, String nick, String equipo) {

    /** Metodo de fabrica estatico: convierte la entidad en DTO. */
    public static JugadorResponse desde(Jugador j) {
        return new JugadorResponse(j.getId(), j.getNick(), j.getEquipo());
    }
}
