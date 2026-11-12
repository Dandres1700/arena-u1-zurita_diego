package ec.intesud.arena.exception;

/**
 * EXCEPCION PROPIA (no verificada, extiende RuntimeException).
 * Se lanza cuando se viola una regla del negocio: cupo lleno, jugador repetido, etc.
 * El GlobalExceptionHandler la convierte en una respuesta HTTP 409.
 */
public class ReglaNegocioException extends RuntimeException {

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
