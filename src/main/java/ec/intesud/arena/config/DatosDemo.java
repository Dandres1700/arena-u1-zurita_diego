package ec.intesud.arena.config;

import ec.intesud.arena.domain.Jugador;
import ec.intesud.arena.domain.Modalidad;
import ec.intesud.arena.dto.CrearTorneoRequest;
import ec.intesud.arena.dto.PartidaRequest;
import ec.intesud.arena.dto.TorneoResponse;
import ec.intesud.arena.repository.JugadorRepository;
import ec.intesud.arena.service.TorneoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Carga datos de ejemplo al arrancar, para probar la API sin empezar de cero.
 * Se activa con arena.cargar-demo=true (ver application.properties).
 */
@Configuration
@ConditionalOnProperty(name = "arena.cargar-demo", havingValue = "true")
public class DatosDemo {

    @Bean
    CommandLineRunner cargarDatos(JugadorRepository jugadores, TorneoService torneos) {
        return args -> {
            Jugador ana = jugadores.save(new Jugador("Ana", "Rojos"));
            Jugador beto = jugadores.save(new Jugador("Beto", "Rojos"));
            Jugador carla = jugadores.save(new Jugador("Carla", "Azules"));
            Jugador dani = jugadores.save(new Jugador("Dani", "Azules"));

            TorneoResponse copa = torneos.crear(new CrearTorneoRequest("Copa INTESUD", Modalidad.CLASICA));
            for (Jugador j : new Jugador[]{ana, beto, carla, dani}) {
                torneos.inscribir(copa.id(), j.getId());
            }
            torneos.registrarPartida(copa.id(), new PartidaRequest(ana.getId(), 7, 2, 300));
            torneos.registrarPartida(copa.id(), new PartidaRequest(carla.getId(), 5, 4, 280));
        };
    }
}
