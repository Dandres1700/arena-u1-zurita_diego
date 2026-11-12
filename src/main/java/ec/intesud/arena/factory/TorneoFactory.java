package ec.intesud.arena.factory;

import ec.intesud.arena.domain.Modalidad;
import ec.intesud.arena.domain.Torneo;
import org.springframework.stereotype.Component;

/** PATRON FACTORY: centraliza la creacion de torneos con valores por defecto por modalidad. */
@Component
public class TorneoFactory {

    public Torneo crear(String nombre, Modalidad modalidad) {
        // TODO 9 (Factory): cada modalidad tiene su cupo: CLASICA=16, SUPERVIVENCIA=32, VELOCIDAD=8.
        // Usa un "switch" con flechas (->) que devuelva el cupo y crea el Torneo.
        // Pregunta: ¿que ventaja tiene que el switch cubra TODOS los valores del enum?
        throw new UnsupportedOperationException("TODO 9: TorneoFactory.crear()");
    }
}
