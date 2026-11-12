package ec.intesud.arena.service;

import ec.intesud.arena.domain.Jugador;
import ec.intesud.arena.dto.JugadorRequest;
import ec.intesud.arena.dto.JugadorResponse;
import ec.intesud.arena.exception.ReglaNegocioException;
import ec.intesud.arena.repository.JugadorRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/** CAPA SERVICE: aqui viven las reglas de la aplicacion. */
@Service
public class JugadorService {

    private JugadorRepository jugadorRepository; // TODO 12: hazlo final cuando completes el constructor

    // TODO 12 (inyeccion de dependencias): asigna el repositorio recibido. Luego vuelve el campo final.
    // Pregunta: ¿por que la inyeccion por constructor es mejor que usar @Autowired en el campo?
    public JugadorService(JugadorRepository jugadorRepository) {
    }

    public JugadorResponse crear(JugadorRequest request) {
        // TODO 13 (capa service): si ya existe un jugador con ese nick (sin importar mayusculas)
        // lanza ReglaNegocioException("El nick '<nick>' ya esta en uso"); si no, guardalo
        // y devuelve su JugadorResponse (ver JugadorResponse.desde).
        throw new UnsupportedOperationException("TODO 13: JugadorService.crear()");
    }

    public List<JugadorResponse> listar() {
        return jugadorRepository.findAll().stream().map(JugadorResponse::desde).toList();
    }
}
