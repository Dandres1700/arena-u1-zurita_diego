package ec.intesud.arena.domain;

/**
 * Datos crudos de una partida (record = clase inmutable).
 */
public record EstadisticasPartida(int kills, int muertes, int segundos) {

    public EstadisticasPartida {
        // TODO 4 (excepciones): valida los datos y lanza IllegalArgumentException con un
        // mensaje claro cuando:
        //   - kills o muertes sean negativos
        //   - segundos sea 0 o negativo
        // Asi sera imposible crear estadisticas invalidas.
    }
}
