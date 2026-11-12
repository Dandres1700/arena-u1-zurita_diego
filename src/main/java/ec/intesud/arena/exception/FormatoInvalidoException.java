package ec.intesud.arena.exception;

/** Se lanza cuando los datos recibidos tienen un formato incorrecto. Se traduce a HTTP 400. */
public class FormatoInvalidoException extends RuntimeException {

    public FormatoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
