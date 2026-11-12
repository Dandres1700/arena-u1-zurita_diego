package ec.intesud.arena.domain;

/**
 * INTERFAZ: contrato minimo para todo lo que puede aparecer en un ranking.
 * Quien la implemente solo debe decir "como me llamo" y "cuantos puntos tengo".
 * (SOLID - Interface Segregation: contrato pequeno y con un unico motivo de existir.)
 */
public interface Puntuable {

    String getNombre();

    int getPuntos();
}
