package ec.intesud.arena.repository;

import ec.intesud.arena.domain.Jugador;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CAPA REPOSITORY: solo acceso a datos. Es una interfaz: Spring Data genera
 * la implementacion en tiempo de ejecucion a partir de los nombres de los metodos.
 * JpaRepository<Jugador, Long> = entidad Jugador con clave de tipo Long (generico).
 */
public interface JugadorRepository extends JpaRepository<Jugador, Long> {

    boolean existsByNickIgnoreCase(String nick);

    Optional<Jugador> findByNickIgnoreCase(String nick);
}
