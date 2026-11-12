package ec.intesud.arena.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ec.intesud.arena.adapter.ImportadorResultados;
import ec.intesud.arena.adapter.ResultadoImportado;
import ec.intesud.arena.domain.EstadisticasPartida;
import ec.intesud.arena.domain.Jugador;
import ec.intesud.arena.domain.Modalidad;
import ec.intesud.arena.domain.Partida;
import ec.intesud.arena.domain.Torneo;
import ec.intesud.arena.dto.CrearTorneoRequest;
import ec.intesud.arena.dto.ImportarResponse;
import ec.intesud.arena.dto.PartidaRequest;
import ec.intesud.arena.dto.PartidaResponse;
import ec.intesud.arena.dto.RankingItem;
import ec.intesud.arena.dto.TorneoResponse;
import ec.intesud.arena.exception.RecursoNoEncontradoException;
import ec.intesud.arena.exception.ReglaNegocioException;
import ec.intesud.arena.factory.TorneoFactory;
import ec.intesud.arena.repository.JugadorRepository;
import ec.intesud.arena.repository.PartidaRepository;
import ec.intesud.arena.repository.TorneoRepository;
import ec.intesud.arena.strategy.CalculadoraPuntos;
import ec.intesud.arena.strategy.ClasicaStrategy;
import ec.intesud.arena.strategy.SupervivenciaStrategy;
import ec.intesud.arena.strategy.VelocidadStrategy;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test del SERVICIO con MOCKITO: los repositorios y el importador son "dobles falsos"
 * (mocks). Asi probamos solo la logica del servicio, sin base de datos ni Spring.
 * La Factory y la Calculadora son reales porque son simples y no tienen efectos externos.
 */
@ExtendWith(MockitoExtension.class)
class TorneoServiceTest {

    @Mock TorneoRepository torneoRepository;
    @Mock JugadorRepository jugadorRepository;
    @Mock PartidaRepository partidaRepository;
    @Mock ImportadorResultados importador;

    TorneoService service;

    @BeforeEach
    void preparar() {
        CalculadoraPuntos calculadora = new CalculadoraPuntos(
                List.of(new ClasicaStrategy(), new SupervivenciaStrategy(), new VelocidadStrategy()));
        service = new TorneoService(torneoRepository, jugadorRepository, partidaRepository,
                new TorneoFactory(), calculadora, importador);
    }

    @Test
    @DisplayName("crear usa la Factory: CLASICA nace con cupo 16")
    void crearTorneo() {
        // thenAnswer devuelve el mismo objeto que recibe save(...), como haria JPA
        when(torneoRepository.save(any(Torneo.class))).thenAnswer(inv -> inv.getArgument(0));

        TorneoResponse r = service.crear(new CrearTorneoRequest("Copa", Modalidad.CLASICA));

        assertEquals(16, r.cupoMaximo());
        assertEquals("Copa", r.nombre());
    }

    @Test
    void inscribirJugador() {
        Torneo torneo = new Torneo("Copa", Modalidad.CLASICA, 2);
        when(torneoRepository.findById(1L)).thenReturn(Optional.of(torneo));
        when(jugadorRepository.findById(5L)).thenReturn(Optional.of(new Jugador("ana", "Rojos")));
        when(torneoRepository.save(any(Torneo.class))).thenAnswer(inv -> inv.getArgument(0));

        TorneoResponse r = service.inscribir(1L, 5L);

        assertEquals(1, r.inscritos());
    }

    @Test
    void inscribirJugadorInexistenteLanza404() {
        when(torneoRepository.findById(1L)).thenReturn(Optional.of(new Torneo("Copa", Modalidad.CLASICA, 2)));
        when(jugadorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> service.inscribir(1L, 99L));
    }

    @Test
    @DisplayName("Un jugador no inscrito no puede registrar partidas y no se guarda nada")
    void partidaDeJugadorNoInscrito() {
        when(torneoRepository.findById(1L)).thenReturn(Optional.of(new Torneo("Copa", Modalidad.CLASICA, 2)));
        when(jugadorRepository.findById(5L)).thenReturn(Optional.of(new Jugador("ana", "Rojos")));

        assertThrows(ReglaNegocioException.class,
                () -> service.registrarPartida(1L, new PartidaRequest(5L, 5, 2, 300)));

        verify(partidaRepository, never()).save(any(Partida.class)); // comprobamos que NO se guardo
    }

