package ec.intesud.arena.dto;

/**
 * GENERICOS: sobre estandar para respuestas. El tipo T cambia segun el endpoint:
 * ApiResponse<List<RankingItem>>, ApiResponse<TorneoResponse>, etc.
 * Una sola clase sirve para todos los casos, sin repetir codigo ni usar Object.
 */
public record ApiResponse<T>(String mensaje, T datos) {

    public static <T> ApiResponse<T> ok(String mensaje, T datos) {
        return new ApiResponse<>(mensaje, datos);
    }
}
