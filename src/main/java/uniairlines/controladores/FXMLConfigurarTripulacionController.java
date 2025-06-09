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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLConfigurarTripulacionController implements Initializable {
    @FXML
    public ComboBox<Piloto> comboPiloto;
    @FXML
    public ComboBox<Piloto> comboCopiloto;
    @FXML
    public ComboBox<AsistenteVuelo> comboAsistenteUno;
    @FXML
    public ComboBox<AsistenteVuelo> comboAsistenteDos;
    @FXML
    public ComboBox<AsistenteVuelo> comboAsistenteTres;
    @FXML
    public ComboBox<AsistenteVuelo> comboAsistenteCuatro;

    private String nombreAerolinea;
    private List<Piloto> pilotos;
    private List<AsistenteVuelo> asistentes;
    private FXMLAgendarVueloController agendarVueloController;
    private FXMLAgendarVueloController.Tripulacion tripulacion;

    public void initialize(URL url, ResourceBundle rb) {

    }

    public void cargarDatos(FXMLAgendarVueloController controlador, FXMLAgendarVueloController.Tripulacion tripulacion, String nombreAerolinea) {
        this.agendarVueloController = controlador;
        this.nombreAerolinea = nombreAerolinea;
        this.tripulacion = tripulacion;
        configurarCombos();
        if(tripulacion != null) {
            comboPiloto.getSelectionModel().select(tripulacion.getPiloto());
            comboCopiloto.getSelectionModel().select(tripulacion.getCopiloto());
            if(controlador.getTripulacion().getAsistentes().size() == 4) {
                comboAsistenteUno.getSelectionModel().select(tripulacion.getAsistentes().get(0));
                comboAsistenteDos.getSelectionModel().select(tripulacion.getAsistentes().get(1));
                comboAsistenteTres.getSelectionModel().select(tripulacion.getAsistentes().get(2));
                comboAsistenteCuatro.getSelectionModel().select(tripulacion.getAsistentes().get(3));
            }
        }
    }

    public void clicRegresar(ActionEvent actionEvent) {
        Stage stage = (Stage) comboPiloto.getScene().getWindow();
        stage.close();
    }

    public void clicGuardar(ActionEvent actionEvent) {
        Piloto piloto = comboPiloto.getSelectionModel().getSelectedItem();
        Piloto copiloto = comboCopiloto.getSelectionModel().getSelectedItem();
        AsistenteVuelo asistenteUno = comboAsistenteUno.getSelectionModel().getSelectedItem();
        AsistenteVuelo asistenteDos = comboAsistenteDos.getSelectionModel().getSelectedItem();
        AsistenteVuelo asistenteTres = comboAsistenteTres.getSelectionModel().getSelectedItem();
        AsistenteVuelo asistenteCuatro = comboAsistenteCuatro.getSelectionModel().getSelectedItem();
        if(validarSeleccion(piloto, copiloto, asistenteUno, asistenteDos, asistenteTres, asistenteCuatro)) {
            //
            if(tripulacion == null) {
                agendarVueloController.instanciarTripulacion();
            }
            agendarVueloController.getTripulacion().setPiloto(piloto);
            agendarVueloController.getTripulacion().setCopiloto(copiloto);
            List<AsistenteVuelo> asistentesAsignados = new ArrayList<>();
            asistentesAsignados.add(asistenteUno);
            asistentesAsignados.add(asistenteDos);
            asistentesAsignados.add(asistenteTres);
            asistentesAsignados.add(asistenteCuatro);
            agendarVueloController.getTripulacion().setAsistentes(asistentesAsignados);
            UtilGeneral.mostrarAlerta("Exito",
                    "La tripulacion se asigno exitosamente",
                    Alert.AlertType.INFORMATION);
            Stage stage = (Stage) comboPiloto.getScene().getWindow();
            stage.close();
        } else {
            UtilGeneral.mostrarAlerta(
                    "Error",
                    "Por favor llene todos los campos",
                    Alert.AlertType.ERROR);
        }
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

    private boolean validarSeleccion(
            Piloto piloto, Piloto copiloto, AsistenteVuelo a, AsistenteVuelo b, AsistenteVuelo c, AsistenteVuelo d) {
        boolean valido = true;
        if(piloto == null) {
            valido = false;
        }
        if(copiloto == null) {
            valido = false;
        }
        if(a == null) {
            valido = false;
        }
        if(b == null) {
            valido = false;
        }
        if(c == null) {
            valido = false;
        }
        if(d == null) {
            valido = false;
        }
        return valido;
    }
}