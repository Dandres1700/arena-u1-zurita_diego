package ec.intesud.arena.domain;

import java.util.List;

/**
 * COMPOSICION: un Equipo "tiene" jugadores (relacion TIENE-UN).
 * Tambien HEREDA de Participante, asi que un Equipo se puede rankear igual que un jugador.
 */
public class Equipo extends Participante {

    private final List<JugadorPuntaje> integrantes;

    public Equipo(String nombre, List<JugadorPuntaje> integrantes) {
        super(nombre);
        // copia defensiva: nadie de afuera puede modificar nuestra lista
        this.integrantes = List.copyOf(integrantes);
    }

    public List<JugadorPuntaje> getIntegrantes() {
        return integrantes;
    }

    @Override
    public int getPuntos() {
        // TODO 1 (herencia + composicion): los puntos de un equipo son la suma de los
        // puntos de sus integrantes. Pista: recorre "integrantes" (for o stream).
        throw new UnsupportedOperationException("TODO 1: Equipo.getPuntos()");
    }

    @Override
    public String describir() {
        // TODO 2 (polimorfismo): reutiliza describir() de la clase padre y agrega el detalle
        // del equipo. Debe quedar asi:  "Equipo Rojos (70 pts) con 2 jugadores"
        // Pista: ¿que palabra clave te permite llamar al metodo del padre?
        throw new UnsupportedOperationException("TODO 2: Equipo.describir()");
    }
}
