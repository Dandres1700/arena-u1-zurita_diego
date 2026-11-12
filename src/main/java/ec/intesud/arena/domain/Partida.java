package ec.intesud.arena.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/** Resultado de un jugador en un torneo, con los puntos ya calculados por la estrategia. */
@Entity
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Torneo torneo;

    @ManyToOne(optional = false)
    private Jugador jugador;

    private int kills;
    private int muertes;
    private int segundos;
    private int puntos;

    protected Partida() {
    }

    public Partida(Torneo torneo, Jugador jugador, EstadisticasPartida stats, int puntos) {
        this.torneo = torneo;
        this.jugador = jugador;
        this.kills = stats.kills();
        this.muertes = stats.muertes();
        this.segundos = stats.segundos();
        this.puntos = puntos;
    }

    public Long getId() {
        return id;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public int getKills() {
        return kills;
    }

    public int getMuertes() {
        return muertes;
    }

    public int getSegundos() {
        return segundos;
    }

    public int getPuntos() {
        return puntos;
    }
}
