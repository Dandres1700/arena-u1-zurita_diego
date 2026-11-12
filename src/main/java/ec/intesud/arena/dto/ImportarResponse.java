package ec.intesud.arena.dto;

import java.util.List;

public record ImportarResponse(int procesadas, List<PartidaResponse> partidas) {
}
