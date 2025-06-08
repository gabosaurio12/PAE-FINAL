package AsistenteVueloDAO;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import uniairlines.controladores.FXMLRegistrarAsistenteVueloController;
import uniairlines.util.utilgeneral;

class RegistrarAsistenteVueloExitoso extends ApplicationTest {

    private final utilgeneral util = new utilgeneral();

    @Override
    public void start(Stage stage) {
        FXMLRegistrarAsistenteVueloController controller = util.abrirFXML(
                "/vista/FXMLRegistrarAsistenteVuelo.fxml",
                "Registrar Asistente de vuelo Test",
                FXMLRegistrarAsistenteVueloController.class);
        controller.init("asd");
    }

    @Test
    public void registrarAsistenteVueloExitoso() {
        clickOn("#idTF").write("asistenteVueloTest");
        clickOn("#nombreTF").write("Mi ViCapi");
        clickOn("#direccionTF").write("Tus ojos");
        clickOn("#telefonoTF").write("10293333334756");
        clickOn("#correoTF").write("vicapi@gmail.com");
        clickOn("#fechaNacimientoTF").write("01/02/1987");
        clickOn("#generoCB");
        sleep(500);
        clickOn("FEMENINO");
        clickOn("#salarioTF").write("150000");
        clickOn("#idiomasTF").write("5");
        clickOn("#horasVueloAsistidasTF").write("10000");
        clickOn("#botonGuardar");
        sleep(800);
    }
}
