package ec.intesud.arena.adapter;

import java.util.List;

/**
 * Interfaz que NUESTRO sistema espera (el "objetivo" del patron Adapter).
 * El servicio solo conoce esta interfaz, no le importa de donde vienen los datos.
 */
public interface ImportadorResultados {

    List<ResultadoImportado> convertir(String origen);
}
