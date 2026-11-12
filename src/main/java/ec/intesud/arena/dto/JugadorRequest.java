package ec.intesud.arena.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada (record). Se usa un DTO y no la entidad para no exponer la
 * base de datos directamente y poder validar lo que llega.
 */
public record JugadorRequest(
        @NotBlank(message = "El nick es obligatorio")
        @Size(min = 3, max = 20, message = "El nick debe tener entre 3 y 20 caracteres")
        String nick,

        @NotBlank(message = "El equipo es obligatorio")
        String equipo) {
}
