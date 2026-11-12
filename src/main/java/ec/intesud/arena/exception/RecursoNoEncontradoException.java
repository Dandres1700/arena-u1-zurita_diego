package ec.intesud.arena.exception;

/** Se lanza cuando se busca algo que no existe. Se traduce a HTTP 404. */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    public RecursoNoEncontradoException(String recurso, Object id) {
        super(recurso + " con id " + id + " no existe");
    }
}
