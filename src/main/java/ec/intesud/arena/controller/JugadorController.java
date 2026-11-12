package ec.intesud.arena.controller;

import ec.intesud.arena.dto.JugadorRequest;
import ec.intesud.arena.dto.JugadorResponse;
import ec.intesud.arena.service.JugadorService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * CAPA CONTROLLER: solo recibe la peticion HTTP, valida y delega al servicio.
 * No contiene reglas de negocio ni acceso a datos.
 */
@RestController
@RequestMapping("/api/jugadores")
public class JugadorController {

    private final JugadorService jugadorService;

    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JugadorResponse crear(@Valid @RequestBody JugadorRequest request) { // @Valid activa las validaciones del DTO
        return jugadorService.crear(request);
    }

    @GetMapping
    public List<JugadorResponse> listar() {
        return jugadorService.listar();
    }
}
