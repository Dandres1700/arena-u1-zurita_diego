package ec.intesud.arena.adapter;

import ec.intesud.arena.domain.EstadisticasPartida;

/** Resultado ya traducido al "idioma" de nuestro sistema. */
public record ResultadoImportado(String nick, EstadisticasPartida estadisticas) {
}
