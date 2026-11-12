package ec.intesud.arena.dto;

import ec.intesud.arena.domain.Modalidad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearTorneoRequest(
        @NotBlank(message = "El nombre del torneo es obligatorio")
        String nombre,

        @NotNull(message = "La modalidad es obligatoria (CLASICA, SUPERVIVENCIA o VELOCIDAD)")
        Modalidad modalidad) {
}
