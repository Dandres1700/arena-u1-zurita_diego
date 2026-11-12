package ec.intesud.arena.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ec.intesud.arena.domain.EstadoTorneo;
import ec.intesud.arena.domain.Modalidad;
import ec.intesud.arena.dto.TorneoResponse;
import ec.intesud.arena.exception.RecursoNoEncontradoException;
import ec.intesud.arena.exception.ReglaNegocioException;
import ec.intesud.arena.service.TorneoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test de la CAPA WEB: @WebMvcTest levanta solo controllers (no servicios ni BD).
 * El servicio se reemplaza por un mock con @MockitoBean.
 * Se prueba el contrato HTTP: codigos de estado y formato del JSON.
 */
@WebMvcTest(TorneoController.class)
class TorneoControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    TorneoService torneoService;

    @Test
    void crearTorneoDevuelve201() throws Exception {
        when(torneoService.crear(any())).thenReturn(
                new TorneoResponse(1L, "Copa INTESUD", Modalidad.CLASICA, EstadoTorneo.ABIERTO, 16, 0));

        mvc.perform(post("/api/torneos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Copa INTESUD\",\"modalidad\":\"CLASICA\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cupoMaximo").value(16));
    }

    @Test
    void nombreVacioDevuelve400ConElMensajeDeValidacion() throws Exception {
        mvc.perform(post("/api/torneos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"\",\"modalidad\":\"CLASICA\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void modalidadInexistenteDevuelve400() throws Exception {
        mvc.perform(post("/api/torneos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Copa\",\"modalidad\":\"XYZ\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void torneoInexistenteDevuelve404() throws Exception {
        when(torneoService.obtener(99L)).thenThrow(new RecursoNoEncontradoException("Torneo", 99L));

        mvc.perform(get("/api/torneos/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje").value("Torneo con id 99 no existe"));
    }

    @Test
    void reglaDeNegocioDevuelve409() throws Exception {
        when(torneoService.inscribir(1L, 2L)).thenThrow(new ReglaNegocioException("Cupo lleno: maximo 16 jugadores"));

        mvc.perform(post("/api/torneos/1/inscripciones/2"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensaje").value("Cupo lleno: maximo 16 jugadores"));
    }
}
