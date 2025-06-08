package PilotoDAO;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import uniairlines.controladores.FXMLRegistrarPilotoController;
import uniairlines.util.utilgeneral;

class RegistrarPilotoExitoso extends ApplicationTest {

    private final utilgeneral util = new utilgeneral();

    @Override
    public void start(Stage stage) {
        FXMLRegistrarPilotoController controller = util.abrirFXML(
                "/vista/FXMLRegistrarPiloto.fxml",
                "Registrar Pilot Test",
                FXMLRegistrarPilotoController.class);
        controller.init("asd");
    }

    @Test
    public void registrarPilotoExitoso() {
        clickOn("#idTF").write("pilotoTest");
        clickOn("#nombreTF").write("Mi Capitán");
        clickOn("#direccionTF").write("Tu corazón");
        clickOn("#telefonoTF").write("1029384756");
        clickOn("#correoTF").write("capi@gmail.com");
        clickOn("#fechaNacimientoTF").write("01/02/1987");
        clickOn("#generoCB");
        sleep(500);
        clickOn("MASCULINO");
        clickOn("#salarioTF").write("150000");
        clickOn("#tipoLicenciaCB");
        sleep(500);
        clickOn("CAPITAN_DE_AEROLINEA");
        clickOn("#fechaCertificacionTF").write("03/04/2015");
        clickOn("#horasVueloTF").write("10000");
        clickOn("#botonGuardar");
        sleep(800);
    }
}
