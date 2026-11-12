package ec.intesud.arena.domain;

/**
 * ABSTRACCION + HERENCIA: clase base de quienes compiten en un torneo.
 * No se puede instanciar (abstract): tiene que ser un jugador o un equipo.
 * Guarda lo comun (el nombre) y obliga a las hijas a definir como calculan sus puntos.
 */
public abstract class Participante implements Puntuable {

    private final String nombre;

    protected Participante(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    /** Cada hija decide como calcula sus puntos (metodo abstracto). */
    @Override
    public abstract int getPuntos();

    /** Metodo concreto compartido; las hijas pueden sobrescribirlo (polimorfismo). */
    public String describir() {
        return nombre + " (" + getPuntos() + " pts)";
    }
}
