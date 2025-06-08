package AsistenteVueloDAO;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uniairlines.dao.AsistenteVueloDAO;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.pojo.empleados.AsistenteVuelo;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AsistenteVueloDAOTest {
    private static final AsistenteVueloDAO dao = new AsistenteVueloDAO();

    @Test
    public void registrarAsistenteVueloFallido() {
        assertThrows(ArchivoException.class, () -> {
            AsistenteVuelo asistente = dao.buscarPorId("asd", "asistenteVueloTest");
            dao.guardarAsistenteVuelo(asistente, "asd");
        });
    }

    @Test
    public void buscarPorIdExitoso() {
        AsistenteVuelo asistente = null;
        try {
            asistente = dao.buscarPorId("asd", "asistenteVueloTest");
        } catch (ArchivoException e) {
            System.err.println("Error al buscar por id: " + e.getMessage());
        }
        Assertions.assertNotNull(asistente);
    }

    @Test
    public void editarPilotoExitoso() {
        AsistenteVuelo asistente = null;
        try {
            asistente = dao.buscarPorId("asd", "asistenteVueloTest");
        } catch (ArchivoException e) {
            System.err.println("Error al buscar por id: " + e.getMessage());
        }
        assert asistente != null;
        asistente.setDireccion("Madrid");
        try {
            dao.editarAsistenteVuelo(asistente, "asd");
        } catch (ArchivoException e) {
            System.err.println("Error al editar piloto: " + e.getMessage());
        }
        try {
            AsistenteVuelo asistenteEditado = dao.buscarPorId("asd", "asistenteVueloTest");
            Assertions.assertEquals(asistente.getDireccion(), asistenteEditado.getDireccion());
        } catch (ArchivoException e) {
            System.err.println("Error al buscar por id: " + e.getMessage());
        }
    }

    @Test
    public void buscarPorIdFallido() {
        AsistenteVuelo asistente = null;
        try {
            asistente = dao.buscarPorId("asd", "asistenteTest");
        } catch (ArchivoException e) {
            System.err.println("Error al buscar por id: " + e.getMessage());
        }
        Assertions.assertNull(asistente);
    }

    @Test
    public void editarPilotoFallido() {
        AsistenteVuelo asistente = null;
        try {
            asistente = dao.buscarPorId("asd", "asistenteVueloTest");
        } catch (ArchivoException e) {
            System.err.println("Error al buscar por id: " + e.getMessage());
        }
        assert asistente != null;
        asistente.setDireccion("");
        if (asistente.validarDatos()) {
            try {
                dao.editarAsistenteVuelo(asistente, "asd");
            } catch (ArchivoException e) {
                System.err.println("Error al editar piloto: " + e.getMessage());
            }
        }

        try {
            AsistenteVuelo asistenteEditado = dao.buscarPorId("asd", "asistenteVueloTest");
            Assertions.assertNotEquals(asistente.getDireccion(), asistenteEditado.getDireccion());
        } catch (ArchivoException e) {
            System.err.println("Error al buscar por id: " + e.getMessage());
        }
    }

    @AfterAll
    public static void borrarPilotoExitoso() {
        AsistenteVuelo asistente = null;
        try {
            asistente = dao.buscarPorId("asd", "asistenteVueloTest");
        } catch (ArchivoException e) {
            System.err.println("Error al buscar por id: " + e.getMessage());
        }
        assert asistente != null;
        try {
            dao.eliminarAsistenteVuelo(asistente, "asd");
        } catch (ArchivoException e) {
            System.err.println("Error al eliminar piloto: " + e.getMessage());
        }
        AsistenteVuelo asistenteEliminado = new AsistenteVuelo();
        try {
            asistenteEliminado = dao.buscarPorId("asd", "asistenteVueloTest");
        } catch (ArchivoException e) {
            System.err.println("Error al eliminar piloto: " + e.getMessage());
        }

        Assertions.assertNull(asistenteEliminado);
    }

}
