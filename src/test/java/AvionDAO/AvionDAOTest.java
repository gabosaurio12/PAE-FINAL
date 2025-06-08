package AvionDAO;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uniairlines.dao.AvionDAO;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.Asiento;
import uniairlines.modelo.Avion;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AvionDAOTest {

    private static final AvionDAO dao = new AvionDAO();
    private static final String NOMBRE_AEROLINEA = "avionTestAerolinea";
    private static final String ID_AVION_TEST = "avion123";

    // Avión de prueba que usaremos para las pruebas
    private Avion crearAvionPrueba() {
        Avion avion = new Avion();
        avion.setId(ID_AVION_TEST);
        avion.setModelo("Boeing 747");
        avion.setTipo("Comercial");
        avion.setCapacidadTotal(300);
        avion.setEstado("Activo");
        avion.setPeso(180000);
        avion.setFilas(30);
        avion.setAsientosPorFila(10);

        // Asientos vacíos para simplicidad
        avion.setAsientos(new ArrayList<Asiento>());
        return avion;
    }

    @Test
    public void agregarAvionExitoso() throws ArchivoException {
        // Antes de agregar, aseguramos que no exista
        try {
            dao.eliminar(NOMBRE_AEROLINEA, ID_AVION_TEST);
        } catch (ArchivoException ignored) {}

        Avion avion = crearAvionPrueba();
        dao.agregar(avion, NOMBRE_AEROLINEA);

        Avion avionGuardado = dao.buscarPorId(NOMBRE_AEROLINEA, ID_AVION_TEST);
        Assertions.assertNotNull(avionGuardado);
        Assertions.assertEquals(avion.getModelo(), avionGuardado.getModelo());
    }

    @Test
    public void agregarAvionFallidoPorDuplicado() throws ArchivoException {
        Avion avion = crearAvionPrueba();
        dao.agregar(avion, NOMBRE_AEROLINEA);

        // Intentamos agregar de nuevo el mismo avión y debe lanzar excepción
        assertThrows(ArchivoException.class, () -> {
            dao.agregar(avion, NOMBRE_AEROLINEA);
        });
    }

    @Test
    public void buscarPorIdExitoso() throws ArchivoException {
        Avion avion = crearAvionPrueba();
        dao.agregar(avion, NOMBRE_AEROLINEA);

        Avion encontrado = dao.buscarPorId(NOMBRE_AEROLINEA, ID_AVION_TEST);
        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals(avion.getId(), encontrado.getId());
    }

    @Test
    public void buscarPorIdFallido() throws ArchivoException {
        Avion encontrado = dao.buscarPorId(NOMBRE_AEROLINEA, "idNoExistente");
        Assertions.assertNull(encontrado);
    }

    @Test
    public void modificarAvionExitoso() throws ArchivoException {
        Avion avion = crearAvionPrueba();
        dao.agregar(avion, NOMBRE_AEROLINEA);

        avion.setModelo("Airbus A320");
        dao.modificar(avion, NOMBRE_AEROLINEA);

        Avion modificado = dao.buscarPorId(NOMBRE_AEROLINEA, ID_AVION_TEST);
        Assertions.assertEquals("Airbus A320", modificado.getModelo());
    }

    @Test
    public void modificarAvionFallido() {
        Avion avion = crearAvionPrueba();
        avion.setId("idNoExistente");

        assertThrows(ArchivoException.class, () -> {
            dao.modificar(avion, NOMBRE_AEROLINEA);
        });
    }

    @Test
    public void eliminarAvionExitoso() throws ArchivoException {
        Avion avion = crearAvionPrueba();
        dao.agregar(avion, NOMBRE_AEROLINEA);

        dao.eliminar(NOMBRE_AEROLINEA, ID_AVION_TEST);

        Avion eliminado = dao.buscarPorId(NOMBRE_AEROLINEA, ID_AVION_TEST);
        Assertions.assertNull(eliminado);
    }

    @Test
    public void eliminarAvionFallido() {
        assertThrows(ArchivoException.class, () -> {
            dao.eliminar(NOMBRE_AEROLINEA, "idNoExistente");
        });
    }

    @AfterAll
    public static void limpiar() throws ArchivoException {
        // Por si acaso queda algo, limpiar el avión de prueba
        try {
            dao.eliminar(NOMBRE_AEROLINEA, ID_AVION_TEST);
        } catch (ArchivoException ignored) {}
    }
}
