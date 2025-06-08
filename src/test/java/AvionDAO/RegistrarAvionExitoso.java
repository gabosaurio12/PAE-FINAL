package AvionDAO;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import uniairlines.dao.AvionDAO;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.Asiento;
import uniairlines.modelo.Avion;

import java.util.ArrayList;

public class RegistrarAvionExitoso extends ApplicationTest {

    private static final AvionDAO dao = new AvionDAO();
    private static final String NOMBRE_AEROLINEA = "asd";
    private static final String ID_AVION_TEST = "avion123";

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
        avion.setAsientos(new ArrayList<Asiento>());
        return avion;
    }

    @Test
    public void agregarAvionExitoso() throws ArchivoException {
        // Intentamos eliminar primero por si ya existe
        try {
            dao.eliminar(NOMBRE_AEROLINEA, ID_AVION_TEST);
        } catch (ArchivoException ignored) {}

        Avion avion = crearAvionPrueba();
        dao.agregar(avion, NOMBRE_AEROLINEA);

        Avion avionGuardado = dao.buscarPorId(NOMBRE_AEROLINEA, ID_AVION_TEST);
        Assertions.assertNotNull(avionGuardado);
        Assertions.assertEquals(avion.getModelo(), avionGuardado.getModelo());
    }

    @AfterAll
    public static void limpiar() throws ArchivoException {
        try {
            dao.eliminar(NOMBRE_AEROLINEA, ID_AVION_TEST);
        } catch (ArchivoException ignored) {}
    }
}
