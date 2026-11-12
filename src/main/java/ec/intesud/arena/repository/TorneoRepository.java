package ec.intesud.arena.repository;

import ec.intesud.arena.domain.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TorneoRepository extends JpaRepository<Torneo, Long> {
}
