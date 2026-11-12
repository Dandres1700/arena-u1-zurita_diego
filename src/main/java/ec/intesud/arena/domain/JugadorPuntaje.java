package ec.intesud.arena.domain;

/**
 * HERENCIA: un jugador con los puntos que lleva en un torneo.
 * Es una clase inmutable: una vez creada no cambia (menos errores).
 */
public class JugadorPuntaje extends Participante {

    private final String equipo;
    private final int puntos;

    public JugadorPuntaje(String nick, String equipo, int puntos) {
        super(nick);
        this.equipo = equipo;
        this.puntos = puntos;
    }

    public String getEquipo() {
        return equipo;
    }

    @Override
    public int getPuntos() {
        return puntos;
    }
}
