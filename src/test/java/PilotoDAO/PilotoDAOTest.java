package PilotoDAO;

import org.junit.jupiter.api.*;
import uniairlines.dao.PilotoDAO;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.pojo.empleados.Piloto;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PilotoDAOTest {
    private static final PilotoDAO dao = new PilotoDAO();

    @Test
    public void registrarPilotoFallido() {
        assertThrows(ArchivoException.class, () -> {
            Piloto piloto = dao.buscarPorId("asd", "pilotoTest");
            dao.guardarPiloto(piloto, "asd");
        });
    }

    @Test
    public void buscarPorIdExitoso() {
        Piloto piloto = null;
        try {
            piloto = dao.buscarPorId("asd", "pilotoTest");
        } catch (ArchivoException e) {
            System.err.println("Error al buscar por id: " + e.getMessage());
        }
        Assertions.assertNotNull(piloto);
    }

    @Test
    public void editarPilotoExitoso() {
        Piloto piloto = null;
        try {
            piloto = dao.buscarPorId("asd", "pilotoTest");
        } catch (ArchivoException e) {
            System.err.println("Error al buscar por id: " + e.getMessage());
        }
        assert piloto != null;
        piloto.setDireccion("Madrid");
        try {
            dao.editarPiloto(piloto, "asd");
        } catch (ArchivoException e) {
            System.err.println("Error al editar piloto: " + e.getMessage());
        }
        try {
            Piloto pilotoEditado = dao.buscarPorId("asd", "pilotoTest");
            Assertions.assertEquals(piloto.getDireccion(), pilotoEditado.getDireccion());
        } catch (ArchivoException e) {
            System.err.println("Error al buscar por id: " + e.getMessage());
        }
    }

    @Test
    public void buscarPorIdFallido() {
        Piloto piloto = null;
        try {
            piloto = dao.buscarPorId("asd", "test");
        } catch (ArchivoException e) {
            System.err.println("Error al buscar por id: " + e.getMessage());
        }
        Assertions.assertNull(piloto);
    }

    @Test
    public void editarPilotoFallido() {
        Piloto piloto = null;
        try {
            piloto = dao.buscarPorId("asd", "pilotoTest");
        } catch (ArchivoException e) {
            System.err.println("Error al buscar por id: " + e.getMessage());
        }
        assert piloto != null;
        piloto.setDireccion("");
        if (piloto.validarDatos()) {
            try {
                dao.editarPiloto(piloto, "asd");
            } catch (ArchivoException e) {
                System.err.println("Error al editar piloto: " + e.getMessage());
            }
        }
        try {
            Piloto pilotoEditado = dao.buscarPorId("asd", "pilotoTest");
            Assertions.assertNotEquals(piloto.getDireccion(), pilotoEditado.getDireccion());
        } catch (ArchivoException e) {
            System.err.println("Error al buscar por id: " + e.getMessage());
        }
    }

    @AfterAll
    public static void borrarPilotoExitoso() {
        Piloto piloto = null;
        try {
            piloto = dao.buscarPorId("asd", "pilotoTest");
        } catch (ArchivoException e) {
            System.err.println("Error al buscar por id: " + e.getMessage());
        }
        assert piloto != null;
        try {
            dao.eliminarPiloto(piloto, "asd");
        } catch (ArchivoException e) {
            System.err.println("Error al eliminar piloto: " + e.getMessage());
        }
        Piloto pilotoEliminado = null;
        try {
            pilotoEliminado = dao.buscarPorId("asd", "pilotoTest");
        } catch (ArchivoException e) {
            System.err.println("Error al buscar por id: " + e.getMessage());
        }
        Assertions.assertNull(pilotoEliminado);
    }

}
