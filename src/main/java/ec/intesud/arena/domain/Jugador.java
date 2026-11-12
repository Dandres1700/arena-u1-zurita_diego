package ec.intesud.arena.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * ENTIDAD JPA: se guarda en la tabla "jugador".
 * Sin setters: los datos entran por el constructor (encapsulamiento).
 */
@Entity
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nick;

    @Column(nullable = false)
    private String equipo;

    /** JPA necesita un constructor vacio; lo dejamos protegido para que nadie mas lo use. */
    protected Jugador() {
    }

    public Jugador(String nick, String equipo) {
        this.nick = nick.trim();
        this.equipo = equipo.trim();
    }

    public Long getId() {
        return id;
    }

    public String getNick() {
        return nick;
    }

    public String getEquipo() {
        return equipo;
    }

    // Dos jugadores son el mismo si tienen el mismo nick (sin importar mayusculas).
    @Override
    public boolean equals(Object o) {
        return o instanceof Jugador otro && nick.equalsIgnoreCase(otro.getNick());
    }

    @Override
    public int hashCode() {
        return nick.toLowerCase().hashCode();
    }
}
