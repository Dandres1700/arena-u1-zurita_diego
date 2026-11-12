package ec.intesud.arena.service;

import ec.intesud.arena.adapter.ImportadorResultados;
import ec.intesud.arena.adapter.ResultadoImportado;
import ec.intesud.arena.domain.Equipo;
import ec.intesud.arena.domain.EstadisticasPartida;
import ec.intesud.arena.domain.EstadoTorneo;
import ec.intesud.arena.domain.Jugador;
import ec.intesud.arena.domain.JugadorPuntaje;
import ec.intesud.arena.domain.Partida;
import ec.intesud.arena.domain.Puntuable;
import ec.intesud.arena.domain.Ranking;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Coordina todo el flujo del torneo. Fijate que NO calcula puntos ni crea torneos
 * por su cuenta: delega en la Factory, la Strategy y el Adapter (SOLID - Single Responsibility).
 *
 * @Transactional: si algo falla a mitad de un metodo, se deshacen TODOS los cambios
 * en la base de datos (por ejemplo, una importacion con una linea mala no guarda nada).
 */
@Service
public class TorneoService {

    private final TorneoRepository torneoRepository;
    private final JugadorRepository jugadorRepository;
    private final PartidaRepository partidaRepository;
    private final TorneoFactory torneoFactory;
    private final CalculadoraPuntos calculadora;
    private final ImportadorResultados importador;

    // Seis dependencias, todas inyectadas por constructor.
    public TorneoService(TorneoRepository torneoRepository,
                         JugadorRepository jugadorRepository,
                         PartidaRepository partidaRepository,
                         TorneoFactory torneoFactory,
                         CalculadoraPuntos calculadora,
                         ImportadorResultados importador) {
        this.torneoRepository = torneoRepository;
        this.jugadorRepository = jugadorRepository;
        this.partidaRepository = partidaRepository;
        this.torneoFactory = torneoFactory;
        this.calculadora = calculadora;
        this.importador = importador;
    }

    @Transactional
    public TorneoResponse crear(CrearTorneoRequest request) {
        Torneo torneo = torneoFactory.crear(request.nombre(), request.modalidad()); // FACTORY
        return TorneoResponse.desde(torneoRepository.save(torneo));
    }

    @Transactional(readOnly = true)
    public List<TorneoResponse> listar() {
        return torneoRepository.findAll().stream().map(TorneoResponse::desde).toList();
    }

    @Transactional(readOnly = true)
    public TorneoResponse obtener(Long id) {
        return TorneoResponse.desde(buscarTorneo(id));
    }

    @Transactional
    public TorneoResponse inscribir(Long torneoId, Long jugadorId) {
        Torneo torneo = buscarTorneo(torneoId);
        Jugador jugador = buscarJugador(jugadorId);
        torneo.inscribir(jugador); // la regla (cupo, repetido...) vive en la entidad
        return TorneoResponse.desde(torneoRepository.save(torneo));
    }

    @Transactional
    public TorneoResponse finalizar(Long torneoId) {
        Torneo torneo = buscarTorneo(torneoId);
        torneo.finalizar();
        return TorneoResponse.desde(torneoRepository.save(torneo));
    }

    @Transactional
    public PartidaResponse registrarPartida(Long torneoId, PartidaRequest request) {
        Torneo torneo = buscarTorneo(torneoId);
        Jugador jugador = buscarJugador(request.jugadorId());
        EstadisticasPartida stats = new EstadisticasPartida(request.kills(), request.muertes(), request.segundos());
        return guardarPartida(torneo, jugador, stats);
    }

    /** Importa resultados del servidor legado. Usa el ADAPTER para entender el formato viejo. */
    @Transactional
    public ImportarResponse importar(Long torneoId, String textoLegado) {
        Torneo torneo = buscarTorneo(torneoId);
        List<ResultadoImportado> filas = importador.convertir(textoLegado); // ADAPTER
        List<PartidaResponse> guardadas = new ArrayList<>();
        for (ResultadoImportado fila : filas) {
            Jugador jugador = jugadorRepository.findByNickIgnoreCase(fila.nick())
                    .orElseThrow(() -> new RecursoNoEncontradoException("No existe el jugador " + fila.nick()));
            guardadas.add(guardarPartida(torneo, jugador, fila.estadisticas()));
        }
        return new ImportarResponse(guardadas.size(), guardadas);
    }

    /** Ranking de jugadores del torneo (los "top" mejores). */
    @Transactional(readOnly = true)
    public List<RankingItem> ranking(Long torneoId, int top) {
        List<JugadorPuntaje> puntajes = puntajesPorJugador(torneoId);
        return aItems(Ranking.top(puntajes, top)); // metodo generico
    }

    /** Ranking por equipos: agrupa jugadores en Equipos (composicion) y los rankea igual. */
    /**
     * TODO 15 (composicion + genericos): ranking por equipos.
     * Agrupa los JugadorPuntaje de puntajesPorJugador(torneoId) por equipo, crea un objeto Equipo
     * por cada grupo y reutiliza Ranking.top(...) y aItems(...). Pista: Collectors.groupingBy.
     */
    @Transactional(readOnly = true)
    public List<RankingItem> rankingEquipos(Long torneoId, int top) {
        throw new UnsupportedOperationException("TODO 15: TorneoService.rankingEquipos()");
    }

    // ---------- metodos privados de apoyo ----------

    private PartidaResponse guardarPartida(Torneo torneo, Jugador jugador, EstadisticasPartida stats) {
        // TODO 14 (service + Strategy): 
        //   1. si el torneo esta FINALIZADO -> ReglaNegocioException
        //   2. si el jugador NO esta inscrito -> ReglaNegocioException (mensaje con el nick)
        //   3. calcula los puntos con "calculadora" segun la modalidad del torneo
        //   4. guarda una Partida con partidaRepository y devuelve PartidaResponse(id, nick, puntos)
        // Pregunta: ¿por que este metodo NO debe tener un switch con las modalidades?
        throw new UnsupportedOperationException("TODO 14: TorneoService.guardarPartida()");
    }

    /** Suma los puntos de cada jugador inscrito (los que no jugaron aparecen con 0). */
    private List<JugadorPuntaje> puntajesPorJugador(Long torneoId) {
        Torneo torneo = buscarTorneo(torneoId);
        Map<Jugador, Integer> sumas = new LinkedHashMap<>();
        for (Jugador j : torneo.getJugadores()) {
            sumas.put(j, 0);
        }
        for (Partida p : partidaRepository.findByTorneoId(torneoId)) {
            sumas.merge(p.getJugador(), p.getPuntos(), Integer::sum);
        }
        return sumas.entrySet().stream()
                .map(e -> new JugadorPuntaje(e.getKey().getNick(), e.getKey().getEquipo(), e.getValue()))
                .toList();
    }

    /** "? extends Puntuable": acepta una lista de cualquier subtipo de Puntuable (comodin generico). */
    private List<RankingItem> aItems(List<? extends Puntuable> ordenados) {
        return IntStream.range(0, ordenados.size())
                .mapToObj(i -> new RankingItem(i + 1, ordenados.get(i).getNombre(), ordenados.get(i).getPuntos()))
                .toList();
    }

    private Torneo buscarTorneo(Long id) {
        return torneoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Torneo", id));
    }

    private Jugador buscarJugador(Long id) {
        return jugadorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Jugador", id));
    }
}
