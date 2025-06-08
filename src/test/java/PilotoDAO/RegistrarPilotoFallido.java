package PilotoDAO;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import uniairlines.controladores.FXMLRegistrarPilotoController;
import uniairlines.util.UtilGeneral;

public class RegistrarPilotoFallido extends ApplicationTest {

    private final UtilGeneral util = new UtilGeneral();

    @Override
    public void start(Stage stage) {
        FXMLRegistrarPilotoController controller = util.abrirFXML(
                "/vista/FXMLRegistrarPiloto.fxml",
                "Registrar Pilot Test",
                FXMLRegistrarPilotoController.class);
        controller.init("asd");
    }

    @Test
    void registrarPilotoFallido() {
        clickOn("#idTF").write("pilotoTestFallido");
        clickOn("#nombreTF").write("Mi Coronel");
        clickOn("#direccionTF").write("Tus ojos");
        clickOn("#telefonoTF").write("0192837465");
        clickOn("#correoTF").write("coronel@gmail.com");
        clickOn("#fechaNacimientoTF").write("01/02/1980");
        clickOn("#generoCB");
        sleep(500);
        clickOn("MASCULINO");
        clickOn("#tipoLicenciaCB");
        sleep(500);
        clickOn("CAPITAN_DE_AEROLINEA");
        clickOn("#fechaCertificacionTF").write("03-04-2015");
        clickOn("#horasVueloTF").write("10000");
        clickOn("#botonGuardar");
        sleep(800);
    }
}
