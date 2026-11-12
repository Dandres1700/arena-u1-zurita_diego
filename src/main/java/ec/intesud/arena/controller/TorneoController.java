package ec.intesud.arena.controller;

import ec.intesud.arena.dto.ApiResponse;
import ec.intesud.arena.dto.CrearTorneoRequest;
import ec.intesud.arena.dto.ImportarResponse;
import ec.intesud.arena.dto.PartidaRequest;
import ec.intesud.arena.dto.PartidaResponse;
import ec.intesud.arena.dto.RankingItem;
import ec.intesud.arena.dto.TorneoResponse;
import ec.intesud.arena.service.TorneoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/torneos")
public class TorneoController {

    private final TorneoService torneoService;

    public TorneoController(TorneoService torneoService) {
        this.torneoService = torneoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TorneoResponse crear(@Valid @RequestBody CrearTorneoRequest request) {
        return torneoService.crear(request);
    }

    @GetMapping
    public List<TorneoResponse> listar() {
        return torneoService.listar();
    }

    @GetMapping("/{id}")
    public TorneoResponse obtener(@PathVariable Long id) {
        return torneoService.obtener(id);
    }

    @PostMapping("/{id}/inscripciones/{jugadorId}")
    public TorneoResponse inscribir(@PathVariable Long id, @PathVariable Long jugadorId) {
        return torneoService.inscribir(id, jugadorId);
    }

    @PostMapping("/{id}/partidas")
    @ResponseStatus(HttpStatus.CREATED)
    public PartidaResponse registrarPartida(@PathVariable Long id, @Valid @RequestBody PartidaRequest request) {
        return torneoService.registrarPartida(id, request);
    }

    /** Recibe texto plano con el formato del servidor viejo (ver README). */
    @PostMapping(value = "/{id}/importar", consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ImportarResponse importar(@PathVariable Long id, @RequestBody String textoLegado) {
        return torneoService.importar(id, textoLegado);
    }

    @PostMapping("/{id}/finalizar")
    public TorneoResponse finalizar(@PathVariable Long id) {
        return torneoService.finalizar(id);
    }

    @GetMapping("/{id}/ranking")
    public ApiResponse<List<RankingItem>> ranking(@PathVariable Long id,
                                                  @RequestParam(defaultValue = "10") int top) {
        return ApiResponse.ok("Ranking de jugadores", torneoService.ranking(id, top));
    }

    @GetMapping("/{id}/ranking-equipos")
    public ApiResponse<List<RankingItem>> rankingEquipos(@PathVariable Long id,
                                                         @RequestParam(defaultValue = "10") int top) {
        return ApiResponse.ok("Ranking de equipos", torneoService.rankingEquipos(id, top));
    }
}
