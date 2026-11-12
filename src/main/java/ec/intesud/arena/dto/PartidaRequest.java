package ec.intesud.arena.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PartidaRequest(
        @NotNull(message = "jugadorId es obligatorio")
        Long jugadorId,

        @Min(value = 0, message = "kills no puede ser negativo")
        int kills,

        @Min(value = 0, message = "muertes no puede ser negativo")
        int muertes,

        @Positive(message = "segundos debe ser mayor a 0")
        int segundos) {
}
