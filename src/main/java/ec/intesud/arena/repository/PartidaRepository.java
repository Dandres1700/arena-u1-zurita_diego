package ec.intesud.arena.repository;

import ec.intesud.arena.domain.Partida;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartidaRepository extends JpaRepository<Partida, Long> {

    // Consulta derivada del nombre: SELECT ... WHERE torneo.id = ?
    List<Partida> findByTorneoId(Long torneoId);
}
