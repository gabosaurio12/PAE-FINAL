package uniairlines.controladores;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import uniairlines.dao.AsistenteVueloDAO;
import uniairlines.dao.PilotoDAO;
import uniairlines.excepcion.ArchivoException;
import uniairlines.modelo.pojo.Vuelo;
import uniairlines.modelo.pojo.empleados.AsistenteVuelo;
import uniairlines.modelo.pojo.empleados.Piloto;
import uniairlines.util.UtilGeneral;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLConfigurarTripulacionController implements Initializable {
    @FXML
    public ComboBox<Piloto> comboPiloto;
    @FXML
    public ComboBox<Piloto> comboCopiloto;
    @FXML
    public ComboBox comboAsistenteUno;
    @FXML
    public ComboBox comboAsistenteDos;
    @FXML
    public ComboBox comboAsistenteTres;
    @FXML
    public ComboBox comboAsistenteCuatro;

    private String nombreAerolinea;
    private List<Piloto> pilotos;
    private List<AsistenteVuelo> asistentes;

    public void initialize(URL url, ResourceBundle rb) {

    }

    public void cargarDatos(Vuelo vuelo, String nombreAerolinea) {
        this.nombreAerolinea = nombreAerolinea;
        configurarCombos();
        if(vuelo != null) {
            //TODO -update
        }
    }

    public void clicRegresar(ActionEvent actionEvent) {
        Stage stage = (Stage) comboPiloto.getScene().getWindow();
        stage.close();
    }

    public void clicGuardar(ActionEvent actionEvent) {

    }

    private void configurarCombos() {
        cargarPilotos();
        cargarAsistentes();
    }

    private void cargarPilotos() {
        pilotos = FXCollections.observableArrayList();
        try {
            PilotoDAO dao = new PilotoDAO();
            List<Piloto> pilotosDao = dao.getTodosLosPilotos(nombreAerolinea);
            pilotos.addAll(pilotosDao);
            comboPiloto.setItems(FXCollections.observableList(pilotos));
            comboCopiloto.setItems(FXCollections.observableList(pilotos));
        } catch (ArchivoException aex) {
            UtilGeneral.mostrarAlerta(
                    "Error",
                    String.format("%s \n %s", aex.getMessage(), aex.getCause()),
                    Alert.AlertType.ERROR);
        }
    }

    private void cargarAsistentes() {
        asistentes = FXCollections.observableArrayList();
        try {
            AsistenteVueloDAO dao = new AsistenteVueloDAO();
            List<AsistenteVuelo> asistentesDao = dao.getTodosLosAsistentesVuelo(nombreAerolinea);
            asistentes.addAll(asistentesDao);
            comboAsistenteUno.setItems(FXCollections.observableList(asistentes));
            comboAsistenteDos.setItems(FXCollections.observableList(asistentes));
            comboAsistenteTres.setItems(FXCollections.observableList(asistentes));
            comboAsistenteCuatro.setItems(FXCollections.observableList(asistentes));
        } catch (ArchivoException aex) {
            UtilGeneral.mostrarAlerta(
                    "Error",
                    String.format("%s \n %s", aex.getMessage(), aex.getCause()),
                    Alert.AlertType.ERROR);
        }
    }
}