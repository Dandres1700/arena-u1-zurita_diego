package ec.intesud.arena.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class RankingTest {

    @Test
    void ordenaDeMayorAMenorYRespetaElLimite() {
        List<JugadorPuntaje> top = Ranking.top(List.of(
                new JugadorPuntaje("ana", "Rojos", 50),
                new JugadorPuntaje("beto", "Rojos", 90),
                new JugadorPuntaje("carla", "Azules", 70)), 2);

        assertEquals(2, top.size());
        assertEquals("beto", top.get(0).getNombre());
        assertEquals("carla", top.get(1).getNombre());
    }

    @Test
    void enCasoDeEmpateOrdenaPorNombre() {
        List<JugadorPuntaje> top = Ranking.top(List.of(
                new JugadorPuntaje("zoe", "A", 10),
                new JugadorPuntaje("ana", "A", 10)), 2);

        assertEquals("ana", top.get(0).getNombre());
    }

    @Test
    void elMismoMetodoFuncionaConEquipos() {
        Equipo rojos = new Equipo("Rojos", List.of(
                new JugadorPuntaje("ana", "Rojos", 40), new JugadorPuntaje("beto", "Rojos", 30)));
        Equipo azules = new Equipo("Azules", List.of(new JugadorPuntaje("carla", "Azules", 60)));

        List<Equipo> top = Ranking.top(List.of(rojos, azules), 1);

        assertEquals("Rojos", top.get(0).getNombre());
        assertEquals(70, top.get(0).getPuntos());
    }

    @Test
    void polimorfismoDescribirSeComportaDistintoSegunElTipo() {
        Participante jugador = new JugadorPuntaje("ana", "Rojos", 10);
        Participante equipo = new Equipo("Rojos", List.of(new JugadorPuntaje("ana", "Rojos", 10)));

        assertEquals("ana (10 pts)", jugador.describir());
        assertTrue(equipo.describir().startsWith("Equipo Rojos"));
    }
}