    @Test
    @DisplayName("registrarPartida calcula los puntos con la estrategia de la modalidad")
    void registrarPartida() {
        Jugador ana = new Jugador("ana", "Rojos");
        Torneo torneo = new Torneo("Copa", Modalidad.CLASICA, 2);
        torneo.inscribir(ana);
        when(torneoRepository.findById(1L)).thenReturn(Optional.of(torneo));
        when(jugadorRepository.findById(5L)).thenReturn(Optional.of(ana));

        PartidaResponse r = service.registrarPartida(1L, new PartidaRequest(5L, 5, 2, 300));

        assertEquals(44, r.puntos());
        verify(partidaRepository).save(any(Partida.class));
    }

    @Test
    @DisplayName("importar traduce con el Adapter y guarda una partida por linea")
    void importarResultados() {
        Jugador ana = new Jugador("ana", "Rojos");
        Torneo torneo = new Torneo("Copa", Modalidad.CLASICA, 2);
        torneo.inscribir(ana);
        when(torneoRepository.findById(1L)).thenReturn(Optional.of(torneo));
        when(importador.convertir("ana;5;2;300"))
                .thenReturn(List.of(new ResultadoImportado("ana", new EstadisticasPartida(5, 2, 300))));
        when(jugadorRepository.findByNickIgnoreCase("ana")).thenReturn(Optional.of(ana));

        ImportarResponse r = service.importar(1L, "ana;5;2;300");

        assertEquals(1, r.procesadas());
        assertEquals(44, r.partidas().get(0).puntos());
    }

    @Test
    void rankingOrdenaPorPuntosYIncluyeAQuienNoJugo() {
        Jugador ana = new Jugador("ana", "Rojos");
        Jugador beto = new Jugador("beto", "Azules");
        Jugador carla = new Jugador("carla", "Azules");
        Torneo torneo = new Torneo("Copa", Modalidad.CLASICA, 5);
        torneo.inscribir(ana);
        torneo.inscribir(beto);
        torneo.inscribir(carla);
        when(torneoRepository.findById(1L)).thenReturn(Optional.of(torneo));
        when(partidaRepository.findByTorneoId(1L)).thenReturn(List.of(
                new Partida(torneo, ana, new EstadisticasPartida(5, 2, 300), 44),
                new Partida(torneo, beto, new EstadisticasPartida(9, 0, 200), 90),
                new Partida(torneo, ana, new EstadisticasPartida(1, 0, 100), 10)));

        List<RankingItem> ranking = service.ranking(1L, 10);

        assertEquals(3, ranking.size());
        assertEquals("beto", ranking.get(0).nombre());   // 90
        assertEquals("ana", ranking.get(1).nombre());    // 44 + 10 = 54
        assertEquals(54, ranking.get(1).puntos());
        assertEquals(0, ranking.get(2).puntos());        // carla no jugo
    }

    @Test
    void rankingPorEquiposSumaLosPuntosDeSusIntegrantes() {
        Jugador ana = new Jugador("ana", "Rojos");
        Jugador beto = new Jugador("beto", "Azules");
        Jugador carla = new Jugador("carla", "Azules");
        Torneo torneo = new Torneo("Copa", Modalidad.CLASICA, 5);
        torneo.inscribir(ana);
        torneo.inscribir(beto);
        torneo.inscribir(carla);
        when(torneoRepository.findById(1L)).thenReturn(Optional.of(torneo));
        when(partidaRepository.findByTorneoId(1L)).thenReturn(List.of(
                new Partida(torneo, ana, new EstadisticasPartida(5, 2, 300), 60),
                new Partida(torneo, beto, new EstadisticasPartida(2, 0, 100), 20),
                new Partida(torneo, carla, new EstadisticasPartida(3, 0, 100), 30)));

        List<RankingItem> ranking = service.rankingEquipos(1L, 10);

        assertEquals("Rojos", ranking.get(0).nombre());  // ana sola: 60
        assertEquals(60, ranking.get(0).puntos());
        assertEquals("Azules", ranking.get(1).nombre()); // beto + carla: 20 + 30 = 50
        assertEquals(50, ranking.get(1).puntos());
    }
}
