package ec.intesud.arena.dto;

/** Forma unica de los errores que devuelve la API. */
public record ApiError(int status, String mensaje) {
}
